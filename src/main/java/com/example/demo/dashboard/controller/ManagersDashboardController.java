package com.example.demo.dashboard.controller;

import java.util.List;

import com.example.demo.dto.AssetRequestDTO;
import com.example.demo.dto.DashboardDTO;
import com.example.demo.entity.Loan;
import com.example.demo.service.AssetService;
import com.example.demo.service.LoanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dashboard.dto.PendingLoanDTO;
import com.example.demo.dashboard.service.ManagerDashboardService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/manager/dashboard")
@RequiredArgsConstructor
public class ManagersDashboardController {

    private final ManagerDashboardService managerDashboardService;
    private final LoanService loanService;
    private final AssetService assetService;


    @GetMapping
    public String managerDashboard(Model model) {
        
        DashboardDTO dashboard = managerDashboardService.getManagerDashboard();

        model.addAttribute("totalAssets", dashboard.getTotalAssets());
        model.addAttribute("availableAssets", dashboard.getAvailableAssets());
        model.addAttribute("approvedLoans", dashboard.getApprovedLoans());
        model.addAttribute("totalLoans", dashboard.getTotalLoans());
        model.addAttribute("loanedAssets", dashboard.getLoanedAssets());
        model.addAttribute("pendingLoans", dashboard.getPendingLoans());
        model.addAttribute("retiredAssets", dashboard.getRetiredAssets());
        model.addAttribute("approvedLoanList",
                loanService.getLoansByStatus(Loan.Status.APPROVED));
        model.addAttribute("overdueLoans", dashboard.getOverdueLoans());

        model.addAttribute(
                "overdueLoanList",
                managerDashboardService.getOverdueLoans()
        );
        // table data
        List<PendingLoanDTO> pendingLoansList =
                managerDashboardService.getPendingLoans();

        model.addAttribute("loanRequests", pendingLoansList);
        return "manager-dashboard";
    }

    @PostMapping("/return/{id}")
    public String managerReturnLoan(@PathVariable Long id) {

        loanService.returnLoan(id);

        return "redirect:/manager/dashboard";
    }

    @GetMapping("/assets")
    public String showAssetsPage(Model model) {

        model.addAttribute("assets", assetService.getAllAssets());

        return "managerAsset";
    }

    @PostMapping("/assets/retire")
    public String retireAsset(
            @RequestParam Long assetId) {

        assetService.retireAsset(assetId);

        return "redirect:/manager/dashboard/assets";
    }

    @GetMapping("/loanHistory")
    public String showLoanHistoryPage(Model model) {
        model.addAttribute("searched", false);
        return "loanHistory";
    }

    @GetMapping("/loanHistory/user")
    public String loanHistoryByUser(
            @RequestParam String username,
            Model model) {

        model.addAttribute(
                "loans",
                loanService.getLoansByUser(username)
        );
        model.addAttribute("searched", true);


        return "loanHistory";

    }
    @GetMapping("/loanHistory/asset")
    public String loanHistoryByAsset(
            @RequestParam String assetTitle,
            Model model) {

        model.addAttribute(
                "loans",
                loanService.getLoansByAsset(assetTitle)
        );
        model.addAttribute("searched", true);

        return "loanHistory";
    }

    @GetMapping("/assets/add")
    public String showAddAssetForm(Model model) {

        model.addAttribute("asset", new AssetRequestDTO());

        return "addAsset";
    }

    @PostMapping("/assets/add")
    public String createAsset(@ModelAttribute AssetRequestDTO dto) {

        assetService.createAsset(dto);

        return "redirect:/manager/dashboard";
    }

}
