package com.example.demo.dashboard.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDashboardDTO {

    private String assetName;
    private LocalDateTime requestDate;
    private String status;
}