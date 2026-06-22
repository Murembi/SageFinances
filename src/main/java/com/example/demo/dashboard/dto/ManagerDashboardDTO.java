package com.example.demo.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerDashboardDTO {

    private long pendingLoans;
    private long approvedLoans;
    private long rejectedLoans;
    private long totalLoans;
    private long overdueLoans;

    private long totalAssets;
    private long availableAssets;
    private long loanedAssets;
    private long damagedAssets;
    private long retiredAssets;
}