package com.example.demo.dashboard.controller;

import com.example.demo.dashboard.service.AdminDashboardService;
import com.example.demo.dto.DashboardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    //add the audit log
}