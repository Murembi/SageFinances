package com.example.demo.dto;

import lombok.Data;

@Data
public class DashboardDTO {

    private long pendingLoans;
    private long approvedLoans;
    private long rejectedLoans;
    private long totalLoans;

    // assets
    private long totalAssets;
    private long availableAssets;
    private long loanedAssets;
    private long damagedAssets;
    private long retiredAssets;
}