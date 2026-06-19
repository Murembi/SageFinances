package com.example.demo.dashboard.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dashboard.dto.AvailableAssetDTO;
import com.example.demo.dashboard.dto.MyLoanedAssetDTO;
import com.example.demo.dashboard.dto.UserLoanDTO;
import com.example.demo.dashboard.service.UserDashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("dashboard/user")
@RequiredArgsConstructor
public class UserDashboardController {

    private final UserDashboardService userDashboardService;

//    @GetMapping("/{userId}/pending-loans")
//    public List<UserLoanDTO> getPendingLoans(@PathVariable Long userId) {
//        return userDashboardService.getMyPendingRequests(userId);
//    }

    @GetMapping("/{userId}/loaned-assets")
    public List<MyLoanedAssetDTO> getLoanedAssets(@PathVariable Long userId) {
        return userDashboardService.getMyLoanedAssets(userId);
    }

    @GetMapping("/assets/available")
    public List<AvailableAssetDTO> getAvailableAssets() {
        return userDashboardService.getAvailableAssets();
    }
}