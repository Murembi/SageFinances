package com.example.demo.controller;

import com.example.demo.dto.CreateUserRequestDTO;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

        // Regestering user
        @PostMapping("/register")
        public String register(
                @Valid @ModelAttribute CreateUserRequestDTO dto,
                BindingResult result,
                Model model) {
            if (result.hasErrors()) {
                return "register";
            }

            try {
                userService.createUser(dto);
                return "redirect:/users/login";
            } catch (RuntimeException e) {
                model.addAttribute("error", e.getMessage());
                return "register";
            }
        }
        // returns the login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    }
