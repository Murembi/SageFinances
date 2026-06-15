package com.example.demo.dashboard.controller;

import com.example.demo.dashboard.dto.PendingLoanDTO;
import com.example.demo.dashboard.service.ManagerDashboardService;
import com.example.demo.dto.DashboardDTO;
import com.example.demo.entity.Asset;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard/manager")
@RequiredArgsConstructor
public class ManagerDashboardController {


    private final ManagerDashboardService managerDashboardService;

    @GetMapping
    public DashboardDTO getManagerDashboard() {
        return managerDashboardService.getManagerDashboard();
    }

    // Pending loans table
    @GetMapping("/pending-loans")
    public List<PendingLoanDTO> getPendingLoans() {
        return managerDashboardService.getPendingLoans();
    }


//    // available assets table
//    @GetMapping("/available-assets")
//    public List<Asset> getAvailableAssets() {
//        return managerDashboardService.getAvailableAssets();
//    }
}
