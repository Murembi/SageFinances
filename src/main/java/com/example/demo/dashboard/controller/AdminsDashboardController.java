package com.example.demo.dashboard.controller;

import com.example.demo.dashboard.service.AdminDashboardService;
import com.example.demo.dto.UserCreationResponse;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.CreateUserRequestDTO;

@Controller
@RequiredArgsConstructor
public class AdminsDashboardController {

    private final AdminDashboardService adminDashboardService;
    private final UserService userService;

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
        model.addAttribute("username", "admin");
        model.addAttribute("userRole", "ADMIN");

        return "adminDashboard";
    }
    @PostMapping("/admin/users/create")
    public String createUserFromAdmin(@Valid @ModelAttribute("user") CreateUserRequestDTO dto, BindingResult result, Model model, RedirectAttributes redirectAttributes) 
    {

        if (result.hasErrors()) 
        {
            model.addAttribute("users", userService.getAllUsers());
            model.addAttribute("username", "admin");
            model.addAttribute("errorMessages",
            result.getAllErrors().stream()
                  .map(org.springframework.validation.ObjectError::getDefaultMessage)
                  .toList()); // BindingResult errors as a flat list in the adminUserPage


            return "adminUserPage"; // to user creation  
        }

        userService.createUserByAdmin(dto);

        model.addAttribute("generatedEmail", response.getUser().getEmail());
        model.addAttribute("generatedPassword", response.getGeneratedPassword());

        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("username", "admin");
        model.addAttribute("userRole", "ADMIN");

        return "adminUserPage";
    }

}