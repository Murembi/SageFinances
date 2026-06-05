package com.example.demo.service;

import com.example.demo.entity.Loan;
import com.example.demo.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

    //constructor
    private final LoanRepository loanRepository;
    public LoanService (LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    //Get all loans
    public List<Loan> getAllLoans(){
        return loanRepository.findAll();
    }

    //get loan by ID
    public Loan getLoanById(Long loanId){
        return loanRepository.findById(loanId)
                .orElseThrow(() ->new RuntimeException("Loan not found"));
    }

    //REQUESTING A LOAN
    //runs when the user request for the loan in the controller layer
    //then the manager will approve to approved/rejected
    public Loan createLoanRequest(Loan loan){
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

        if (loan.getStatus() != Loan.Status.PENDING) {
            throw new IllegalStateException("Only pending loans can be processed");
        }
        loan.setStatus(Loan.Status.REJECTED);
        return loanRepository.save(loan);
    }

    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }
}
