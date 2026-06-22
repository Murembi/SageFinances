package com.example.demo.exception;

public class LoanNotFoundException extends RuntimeException {

    public LoanNotFoundException(Long loanId) {
        super("Loan with ID " + loanId + " not found.");
    }
}