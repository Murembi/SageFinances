package com.example.demo.dashboard.controller;

import com.example.demo.dto.LoanRequestDTO;
import com.example.demo.service.LoanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dashboard.service.UserDashboardService;
import com.example.demo.entity.User;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UsersDashboardController {

    private final UserDashboardService userDashboardService;
    private final LoanService loanService;

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

    @PostMapping("/request-loans")
    public String requestLoans(
            @RequestParam(value = "assetIds", required = false) List<Long> assetIds,
            HttpSession session) {

        User user = (User) session.getAttribute("user");

        System.out.println("USER FROM SESSION: " + user);
        System.out.println("SELECTED ASSETS: " + assetIds);

        if (user == null) {
            return "redirect:/loginpage";
        }

        if (assetIds == null || assetIds.isEmpty()) {
            session.setAttribute("errorMessage", "Please select at least one asset.");
            return "redirect:/user-dashboard";
        }

        try {
            loanService.createMultipleLoanRequests(
                    user.getUserId(),
                    assetIds
            );
        } catch (IllegalStateException e) {
            session.setAttribute("errorMessage", e.getMessage());
            return "redirect:/user-dashboard";
        }

        session.setAttribute("successMessage", "Loan request submitted successfully");

        return "redirect:/user-dashboard";

    }
}
