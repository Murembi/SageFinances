package com.example.demo.dto;

import lombok.Data;

@Data
public class LoanRequestDTO {

    //data required to request a loan
    private Long userId;
    private Long assetId;
}