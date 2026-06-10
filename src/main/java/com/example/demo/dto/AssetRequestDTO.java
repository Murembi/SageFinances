package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

//updating of creating an assest
// no asset id, generated auto
public class AssetRequestDTO {
    private String title;
    private String category;
    private String serialNumber;
    private LocalDate acquisitionDate;
    private BigDecimal cost;
    private String location;
    private String condition;
    private String photoPath;
    private String status;

}
