package com.example.demo.dashboard.dto;

import lombok.Data;

@Data
public class AvailableAssetDTO {

    private Long assetId;
    private String assetName;
    private String category;
    private String status;
}
