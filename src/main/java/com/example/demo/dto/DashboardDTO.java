package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DashboardDTO {
    private Long pendingLoans;
    private Long approvedLoans;
    private Long rejectedLoans;
    private Long totalLoans;

    private Long totalAssets;
    private Long availableAssets;
    private Long loanedAssets;
    private Long damagedAssets;
    private Long retiredAssets;

    private Long myLoans;
    private Long myPendingRequests;
    private Long myApprovedLoans;
    private Long myRejectedLoans;
    private Long myActiveLoans;

}