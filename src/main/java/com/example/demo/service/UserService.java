package com.example.demo.service;


import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.UserCreationResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.*;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


//Hashing Password

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
    @Transactional
    public User createUser(User user) {

        validatePassword(user.getPasswordHash());


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
                .passwordHash(encoder.encode(user.getPasswordHash()))
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
                .passwordHash(encoder.encode(generatedPassword))
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
    // Update only the user's password
    public User updateUserPassword(Long id, String newPassword) {
        User user = getUserById(id);
        user.setPasswordHash(encoder.encode(newPassword));
        return userRepository.save(user);
    }

    //password hashing
    public LoginResponseDTO login(LoginDTO loginDTO) {

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

        if (newPassword.length() < 8) {
            throw new IllegalArgumentException("Password too short");
        }

        // HASH THE PASSWORD BEFORE SAVING
        user.setPasswordHash(
                encoder.encode(newPassword)
        );

        userRepository.save(user);
    }

    // READ
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByLoginDetails(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials."));

        if (user.getStatus() == User.UserStatus.DELETED ||
                user.getStatus() == User.UserStatus.INACTIVE) {
            throw new AccountInactiveException(
                    "Account is inactive."
            );
        }

        if (user.getPasswordHash() != null && encoder.matches(password, user.getPasswordHash())) {
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
    private void validatePassword(String password) {

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password is required.");
        }

        String passwordPattern =
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&!#^()_+\\-=]).{8,}$";

        if (!password.matches(passwordPattern)) {
            throw new IllegalArgumentException(
                    "Password must be at least 8 characters long and contain an uppercase letter, lowercase letter, number, and special character."
            );
        }
    }
}
