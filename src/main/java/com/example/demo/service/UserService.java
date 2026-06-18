package com.example.demo.service;


import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;


//Hashing Password

@Service
public class UserService {

    // Inject the UserRepository to interact with the database
    private final UserRepository userRepository;

    // Password encoder removed to avoid dependency on Spring Security here.

   // Constructor-based dependency injection
   // BCryptPasswordEncoder encoder

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // CREATE
    public User createUser(User user) {
        User newUser = User.builder()
                .name(user.getName())
                .department(user.getDepartment())
                .email(user.getEmail())
                .passwordHash(user.getPasswordHash())
                .createdAt(LocalDateTime.now())
                .role(User.Role.BORROWER)

                .build();

        return userRepository.save(newUser);
    }

    // READ
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public  User getUserByLoginDetails(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Compare provided password with stored password (should be hashed in production)
        if (user.getPasswordHash() != null && user.getPasswordHash().equals(password)) {
            return user;
        }
        throw new RuntimeException("Invalid credentials");

    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    //
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
        user.setPasswordHash(newPassword);
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

    public List<User> searchUsers(String keyword) {
        return userRepository.findAll().stream()
                .filter(user ->
                        user.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                                user.getEmail().toLowerCase().contains(keyword.toLowerCase()) ||
                                user.getDepartment().toLowerCase().contains(keyword.toLowerCase()) ||
                                user.getRole().name().toLowerCase().contains(keyword.toLowerCase())
                )
                .toList();
    }
}
