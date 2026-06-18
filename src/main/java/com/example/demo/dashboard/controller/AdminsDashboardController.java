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

        // shared dashboard stats only
        model.addAttribute("dashboard", adminDashboardService.getAdminDashboard());

        // shared UI info
        model.addAttribute("username", "admin");
        model.addAttribute("userRole", "ADMIN");

        return "adminDashboard";
    }
}