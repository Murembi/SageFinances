package com.example.demo.dashboard.controller;

import com.example.demo.dashboard.service.AdminDashboardService;
import com.example.demo.dto.UserCreationResponse;
import com.example.demo.entity.User;
import com.example.demo.service.AssetService;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
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
    private final AssetService assetService;


    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        //when user not logged in
        if (user == null) {
            return "redirect:/loginpage";
        }
        //when the role is not admin
        if (user.getRole() != User.Role.ADMIN) {
            return "redirect:/loginpage";
        }
        // shared dashboard stats only
        model.addAttribute("dashboard", adminDashboardService.getAdminDashboard());

        // shared UI info
        model.addAttribute("username", user.getName());
        model.addAttribute("userRole", user.getRole());

        return "adminDashboard";
    }

    @PostMapping("/admin/users/create")
    public String createUserFromAdmin(@ModelAttribute User user,
                                      RedirectAttributes redirectAttributes) {

        UserCreationResponse response =
                userService.createUserByAdmin(user);

        redirectAttributes.addFlashAttribute(
                "generatedEmail",
                response.getUser().getEmail()
        );

        redirectAttributes.addFlashAttribute(
                "generatedPassword",
                response.getGeneratedPassword()
        );


        return "redirect:/admin/users";
    }

}