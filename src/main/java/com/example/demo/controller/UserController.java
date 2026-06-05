package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Registration with validation
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            // Collect validation errors
            List<String> errors = result.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errors);
        }

        userService.register(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    // Login Controller
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        boolean isAuthenticated = userService.login(user.getName(), user.getPasswordHash());
        if (isAuthenticated) {
            return "Login successful!";
        } else {
            return "Invalid username or password!";
        }
    }
}
