package com.example.demo.controller;

import com.example.demo.entity.AuditLog;
import com.example.demo.service.AuditLogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AuditLogsController {

    private final AuditLogService auditLogService;

    public AuditLogsController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping("/admin/auditlog")
    public String adminAuditLogPage(@RequestParam(required = false) String keyword,
                                    Model model) {

        List<AuditLog> logs = auditLogService.searchLogs(keyword);

        model.addAttribute("logs", logs);
        model.addAttribute("keyword", keyword);

        model.addAttribute("username", "Admin");
        model.addAttribute("userRole", "ADMIN");

        return "adminAuditLog";
    }
}