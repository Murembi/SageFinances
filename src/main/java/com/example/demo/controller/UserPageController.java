package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class UserPageController {

    private final UserService userService;

    public UserPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()//moved it up
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

    @PostMapping("/update-role/{id}")
    public String updateUserRole(@PathVariable Long id,
                                 @RequestParam String role) {

        userService.updateUserRole(
                id,
                User.Role.valueOf(role)
        );

        return "redirect:/admin/users";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return "redirect:/admin/users";
    }

}