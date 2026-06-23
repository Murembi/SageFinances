//package com.example.demo.service;
//
//import com.example.demo.entity.User;
//import com.example.demo.repository.UserRepository;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class AuthService {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder encoder;
//
//    public AuthService(UserRepository userRepository, PasswordEncoder encoder) {
//        this.userRepository = userRepository;
//        this.encoder = encoder;
//    }
//
//    // 🔑 Handles login and sets authentication in Spring context
//    public boolean login(String email, String rawPassword) {
//        Optional<User> optionalUser = userRepository.findByEmail(email);
//
//        if (optionalUser.isEmpty()) {
//            return false;
//        }
//
//        User user = optionalUser.get();
//
//        // Validate password
//        if (!encoder.matches(rawPassword, user.getPasswordHash())) {
//            return false;
//        }
//
//        // Create authentication token
//        Authentication authentication = new UsernamePasswordAuthenticationToken(
//                user.getName(),
//                user.getPasswordHash(),
//                new CustomUserDetails(user).getAuthorities()
//        );
//
//        // Store authentication in Spring Security context
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        return true;
//
//        //  todo
//        // Registration Method
//
//        // This method handles user registration.
//        // It hashes the user's password before saving to the database.
//    }
//}
//
