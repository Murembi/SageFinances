package com.example.demo.controller;

import com.example.demo.dto.LoanRequestDTO;
import com.example.demo.entity.Loan;
import com.example.demo.service.AssetService;
import com.example.demo.service.LoanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/loan-page")
public class LoanPageController {

    private final LoanService loanService;
    private final AssetService assetService;

    public LoanPageController(
            LoanService loanService,
            AssetService assetService) {

        this.loanService = loanService;
        this.assetService = assetService;
    }

    @GetMapping
    public String showLoanPage(Model model) {

        model.addAttribute(
                "assets",
                assetService.getAvailableAssets()
        );

        model.addAttribute(
                "loans",
                loanService.getAllLoans());

        model.addAttribute(
                "requests",
                loanService.getLoansByStatus(
                        Loan.Status.PENDING));

        return "loanPage";
    }

    @PostMapping("/request")
    public String requestLoan(
            @RequestParam Long userId,
            @RequestParam Long assetId) {

        LoanRequestDTO dto = new LoanRequestDTO();

        dto.setUserId(userId);
        dto.setAssetId(assetId);

        loanService.createLoanRequest(dto);

        return "redirect:/loan-page";
    }

    @PostMapping("/approve/{id}")
    public String approveLoan(
            @PathVariable Long id) {

        loanService.approveLoan(id);

        return "redirect:/loan-page";
    }

    @PostMapping("/reject/{id}")
    public String rejectLoan(
            @PathVariable Long id) {

        loanService.rejectLoan(id);

        return "redirect:/loan-page";
    }

    @PostMapping("/delete/{id}")
    public String deleteLoan(
            @PathVariable Long id) {

        loanService.deleteLoan(id);

        return "redirect:/loan-page";
    }

}
