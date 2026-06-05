package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

//Hashing Password
@Service
public class UserService {

    // Inject the UserRepository to interact with the database
    private final UserRepository userRepository;

    // BCryptPasswordEncoder is used to hash and verify passwords securely
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Constructor-based dependency injection
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Registration Method

    // This method handles user registration.
    // It hashes the user's password before saving to the database.
    public User register(User user) {
        // Hash the raw password and set it on the user object
        user.setPasswordHash(encoder.encode(user.getPasswordHash()));

        // Save the user to the database and return the saved entity
        return userRepository.save(user);
    }

    // Login Method

    // This method verifies user credentials during login.
    // It checks if the username exists and compares the raw password

    public boolean login(String name, String rawPassword) {
        // Retrieve the user from the database using Optional to avoid null errors
        Optional<User> optionalUser = userRepository.findByUsername(name);

        // If no user is found, return false
        if (optionalUser.isEmpty()) {
            return false;
        }

        // Extract the user object from Optional
        User user = optionalUser.get();

        // Compare the raw password with the hashed password
        return encoder.matches(rawPassword, user.getPasswordHash());
    }
}

