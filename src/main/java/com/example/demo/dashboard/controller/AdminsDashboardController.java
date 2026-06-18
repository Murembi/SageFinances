package com.example.demo.dashboard.controller;

import com.example.demo.dashboard.service.AdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminsDashboardController {

    private final AdminDashboardService adminDashboardService;

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {

        // 1. Dashboard numbers (cards)
        model.addAttribute(
                "dashboard",
                adminDashboardService.getAdminDashboard()
        );

        // 2. Audit log table data
        model.addAttribute(
                "auditLogs",
                adminDashboardService.getAuditLogs()
        );

        // 3. Sidebar user info (temporary example)
        model.addAttribute("username", "admin");
        model.addAttribute("userRole", "admin");

        // 4. Return JSP file name
        return "adminDashboard";
    }
}