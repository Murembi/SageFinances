package com.example.demo.dashboard.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MyLoanedAssetDTO {

    private String assetName;
    private LocalDateTime checkoutDate;
    private LocalDateTime dueDate;
    private String status;
    private String photoPath;
}
