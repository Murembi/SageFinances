package com.example.demo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AssetRequestDTO {

    private String title;
    private String category;
    private String serialNumber;
    private LocalDate acquisitionDate;
    private BigDecimal cost;
    private String location;

    // NOTE: matches DB column asset_condition
    private String assetCondition;

    private String photoPath;

    // send as string: "AVAILABLE", "LOANED", etc.
    private String status;
}
