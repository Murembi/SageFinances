package com.example.demo.dto;

import com.example.demo.entity.Loan;
import java.time.LocalDateTime;

public class LoanResponseDTO {

    private Long loanId;

    private Long userId;
    private Long assetId;

    private Loan.Status status;

    private LocalDateTime createdAt;

    // getters and setters
    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public Loan.Status getStatus() {
        return status;
    }

    public void setStatus(Loan.Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
