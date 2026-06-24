package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Data
public class AssetRequestDTO {

    @NotBlank(message = "Title is required") @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Serial number is required")
    private String serialNumber;

    @NotNull(message = "Acquisition date is required")
    private LocalDate acquisitionDate;

    @NotNull(message = "Cost is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Cost must be greater than 0")
    private BigDecimal cost;

    @NotBlank(message = "Location is required")
    private String location;

    // NOTE: matches DB column asset_condition
    @NotBlank(message = "Condition is required")
    private String assetCondition;

    @NotBlank(message = "Photo is required")
    private String photoPath;

    // send as string: "AVAILABLE", "LOANED", etc.
    private String status;
}
