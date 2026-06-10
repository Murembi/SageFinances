package com.example.demo.dto;

import com.example.demo.entity.Loan;
import lombok.Data;

import java.time.LocalDateTime;


@Data

public class LoanResponseDTO {

    private Long loanId;

    private Long userId;
    private Long assetId;

    private Loan.Status status;

    private LocalDateTime createdAt;

}
