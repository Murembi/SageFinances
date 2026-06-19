package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/users")
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
        }

        //WORKS
        @GetMapping("/register")
        public String showRegisterPage() {
            return "register";
        }

        // WORKS redirects to the login page
        @PostMapping("/register")
        public String register(@ModelAttribute User user) {
            userService.createUser(user);
            return "redirect:/users/login";
        }

        // returns the login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    }
