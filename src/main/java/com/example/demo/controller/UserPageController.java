package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserPageController {

    private final UserService userService;

    public UserPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/users")
    public String adminUserPage(@RequestParam(required = false) String keyword,
                                Model model) {

        List<User> users;

        if (keyword != null && !keyword.trim().isEmpty()) {
            users = userService.searchUsers(keyword);
        } else {
            users = userService.getAllUsers();
        }

        model.addAttribute("users", users);
        model.addAttribute("keyword", keyword);

        return "adminUserPage";
    }
}