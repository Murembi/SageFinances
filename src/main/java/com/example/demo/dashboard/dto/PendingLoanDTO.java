package com.example.demo.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PendingLoanDTO {
    private String borrowerName;
    private String assetTitle;
    private LocalDateTime requestDate;
    private LocalDateTime dueDate;
}
