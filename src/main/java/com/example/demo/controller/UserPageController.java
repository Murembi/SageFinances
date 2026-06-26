package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
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
                                HttpSession session,
                                Model model) {

        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != User.Role.ADMIN) {
            return "redirect:/loginpage";
        }

        List<User> users;

        if (keyword != null && !keyword.trim().isEmpty()) {
            users = userService.searchUsers(keyword);
        } else {
            users = userService.getAllUsers();
        }

        model.addAttribute("users", users);
        model.addAttribute("keyword", keyword);
        model.addAttribute("username", user.getName());
        model.addAttribute("userRole", user.getRole().name());

        return "adminUserPage";
    }

    @PostMapping("/update-role/{id}")
    public String updateUserRole(@PathVariable Long id,
                                 @RequestParam String role,
                                 HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != User.Role.ADMIN) {
            return "redirect:/loginpage";
        }

        userService.updateUserRole(
                id,
                User.Role.valueOf(role)
        );

        return "redirect:/admin/users";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id,
                             HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null || user.getRole() != User.Role.ADMIN) {
            return "redirect:/loginpage";
        }

        userService.deleteUser(id);

        return "redirect:/admin/users";
    }

}