package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.dashboard.service.UserDashboardService;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class LoginController {

    private final UserService userService;
    private final UserDashboardService userDashboardService;

    public LoginController(UserService userService,  UserDashboardService userDashboardService) {
        this.userService = userService;
        this.userDashboardService = userDashboardService;
    }


    @GetMapping("/loginpage")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/auth/login")
    public String logIn(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session) {

        User user = userService.getUserByLoginDetails(email, password);
        session.setAttribute("user", user);
        return "redirect:/user-dashboard";
    }

    @GetMapping("/user-dashboard")
    public String showUserDashboard(Model model, HttpSession session) {

        User loggedInUser = (User) session.getAttribute("user");

        if (loggedInUser == null) {
            return "redirect:/loginpage";
        }

        Long userId = loggedInUser.getUserId();

        model.addAttribute("dashboard", userDashboardService.getUserDashboard(userId));
        model.addAttribute("availableAssets", userDashboardService.getAvailableAssets());
        model.addAttribute("pendingLoans", userDashboardService.getMyPendingRequests(userId));
        model.addAttribute("loanedAssets", userDashboardService.getMyLoanedAssets(userId));

        return "user-dashboard";
    }
}
