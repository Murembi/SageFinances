package com.example.demo.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AuditLogDTO {

    private Long userId;
    private String action;
    private String entityType;
    private Long entityId;
    private LocalDateTime timestamp;
    private String oldValue;
    private String newValue;
    }
