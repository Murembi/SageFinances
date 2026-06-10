
package com.example.demo.controller;

import com.example.demo.dto.DashboardDTO;
import com.example.demo.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor

public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/manager")
    public DashboardDTO getManagerDashboard() {
        return dashboardService.getManagerDashboard();
    }

    @GetMapping("/admin")
    public DashboardDTO getAdminDashBoard() {
        return dashboardService.getAdminDashboard();
    }


}
