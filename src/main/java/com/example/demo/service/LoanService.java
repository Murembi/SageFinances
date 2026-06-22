package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.exception.*;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoanRequestDTO;
import com.example.demo.entity.Asset;
import com.example.demo.entity.Loan;
import com.example.demo.entity.User;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.LoanRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoanService {

    //Dependency injection (Loan repo)constructor
    //enables the service to communicate with the database
    private final LoanRepository loanRepository;
    private final AssetRepository assetRepository;
    private final UserRepository userRepository;
    private final AuditLogService auditLogService;

    public LoanService(LoanRepository loanRepository,
                       AssetRepository assetRepository,
                       UserRepository userRepository,
                       AuditLogService auditLogService) {
        this.loanRepository = loanRepository;
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
        this.auditLogService = auditLogService;
    }

    //Get all loans in the db
    // DONE
    public List<Loan> getAllLoans(){

        List<Loan> loans = loanRepository.findAll();

        auditLogService.createAuditLog(
                null,
                "LOAN",
                null,
                "READ_ALL",
                null,
                "Fetched all loans"
        );

        return loans;
    }

    //get loan by ID
    //DONE
    public Loan getLoanById(Long loanId){

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException(
                         loanId ));
        auditLogService.createAuditLog(
                null,
                "LOAN",
                loanId,
                "READ",
                null,
                Loan.Status.APPROVED.name() //previous method contained whole loan object
        );

        return loan;
    }

    //REQUESTING a Loan and validating
    //runs when the user request for the loan in the controller layer
    //then the manager will approve to approved/rejected
    @Transactional
    public Loan createLoanRequest(LoanRequestDTO dto){

        //prevents empty requests
        //user id must exists
        if(dto.getUserId() == null){
            throw new IllegalArgumentException("User is required");
            //checks if the asset was provided
        }

        //asset id must exists
        if (dto.getAssetId() == null) {
            throw new IllegalArgumentException("Asset is required");
        }

        // extracting values userId, assetID, and status from loan
        Long userId = dto.getUserId();
        Long assetId = dto.getAssetId();

        Loan.Status status = Loan.Status.PENDING;

        //if individual has already requested ths assert and pending
        boolean alreadyExists =
                loanRepository.existsByUser_UserIdAndAsset_AssetIdAndStatus(
                        userId,
                        assetId,
                        status
                );

        // checking the condition
        // block the request
        // prevents spam requests
        if (alreadyExists) {
            throw new InvalidLoanActionException(
                    "You already have a pending request for this asset"
            );
        }

        //new db object
        Loan loan = new Loan();

        // set user and asset once repositories are available
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                         userId ));

        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() ->new AssetNotFoundException(
                        "Asset with ID " + assetId + " not found."
                ));


        loan.setUser(user);
        loan.setAsset(asset);
        loan.setRequestDate(LocalDateTime.now());
        loan.setStatus(Loan.Status.PENDING);

        Loan saved = loanRepository.save(loan);

        auditLogService.createAuditLog(
                null,
                "LOAN",
                saved.getLoanId(),
                "CREATE",
                null,
                "loan requested"
        );

        return saved;
    }

    //method for multiple loans

    public void createMultipleLoanRequests(
            Long userId,
            List<Long> assetIds) {

        for (Long assetId : assetIds) {

            LoanRequestDTO dto = new LoanRequestDTO();
            dto.setUserId(userId);
            dto.setAssetId(assetId);

            createLoanRequest(dto);
        }
    }
    // different methods for approving and rejecting a loan
    //APPROVING A LOAN
    @Transactional
    public Loan approveLoan(Long loanId) {

        Loan loan = getLoanById(loanId);

        //only
        if (loan.getStatus() != Loan.Status.PENDING) {
            throw new InvalidLoanActionException(
                    "Only pending loans can be approved."
            );
        }

        Loan.Status old = loan.getStatus();

        LocalDateTime checkoutDate = LocalDateTime.now();
        LocalDateTime dueDate = checkoutDate.plusDays(21);
        loan.setStatus(Loan.Status.APPROVED);

        loan.setCheckoutDate(checkoutDate);
        loan.setDueDate(dueDate);
        loan.getAsset().setStatus(Asset.Status.LOANED);
        Loan saved = loanRepository.save(loan);

        auditLogService.createAuditLog(
                loan.getUser(),
                "LOAN",
                loanId,
                "APPROVE",
                old.name(),
                Loan.Status.APPROVED.name()
        );

        return saved;
    }

    //REJECTING A LOAN
    @Transactional
    public Loan rejectLoan(Long loanId){

        Loan loan = getLoanById(loanId);

        //only pending loans can be approved
        if (loan.getStatus() != Loan.Status.PENDING) {
            throw new InvalidLoanActionException("Only pending loans can be processed");
        }

        Loan.Status old = loan.getStatus();

        loan.setStatus(Loan.Status.REJECTED);

        Loan saved = loanRepository.save(loan);

        // AUDIT LOG ADDED
        auditLogService.createAuditLog(
                loan.getUser(),
                "LOAN",
                loanId,
                "REJECT",
                old.name(),
                Loan.Status.REJECTED.name()
        );

        return saved;
    }

    @Transactional
    public void deleteLoan(Long id) {

        Loan loan = getLoanById(id);

        auditLogService.createAuditLog(
                null,
                "LOAN",
                id,
                "DELETE",
                loan.toString(),
                null
        );

        loanRepository.deleteById(id);
    }

    // MANAGERS METHODS

    //finding the loans by status
    //PENDING, APPROVED OR REJECTED
    public List<Loan> getLoansByStatus(Loan.Status status){

        List<Loan> loans = loanRepository.findByStatus(status);

        auditLogService.createAuditLog(
                null,
                "LOAN",
                null,
                "FILTER_STATUS",
                null,
                "loan Status requested"
        );

        return loans;
    }

    //get the loan by the ID
