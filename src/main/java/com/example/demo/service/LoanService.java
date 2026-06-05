package com.example.demo.service;

import com.example.demo.entity.Loan;
import com.example.demo.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        //loan.setRequestDate(LocalDate.now());
        return loanRepository.save(loan);
    }

    // different methods for approving and rejecting a loan
    //APPROVING A LOAN
    public Loan approveLoan(Long loanId) {

        Loan loan = getLoanById(loanId);
        loan.setStatus(Loan.Status.APPROVED);
        return loanRepository.save(loan); //writes the updated loan back to the database
    }

    //REJECTING A LOAN
    public Loan rejectLoan(Long loanId){

        Loan loan = getLoanById(loanId);
            //loan.getStatus(Loan.Status.REJECTED);
            return loanRepository.save(loan);
    }


}
