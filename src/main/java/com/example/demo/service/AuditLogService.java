package com.example.demo.service;

import com.example.demo.entity.AuditLog;
import com.example.demo.entity.User;
import com.example.demo.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditLogService {

    private final AuditLogRepository repository;

    public AuditLogService(AuditLogRepository repository) {
        this.repository = repository;
    }

    public AuditLog createAuditLog(
            User user,
            String entityType,
            Long entityId,
            String action,
            String oldValue,
            String newValue
    ) {
        if (newValue != null && newValue.length() > 1000) {
            newValue = newValue.substring(0, 1000);
        }

        if (oldValue != null && oldValue.length() > 1000) {
            oldValue = oldValue.substring(0, 1000);
        }
        AuditLog log = new AuditLog();

        log.setUser(user);
        log.setEntityType(entityType);
        log.setEntityId(entityId);
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());
        log.setOldValue(oldValue);
        log.setNewValue(newValue);

        return repository.save(log);
    }

    public List<AuditLog> getAllLogs() {
        return repository.findAll();
    }

    public AuditLog getLogById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Audit log not found"));
    }

}
