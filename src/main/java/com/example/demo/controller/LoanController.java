package com.example.demo.controller;

import com.example.demo.entity.Loan;
import com.example.demo.service.LoanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //handles http requests
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService){ //enables access to the business logic
        this.loanService = loanService;
    }
    @PostMapping //creating a post
    public Loan createLoan(@RequestBody Loan loan){
        return loanService.createLoanRequest(loan);
    }
    //READ
    @GetMapping
    public List<Loan> getAllLoans(){
        return loanService.getAllLoans();
    }

    //retrieve the loan by their id
    @GetMapping("/{id}")
    public Loan getLoanById(@PathVariable Long id) {
        return loanService.getLoanById(id);
    }

    //delete a loan by their id
    @DeleteMapping("/{id}")
    public void deleteLoan(@PathVariable Long id){
        loanService.deleteLoan(id);
    }









}
