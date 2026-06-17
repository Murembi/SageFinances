package com.example.demo.dashboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dashboard.service.UserDashboardService;
import com.example.demo.entity.User;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UsersDashboardController {

    private final UserDashboardService userDashboardService;

    @GetMapping("/dashboard")
public String dashboard(HttpSession session, Model model) {

    User user = (User) session.getAttribute("user");

    if (user == null) {
        return "redirect:/loginpage";
    }

    model.addAttribute("user", user);

    model.addAttribute("availableAssets",
            userDashboardService.getAvailableAssets());

    model.addAttribute("pendingLoans",
            userDashboardService.getMyPendingRequests(user.getUserId()));

    model.addAttribute("loanedAssets",
            userDashboardService.getMyLoanedAssets(user.getUserId()));

    return "user-dashboard";
}
}
