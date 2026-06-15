package com.example.demo.service;

import com.example.demo.dto.LoanRequestDTO;
import com.example.demo.entity.Asset;
import com.example.demo.entity.Loan;
import com.example.demo.entity.User;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.LoanRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class
LoanService {

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
    public Loan getLoanById(Long loanId){

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        auditLogService.createAuditLog(
                null,
                "LOAN",
                loanId,
                "READ",
                null,
                loan.toString()
        );

        return loan;
    }

    //REQUESTING a Loan and validating
    //runs when the user request for the loan in the controller layer
    //then the manager will approve to approved/rejected
    public Loan createLoanRequest(LoanRequestDTO dto){

        //prevents empty requests
        //user id must exists
        if(dto.getUserId() == null){
            throw new IllegalArgumentException("User is required");
            //checks if the assest was provided
        }

        //asset id must exists
        if (dto.getAssetId() == null) {
            throw new IllegalArgumentException("Asset is required");
        }

        // extracting values userId, assetID, and status from loan
        Long userId = dto.getUserId();
        Long assetId = dto.getAssetId();

        Loan.Status status = Loan.Status.PENDING;

        //if individual has already requested ths assert and pendng
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
            throw new IllegalStateException(
                    "You already have a pending request for this asset"
            );
        }

        //new db object
        Loan loan = new Loan();

        // set user and asset once repositories are available
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new RuntimeException("Asset not found"));

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
                saved.toString()
        );

        return saved;
    }

    // different methods for approving and rejecting a loan
    //APPROVING A LOAN
    public Loan approveLoan(Long loanId) {

        Loan loan = getLoanById(loanId);
        //only
        if (loan.getStatus() != Loan.Status.PENDING) {
            throw new IllegalStateException("Only pending loans can be processed");
        }

        Loan.Status old = loan.getStatus();
        Asset asset = loan.getAsset();

        LocalDateTime checkoutDate = LocalDateTime.now();

        loan.setStatus(Loan.Status.APPROVED);
        loan.setCheckoutDate(checkoutDate);
        loan.setDueDate(checkoutDate.plusDays(21));

        asset.setStatus(Asset.Status.LOANED);

        assetRepository.save(asset);
        Loan saved = loanRepository.save(loan);

        auditLogService.createAuditLog(
                null,
                "LOAN",
                loanId,
                "APPROVE",
                old.name(),
                Loan.Status.APPROVED.name()
        );

        return saved;
    }

    //REJECTING A LOAN
    public Loan rejectLoan(Long loanId){

        Loan loan = getLoanById(loanId);

        //only pending loans can be approved
        if (loan.getStatus() != Loan.Status.PENDING) {
            throw new IllegalStateException("Only pending loans can be processed");
        }

        Loan.Status old = loan.getStatus();

        loan.setStatus(Loan.Status.REJECTED);

        Loan saved = loanRepository.save(loan);

        auditLogService.createAuditLog(
                null,
                "LOAN",
                loanId,
                "REJECT",
                old.name(),
                Loan.Status.REJECTED.name()
        );

        return saved;
    }

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
                status.name()
        );

        return loans;
    }

    //get the loan by the ID
    public List<Loan> getLoansByUser(Long userId) {

        List<Loan> loans = loanRepository.findByUser_UserId(userId);

        auditLogService.createAuditLog(
                null,
                "LOAN",
                userId,
                "FILTER_USER",
                null,
                "Fetched loans by user"
        );

        return loans;
    }
    //checkout loan
    //public Loan checkoutLoan(Long loanId){};

    //retrieve the loan history
    //public List<Loan> getMyLoanHistory(Long userId)



}