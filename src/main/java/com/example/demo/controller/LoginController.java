package com.example.demo.controller;

import com.example.demo.dto.DashboardDTO;
import com.example.demo.dto.LoginRequestDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    //Includes Validation
    @PostMapping("/auth/login")
    public String logIn(
            @Valid @ModelAttribute LoginRequestDTO loginDTO,
            BindingResult result,
            HttpSession session, Model model
            ) {
        if (result.hasErrors()) {
            return "login";
        }

        try{

        User user = userService.getUserByLoginDetails(loginDTO.getEmail(),
                loginDTO.getPassword());
        session.setAttribute("user", user);

        // redirection after logging
        if (user.getRole() == User.Role.ADMIN) {
            return "redirect:/admin/dashboard";
        }

        if (user.getRole() == User.Role.MANAGER) {
            return "redirect:/manager/dashboard";
        }
        return "redirect:/user-dashboard";

    } catch (RuntimeException e) {

            model.addAttribute("error", e.getMessage());

            return "login";
        }
    }

    @GetMapping("/user-dashboard")
    public String showUserDashboard(Model model, HttpSession session) {

        User loggedInUser = (User) session.getAttribute("user");

        if (loggedInUser == null) {
            return "redirect:/loginpage";

        }
        // Check if account is inactive/deleted
        if (loggedInUser.getStatus() == User.UserStatus.DELETED) {

            session.invalidate();

            return "redirect:/loginpage?error=inactive";
        }

        Long userId = loggedInUser.getUserId();

        DashboardDTO dashboard = userDashboardService.getUserDashboard(userId);

        model.addAttribute("user", loggedInUser);

        model.addAttribute("availableAssetsCount", dashboard.getAvailableAssets());
        model.addAttribute("myLoans", dashboard.getMyLoans());
        model.addAttribute("myPendingRequests", dashboard.getMyPendingRequests());

        model.addAttribute("availableAssets", userDashboardService.getAvailableAssets());
        model.addAttribute("pendingLoans", userDashboardService.getMyPendingRequests(userId));
        model.addAttribute("loanedAssets", userDashboardService.getMyLoanedAssets(userId));
        return "user-dashboard";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginpage";
    }
}
