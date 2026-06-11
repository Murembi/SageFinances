package com.example.demo.service;

import com.example.demo.dto.LoanRequestDTO;
import com.example.demo.entity.Asset;
import com.example.demo.entity.Loan;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class
LoanService {

    //Dependency injection (Loan repo)constructor
    //enables the service to communicate with the database
    private final LoanRepository loanRepository;
    private final AssetRepository assetRepository;
    public LoanService (LoanRepository loanRepository,AssetRepository assetRepository ) {
        this.loanRepository = loanRepository;
        this.assetRepository= assetRepository;
    }

    //Get all loans in the db
    public List<Loan> getAllLoans(){
        return loanRepository.findAll();
    }

    //get loan by ID
    public Loan getLoanById(Long loanId){
        return loanRepository.findById(loanId)
                .orElseThrow(() ->new RuntimeException("Loan not found"));
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
        //  prevents spam requests
        if (alreadyExists) {
            throw new IllegalStateException(
                    "You already have a pending request for this asset"
            );
        }
        //new db object
        Loan loan = new Loan();
        //         TODO
        // set user and asset once repositories are available
        // User user = userRepository.findById(dto.getUserId())
        Asset asset = assetRepository.findById(dto.getAssetId())
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        loan.setStatus(Loan.Status.PENDING);
        return loanRepository.save(loan);
    }

    // different methods for approving and rejecting a loan
    //APPROVING A LOAN
    public Loan approveLoan(Long loanId) {
        Loan loan = getLoanById(loanId);

        //only
        if (loan.getStatus() != Loan.Status.PENDING) {
            throw new IllegalStateException("Only pending loans can be processed");
        }

        loan.setStatus(Loan.Status.APPROVED);
        return loanRepository.save(loan); //writes the updated loan back to the database
    }

    //REJECTING A LOAN
    public Loan rejectLoan(Long loanId){
        Loan loan = getLoanById(loanId);

        //only pending loans can be approved
        if (loan.getStatus() != Loan.Status.PENDING) {
            throw new IllegalStateException("Only pending loans can be processed");
        }
        loan.setStatus(Loan.Status.REJECTED);
        return loanRepository.save(loan);
    }

    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }

    // MANAGERS METHODS

    //finding the loans by status
    //PENDING, APPROVED OR REJECTED
    public List<Loan> getLoansByStatus(Loan.Status status){
       return loanRepository.findByStatus(status);
    }

    //get the loan by the ID
    public List<Loan> getLoansByUser(Long userId) {
        return loanRepository.findByUser_UserId(userId);
    }

    // Duplicate request prevention

}
