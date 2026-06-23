package com.example.demo.dashboard.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDashboardDTO {

    private long availableAssets;
    private long myLoans;
    private long pendingRequests;
}