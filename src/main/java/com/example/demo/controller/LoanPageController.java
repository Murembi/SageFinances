package com.example.demo.controller;

import com.example.demo.dto.LoanRequestDTO;
import com.example.demo.entity.Loan;
import com.example.demo.service.AssetService;
import com.example.demo.service.LoanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/loans")
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
                loanService.getLoansByStatus(Loan.Status.APPROVED));

        model.addAttribute(
                "requests",
                loanService.getLoansByStatus(
                        Loan.Status.PENDING));

        model.addAttribute("returnedLoans",
                loanService.getReturnedLoans());

        return "loanPage";
    }

    //MODIFIED AND WORKS
    @PostMapping("/request")
    public String requestLoan(
            @RequestParam Long userId,
            @RequestParam Long assetId) {

        LoanRequestDTO dto = new LoanRequestDTO();

        dto.setUserId(userId);
        dto.setAssetId(assetId);

        loanService.createLoanRequest(dto);

        return "redirect:/admin/loans";
    }

    @PostMapping("/delete/{id}")
    public String deleteLoan(
            @PathVariable Long id) {

        loanService.deleteLoan(id);

        return "redirect:/admin/loans";
    }

    @PostMapping("/approve/{id}")
    public String approveLoan(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        loanService.approveLoan(id);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Loan approved successfully."
        );

        return "redirect:/admin/loans";
    }

    @PostMapping("/reject/{id}")
    public String rejectLoan(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        loanService.rejectLoan(id);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Loan rejected successfully."
        );

        return "redirect:/admin/loans";
    }

    @PostMapping("/return/{id}")
    public String returnLoan(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        loanService.returnLoan(id);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Asset returned successfully."
        );

        return "redirect:/admin/loans";
    }



}


