package com.example.demo.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PendingLoanDTO {

    private Long id;
    private String borrowerName;
    private String assetTitle;
    private LocalDateTime requestDate;
    private LocalDateTime dueDate;
    private String status;
    private String photoPath;
}