package com.example.demo.dashboard.controller;

import com.example.demo.dashboard.dto.AuditLogDTO;
import com.example.demo.dashboard.service.AdminDashboardService;
import com.example.demo.dto.DashboardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard/admin")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    //
    @GetMapping
    public DashboardDTO getAdminDashboard() {
        return adminDashboardService.getAdminDashboard();
    }
    // TODO
    //add the audit log

    @GetMapping("/audit-logs")
    public List<AuditLogDTO> getAuditLogs() {
        return adminDashboardService.getAuditLogs();
    }
}
