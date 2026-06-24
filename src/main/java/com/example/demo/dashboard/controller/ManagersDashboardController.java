package com.example.demo.dashboard.controller;

import java.util.List;

import com.example.demo.dto.AssetRequestDTO;
import com.example.demo.dto.DashboardDTO;
import com.example.demo.entity.Asset;
import com.example.demo.entity.Loan;
import com.example.demo.entity.User;
import com.example.demo.service.AssetService;
import com.example.demo.service.LoanService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dashboard.dto.PendingLoanDTO;
import com.example.demo.dashboard.service.ManagerDashboardService;
import com.example.demo.dashboard.dto.ManagerDashboardDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/manager/dashboard")
@RequiredArgsConstructor
public class ManagersDashboardController {

    private final ManagerDashboardService managerDashboardService;
    private final LoanService loanService;
    private final AssetService assetService;


    @GetMapping
    public String managerDashboard(Model model, HttpSession session) {

        //NEEDS FIXING
//        if (session.getAttribute("userId") == null) {
//            return "redirect:/loginpage";
//        }

        //NEEDS FIXING
//        if (!session.getAttribute("userRole").toString().equals("MANAGER")) {
//            return "redirect:/loginpage";
//        }

        User user = (User) session.getAttribute("user");

        if (user == null || user.getRole() != User.Role.MANAGER) {
            return "redirect:/loginpage";
        }
        DashboardDTO dashboard = managerDashboardService.getManagerDashboard();


        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("userRole", session.getAttribute("userRole"));

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
    public String showAssetsPage(
            Model model,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String condition,
            @RequestParam(required = false) String status) {

        List<Asset> assets =
                assetService.searchAssets(keyword, location, condition, status);

        model.addAttribute("assets", assets);

        // keep selected values (like admin page)
        model.addAttribute("keyword", keyword);
        model.addAttribute("location", location);
        model.addAttribute("condition", condition);
        model.addAttribute("status", status);

        // dropdown data (same as admin)
        model.addAttribute("locations", assetService.getAllLocations());
        model.addAttribute("conditions", assetService.getAllConditions());
        model.addAttribute("statuses", Asset.Status.values());

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
    public String createAsset(@ModelAttribute AssetRequestDTO dto,
                              @RequestParam("imageFile") MultipartFile imageFile) {

        assetService.createAsset(dto, imageFile);

        return "redirect:/manager/dashboard/assets";
    }
    @PostMapping("/approve")
    public String approveLoan(@RequestParam Long loanId) {
        loanService.approveLoan(loanId);
        return "redirect:/manager/dashboard";
    }

    @PostMapping("/reject")
    public String rejectLoan(@RequestParam Long loanId) {
        loanService.rejectLoan(loanId);
        return "redirect:/manager/dashboard";
    }

}
