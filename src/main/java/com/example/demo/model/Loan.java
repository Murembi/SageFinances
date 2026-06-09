package com.example.demo.model;

import java.time.LocalDate;

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

    //no arguments contructor
    public Loan() {
    }

    // minimal loan request
    public Loan(Long assetId, Long userId) {
        this.assetId = assetId;
        this.userId = userId;
        this.requestDate = LocalDate.now();
        this.status = Status.PENDING;
    }

    //getters and setters
    public Long getLoanId() {
        return loanId;
    }
    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }


    public Long getAssetId() {
        return assetId;
    }
    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }
    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }
    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }


    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Status getStatus(Status rejected) {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

}
