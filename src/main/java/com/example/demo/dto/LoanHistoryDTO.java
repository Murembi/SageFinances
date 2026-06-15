package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanHistoryDTO {

    private Long loanId;
    private String assetName;
    private LocalDateTime requestDate;
    private LocalDateTime checkoutDate;
    private LocalDateTime dueDate;
    private String status;
    }

