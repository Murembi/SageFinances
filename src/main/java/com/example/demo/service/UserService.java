package com.example.demo.service;


import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    // Inject the UserRepository to interact with the database
    private final UserRepository userRepository;

    // BCryptPasswordEncoder is used to hash and verify passwords securely
    private final BCryptPasswordEncoder encoder ;


    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    // CREATE
    public User createUser(User user) {
        User newUser = User.builder()
                .name(user.getName())
                .department(user.getDepartment())
                .email(user.getEmail())

                .passwordHash(encoder.encode(user.getPasswordHash()))
                .createdAt(LocalDateTime.now())
                .role(User.Role.Borrower)

                .build();
        return userRepository.save(newUser);
    }

    // READ
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    // the entire user
    public User updateUser(Long id, User updatedUser) {
        User existingUser = getUserById(id);
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setDepartment(updatedUser.getDepartment());
        existingUser.setRole(updatedUser.getRole());
        return userRepository.save(existingUser);
    }

    // UPDATE SPECIFIC THINGS ABOUT THE USER
    // Update only the user's name
    public User updateUserName(Long id, String newName) {
        User user = getUserById(id);
        user.setName(newName);
        return userRepository.save(user);
    }

    // Update only the user's department
    public User updateUserDepartment(Long id, String newDepartment) {
        User user = getUserById(id);
        user.setDepartment(newDepartment);
        return userRepository.save(user);
    }

    // Update only the user's email
    public User updateUserEmail(Long id, String newEmail) {
        User user = getUserById(id);
        user.setEmail(newEmail);
        return userRepository.save(user);
    }

    // Update only the user's password
    public User updateUserPassword(Long id, String newPassword) {
        User user = getUserById(id);
        user.setPasswordHash(encoder.encode(newPassword));
        return userRepository.save(user);
    }

    // Update only the user's role
    public User updateUserRole(Long id, User.Role newRole) {
        User user = getUserById(id);
        user.setRole(newRole);
        return userRepository.save(user);
    }

    // DELETE
    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }

    //Login Authentication - Aaliyah
    public LoginResponseDTO login(LoginRequestDTO loginDTO) {

        // 1. Find user by email
        Optional<User> userOptional = userRepository.findByEmail(loginDTO.getEmail());

        if (userOptional.isEmpty()) {
            return new LoginResponseDTO("User not found", false);
        }

        User user = userOptional.get();

        // 2. Check password
        boolean passwordMatches = encoder.matches(
                loginDTO.getPassword(),
                user.getPasswordHash()
        );

        if (!passwordMatches) {
            return new LoginResponseDTO("Invalid password", false);
        }

        // 3. Success login
        return new LoginResponseDTO("Login successful", true);
    }
}
