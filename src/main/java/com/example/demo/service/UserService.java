package com.example.demo.service;


import com.example.demo.dto.UserCreationResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.*;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


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
    @Transactional
    public User createUser(User user) {
        if (!user.getEmail().toLowerCase().endsWith("@sageassets.co.za")) {
            throw new RuntimeException(
                    "Email must end with @sageassets.co.za"
            );
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(
                    "Email " + user.getEmail() + " already exists."
            );
        }
        User newUser = User.builder()
                .name(user.getName())
                .department(user.getDepartment())
                .email(user.getEmail())
                .passwordHash(user.getPasswordHash())
                .createdAt(LocalDateTime.now())
                .role(User.Role.BORROWER)
                .status(User.UserStatus.ACTIVE)

                .build();

        return userRepository.save(newUser);
    }

    @Transactional
    public UserCreationResponse createUserByAdmin(User user) {

        if (!user.getEmail().toLowerCase().endsWith("@sageassets.co.za")) {
            throw new InvalidEmailException(
                    "Email must end with @sageassets.co.za"
            );
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(
                    "Email " + user.getEmail() + " already exists."
            );
        }
        String generatedPassword = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 8);


        User newUser = User.builder()
                .name(user.getName())
                .department(user.getDepartment())
                .email(user.getEmail())
                .passwordHash(generatedPassword)
                .createdAt(LocalDateTime.now())
                .role(user.getRole())
                .status(User.UserStatus.ACTIVE)
                .build();

        User savedUser = userRepository.save(newUser);
        return new UserCreationResponse(
                savedUser,
                generatedPassword
        );
    }
    public void resetPassword(
            String email,
            String newPassword,
            String confirmPassword) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(
                "No user found with email: " + email
        ));

        if (!newPassword.equals(confirmPassword)) {
            throw new InvalidCredentialsException(
                    "Passwords do not match."
            );
        }

        user.setPasswordHash(newPassword);
        userRepository.save(user);
    }

    // READ
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public  User getUserByLoginDetails(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials."));

        if (user.getStatus() == User.UserStatus.DELETED ||
                user.getStatus() == User.UserStatus.INACTIVE) {
            throw new AccountInactiveException(
                    "Account is inactive."
            );
        }

        if (user.getPasswordHash() != null && user.getPasswordHash().equals(password)) {
            return user;
        }
        throw new InvalidCredentialsException(
                "Invalid credentials."
        );

    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(
                 id ));
    }


    // Update only the user's role
    //used
    public User updateUserRole(Long id, User.Role newRole) {
        User user = getUserById(id);
        user.setRole(newRole);
        return userRepository.save(user);
    }

    // DELETE //NEED TO KEEP HISTORY/RECORDS
    // implemenet a new soft delete
    // prevents systems from crashing
    // SOFT DELETE
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        userId ));

        user.setStatus(User.UserStatus.DELETED);
        userRepository.save(user);
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
    //cannot delete a user because of the foreign key


}
