package com.example.demo.dashboard.controller;

import com.example.demo.dashboard.service.AdminDashboardService;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AdminsDashboardController {

    private final AdminDashboardService adminDashboardService;
    private final UserService userService;

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {

        // shared dashboard stats only
        model.addAttribute("dashboard", adminDashboardService.getAdminDashboard());

        // shared UI info
        model.addAttribute("username", "admin");
        model.addAttribute("userRole", "ADMIN");

        return "adminDashboard";
    }
    @PostMapping("/admin/users/create")
    public String createUserFromAdmin(@ModelAttribute User user,
                                      RedirectAttributes redirectAttributes) {

        userService.createUserByAdmin(user);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "New user created in system."
        );

        return "redirect:/admin/users";
    }
}