package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
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
        return "redirect:/dashboard";
    }

}
