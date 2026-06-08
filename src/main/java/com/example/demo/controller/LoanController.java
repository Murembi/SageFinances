package com.example.demo.controller;

import com.example.demo.dto.LoanRequestDTO;
import com.example.demo.entity.Loan;
import com.example.demo.service.LoanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //handles http requests
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) { //enables access to the business logic
        this.loanService = loanService;
    }

    //@PostMapping //creating a request
    //public Loan createLoan(@RequestBody Loan loan) {
        //return loanService.createLoanRequest(loan);
    //}
    @PostMapping
    public Loan createLoan(@RequestBody LoanRequestDTO dto) {
        return loanService.createLoanRequest(dto);
    }

    //READ getting all loans
    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    //retrieve the loan by their id
    @GetMapping("/{id}")
    public Loan getLoanById(@PathVariable Long id) {
        return loanService.getLoanById(id);
    }

    // allows manager to get their loans by status
    @GetMapping("/status/{status}")
    public List<Loan> getLoansByStatus(@PathVariable Loan.Status status) {
        return loanService.getLoansByStatus(status);
    }

    //delete a loan by their id
    @DeleteMapping("/{id}")
    public void deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
    }

    //approving a loan
    @PutMapping("/{id}/approve")
    public Loan approveLoan(@PathVariable Long id) {
        return loanService.approveLoan(id);
    }

    // rejecting a loan
    @PutMapping("/{id}/reject")
    public Loan rejectLoan(@PathVariable Long id) {
        return loanService.rejectLoan(id);
    }

    // get by status
    @GetMapping("/status/{status}")
    public List<Loan> getByStatus(@PathVariable Loan.Status status) {
        return loanService.getLoansByStatus(status);
    }

    //get by status
    @GetMapping("/user/{userId}")
    public List<Loan> getByUser(@PathVariable Long userId) {
        return loanService.getLoansByUser(userId);
    }
}

