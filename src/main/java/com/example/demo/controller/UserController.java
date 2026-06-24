//package com.example.demo.controller;
//
//import com.example.demo.entity.User;
//import com.example.demo.service.UserService;
//import java.util.List;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/users")
//public class UserController {
//
//    private final UserService userService;
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    // Registration with validation
//    // CREATE User(Register)
//    @PostMapping()
//    public ResponseEntity<?> createUser(@RequestBody User user) {
//        userService.createUser(user);
//        return ResponseEntity.ok("User registered successfully!");
//    }
//
//    // READ (Get all users)
//    //DONE
//    @GetMapping
//    public List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }
//
//    // READ (Get user by ID)
//    // done
//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable Long id) {
//        return ResponseEntity.ok(userService.getUserById(id));
//    }
//
//    // UPDATE (Edit user)
//    // Update name
//    @PatchMapping("/name/{id}")
//    public User updateName(@PathVariable Long id, @RequestBody String newName) {
//        return userService.updateUserName(id, newName);
//    }
//
//    // Update department
//    @PatchMapping("/department/{id}")
//    public User updateDepartment(@PathVariable Long id, @RequestBody String newDepartment) {
//        return userService.updateUserDepartment(id, newDepartment);
//    }
//
//    // Update email
//    @PatchMapping("/email/{id}")
//    public User updateEmail(@PathVariable Long id, @RequestBody String newEmail) {
//        return userService.updateUserEmail(id, newEmail);
//    }
//
//    // Update password
//    @PatchMapping("/password/{id}")
//    public User updatePassword(@PathVariable Long id, @RequestBody String newPassword) {
//        return userService.updateUserPassword(id, newPassword);
//    }
//
//    //DTO Wrapping
////    public static class RoleUpdateRequest {
////        private User.Role role;
////        public User.Role getRole() {
////            return role;
////        }
////        public void setRole(User.Role role) {
////            this.role = role;
////        }
////    }
//    // Update role
//    @PatchMapping("/role/{id}")
//    public User updateRole(@PathVariable Long id,
//                           @RequestBody User.Role newRole) {
//        return userService.updateUserRole(id, newRole);
//    }
//
//    @PutMapping("/{id}")
//    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
//        return userService.updateUser(id, updatedUser);
//    }
//
//    // DELETE (Remove user)
//    //done
//    @DeleteMapping("/{id}")
//    public String deleteUser(@PathVariable Long id) {
//        userService.deleteUser(id);
//        return "User deleted successfully!";
//    }
//}