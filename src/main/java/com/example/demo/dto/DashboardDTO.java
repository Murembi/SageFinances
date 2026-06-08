package com.example.demo.dto;

import lombok.Data;

@Data
public class DashboardDTO {

    private long pendingLoans;
    private long approvedLoans;
    private long rejectedLoans;
    private long totalLoans;
}