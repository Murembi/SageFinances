package com.example.demo.controller;

import com.example.demo.entity.AuditLog;
import com.example.demo.service.AuditLogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AuditLogsController {

    private final AuditLogService auditLogService;

    public AuditLogsController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping("/admin/auditlog")
    public String getAuditLogs(
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model
    ) {

        List<AuditLog> logs = auditLogService.getAllLogs();

        // SEARCH FILTER (in-memory for now)
        if (keyword != null && !keyword.isEmpty()) {
            String lower = keyword.toLowerCase();

            logs = logs.stream()
                    .filter(log ->
                            (log.getUser() != null && log.getUser().getName() != null &&
                                    log.getUser().getName().toLowerCase().contains(lower))
                                    ||
                                    (log.getEntityType() != null &&
                                            log.getEntityType().toLowerCase().contains(lower))
                                    ||
                                    (log.getAction() != null &&
                                            log.getAction().toLowerCase().contains(lower))
                    )
                    .collect(Collectors.toList());
        }

        model.addAttribute("logs", logs);
        model.addAttribute("keyword", keyword);

        // reuse shared header values (if you're already doing this elsewhere)
        model.addAttribute("username", "Admin");
        model.addAttribute("userRole", "ADMIN");

        return "adminAuditLog";
    }
}