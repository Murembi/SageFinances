package com.example.demo.dashboard.controller;

import java.util.List;

import com.example.demo.dto.DashboardDTO;
import com.example.demo.service.LoanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dashboard.dto.PendingLoanDTO;
import com.example.demo.dashboard.service.ManagerDashboardService;
import com.example.demo.dashboard.dto.ManagerDashboardDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/manager/dashboard")
@RequiredArgsConstructor
public class ManagersDashboardController {

    private final ManagerDashboardService managerDashboardService;
    private final LoanService loanService;

    @GetMapping
    public String managerDashboard(Model model) {
        
        DashboardDTO dashboard = managerDashboardService.getManagerDashboard();

        model.addAttribute("totalAssets", dashboard.getTotalAssets());
        model.addAttribute("availableAssets", dashboard.getAvailableAssets());
        model.addAttribute("approvedLoans", dashboard.getApprovedLoans());
        model.addAttribute("totalLoans", dashboard.getTotalLoans());
        model.addAttribute("loanedAssets", dashboard.getLoanedAssets());
        model.addAttribute("overdueLoans", dashboard.getOverdueLoans());
        model.addAttribute("pendingLoans", dashboard.getPendingLoans());
        model.addAttribute("retiredAssets", dashboard.getRetiredAssets());

        // table data
        List<PendingLoanDTO> pendingLoansList =
                managerDashboardService.getPendingLoans();

        model.addAttribute("loanRequests", pendingLoansList);

        return "manager-dashboard";
    }

    //Approve Loan
    @PostMapping("/approve")
    public String approveLoan(@RequestParam Long loanId) {

        loanService.approveLoan(loanId);

        return "redirect:/manager/dashboard";
    }

    //Reject Loan
    @PostMapping("/reject")
    public String rejectLoan(@RequestParam Long loanId) {

        loanService.rejectLoan(loanId);

        return "redirect:/manager/dashboard";
    }
}
