package com.example.demo.controller;

import com.example.demo.entity.AuditLog;
import com.example.demo.repository.AuditLogRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

    private final AuditLogRepository repository;

    public AuditLogController(AuditLogRepository repository) {
        this.repository = repository;
    }

    // creates an auditLog //1//DONE
    @PostMapping
    public AuditLog createAuditLog(@RequestBody AuditLog auditLog) {
        auditLog.setTimestamp(LocalDateTime.now());
        return repository.save(auditLog);
    }

    // gets allLogs //2//DONE
    @GetMapping
    public List<AuditLog> getAllLogs() {
        return repository.findAll();
    }


    @GetMapping("/{id}")
    public AuditLog getLogById(@PathVariable Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Audit log not found"));
    }
}