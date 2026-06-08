package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "asset")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asset_id")
    private Long assetId;

    private String title;
    private String category;

    @Column(name = "serial_number", unique = true, nullable = false)
    private String serialNumber;

    @Column(name = "acquisition_date")
    private LocalDate acquisitionDate;

    private BigDecimal cost;
    private String location;

    @Column(name = "`condition`")
    private String condition;

    @Column(name = "photo_path")
    private String photoPath;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        AVAILABLE, LOANED, DAMAGED, RETIRED
    }
}