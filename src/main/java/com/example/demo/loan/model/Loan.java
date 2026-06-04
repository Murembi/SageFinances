package com.example.demo.loan.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Loan {

    // just incase the id is null
    private Long loanId;
    private Long assetId;
    private Long userId;
    private LocalDate requestDate;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private Status status;

    public enum Status{
        PENDING,
        APPROVED,
        REJECTED
    }

    //Full constructor (everything included)
    public Loan(Long loanId,
                Long assetId,
                Long userId,
                LocalDate requestDate,
                LocalDate checkoutDate,
                LocalDate dueDate,
                LocalDate returnDate,
                Status status) {

        this.loanId = loanId;
        this.assetId = assetId;
        this.userId = userId;
        this.requestDate = requestDate;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    //Constructor WITHOUT loanId
    public Loan(Long assetId,
                Long userId,
                LocalDate requestDate,
                LocalDate dueDate,
                Status status) {

        this.assetId = assetId;
        this.userId = userId;
        this.requestDate = requestDate;
        this.dueDate = dueDate;
        this.status = status;

        this.checkoutDate = null;
        this.returnDate = null;
    }

    // minimal loan request
    public Loan(Long assetId, Long userId) {
        this.assetId = assetId;
        this.userId = userId;
        this.requestDate = LocalDate.now();
        this.status = Status.PENDING;
    }




}
