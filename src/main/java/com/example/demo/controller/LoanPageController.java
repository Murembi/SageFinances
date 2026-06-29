package com.example.demo.controller;

import com.example.demo.dto.LoanRequestDTO;
import com.example.demo.entity.Loan;
import com.example.demo.entity.User;
import com.example.demo.service.AssetService;
import com.example.demo.service.LoanService;
import jakarta.servlet.http.HttpSession;
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
    public String showLoanPage(Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user == null || user.getRole() != User.Role.ADMIN) {
            return "redirect:/loginpage";
        }
        model.addAttribute(
                "assets",
                assetService.getAvailableAssets()
        );

        model.addAttribute(
                "loans",
                loanService.getLoansByStatus(Loan.Status.APPROVED, user));

        model.addAttribute(
                "requests",
                loanService.getLoansByStatus(
                        Loan.Status.PENDING, user));

        model.addAttribute("returnedLoans",
                loanService.getReturnedLoans());

        return "loanPage";
    }

    //MODIFIED AND WORKS
    @PostMapping("/request")
    public String requestLoan(
            @RequestParam Long userId,
            @RequestParam Long assetId,
            HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != User.Role.ADMIN) {
            return "redirect:/loginpage";
        }

        LoanRequestDTO dto = new LoanRequestDTO();

        dto.setUserId(userId);
        dto.setAssetId(assetId);

        loanService.createLoanRequest(dto, user);

        return "redirect:/admin/loans";
    }


    @PostMapping("/return/{id}")
    public String returnLoan(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");

        if (user == null || user.getRole() != User.Role.ADMIN) {
            return "redirect:/loginpage";
        }

        redirectAttributes.addFlashAttribute(
                "adminLoanPageSuccessMessage",
                "Asset returned successfully."
        );
        loanService.returnLoan(id, user);

        return "redirect:/admin/loans";
    }

    @PostMapping("/approve/{id}")
    public String approveLoan(
            @PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {

        User user = (User) session.getAttribute("user");

        if (user == null || user.getRole() != User.Role.ADMIN) {
            return "redirect:/loginpage";
        }
        loanService.approveLoan(id, user);

        redirectAttributes.addFlashAttribute(
                "adminLoanPageSuccessMessage",
                "Loan approved successfully."
        );

        return "redirect:/admin/loans";
    }

    @PostMapping("/reject/{id}")
    public String rejectLoan(
            @PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");

        if (user == null || user.getRole() != User.Role.ADMIN) {
            return "redirect:/loginpage";
        }
        loanService.rejectLoan(id, user);

        redirectAttributes.addFlashAttribute(
                "adminLoanPageSuccessMessage",
                "Loan rejected successfully."
        );

        return "redirect:/admin/loans";
    }

    @PostMapping("/delete/{id}")
    public String deleteLoan(
            @PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null || user.getRole() != User.Role.ADMIN) {
            return "redirect:/loginpage";
        }
        loanService.deleteLoan(id, user);

        return "redirect:/admin/loans";
    }



}


