package com.example.demo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AssetResponseDTO {

    private Long assetId;
    private String title;
    private String category;
    private String serialNumber;
    private LocalDate acquisitionDate;
    private BigDecimal cost;
    private String location;
    private String assetCondition;
    private String photoPath;
    private LocalDateTime createdAt;
    private String status;
}