//    public List<Loan> getLoansByUser(Long userId) {
//
//        List<Loan> loans = loanRepository.findByUser_UserId(userId);
//
//        auditLogService.createAuditLog(
//                null,
//                "LOAN",
//                userId,
//                "FILTER_USER",
//                null,
//                "Fetched loans by user"
//        );
//
//        return loans;
//    }

    // Duplicate request prevention

    //checkout loans
//    public Loan checkoutLoan(Long loanId){
//        //looks fot the loanID in the db
//        Loan loan = loanRepository.findById(loanId).orElse(null);
//        if(loan == null)
//            return null;
//
//        //only approved loans can be checked out
//        if (loan.getStatus() != Loan.Status.APPROVED) {
//            return null;
//        }
//        // retrieve the asset linked to the loan
//        Asset asset = loan.getAsset();
//        if (asset == null) return null;
//
//        if (asset.getStatus() != Asset.Status.AVAILABLE) {
//            return null;
//        }
//
//        // Records the exact time the loan started
//        LocalDateTime now = LocalDateTime.now();
//        loan.setCheckoutDate(now);
//        loan.setDueDate(now.plusDays(10)); //set the due dateDouble check the loan days
//
//        loan.setStatus(Loan.Status.APPROVED);
//        asset.setStatus(Asset. Status.LOANED);
//
//
//        assetRepository.save(asset);
//        return loanRepository.save(loan);
//    }

    public List<Loan> getReturnedLoans() {
        return loanRepository.findByStatus(Loan.Status.RETURNED);
    }

    @Transactional
    public Loan returnLoan(Long loanId) {

        Loan loan = getLoanById(loanId);
        // only approved loans can be returned
        if (loan.getStatus() != Loan.Status.APPROVED) {
            throw new InvalidLoanActionException(
                    "Only approved loans can be returned."
            );
        }
        loan.setStatus(Loan.Status.RETURNED);
        loan.setReturnDate(LocalDateTime.now());
        loan.getAsset().setStatus(Asset.Status.AVAILABLE);

        return loanRepository.save(loan);
    }

    //find loan by user
    public List<Loan> getLoansByUser(String name) {
        return loanRepository.findByUser_NameContainingIgnoreCase(name);
    }

    //find loans by asset title
    public List<Loan> getLoansByAsset(String title) {
        return loanRepository.findByAsset_TitleContainingIgnoreCase(title);
    }



}