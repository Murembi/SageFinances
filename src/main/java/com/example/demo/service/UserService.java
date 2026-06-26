package com.example.demo.service;


import com.example.demo.dto.CreateUserRequestDTO;
import com.example.demo.service.EmailService;
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
    private final EmailService emailService;

    // Password encoder removed to avoid dependency on Spring Security here.

   // Constructor-based dependency injection
   // BCryptPasswordEncoder encoder

    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
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
                .passwordHash(user.getPasswordHash())
                .createdAt(LocalDateTime.now())
                .role(User.Role.BORROWER)
                .status(User.UserStatus.ACTIVE)

                .build();

        User savedUser = userRepository.save(newUser);

        String htmlBody = """
        <html>
        <body style="font-family: Arial, sans-serif; color:#2E2E2E; max-width: 500px;">
            <h2 style="color: #2F5D50;">Welcome to Sage Assets</h2>
            <p>Good day %s,</p>
            <p>Thank you for registering on the Asset Management System. Your account is now active.</p>
            <p>You can now log in and start managing assets.</p>
            <p style="margin-top: 24px;">
                <a href="http://localhost:8083/loginpage" 
                   style="background: #2F5D50; color: white; padding: 10px 18px; 
                   border-radius: 8px; text-decoration: none;">
                    Log In Now
                </a>
            </p>
        </body>
        </html>
        """.formatted(savedUser.getName());

        emailService.sendHtmlEmail(savedUser.getEmail(), "Welcome to Sage Assets", htmlBody);

        return savedUser;
    }

    @Transactional
    public UserCreationResponse createUserByAdmin(User user)
    {

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

        String htmlBody = """
        <html>
        <body style="font-family: Arial, sans-serif; color:#2E2E2E; max-width: 500px;">

            <h2 style="color: #2F5D50;">Welcome to the Asset Management System</h2>

            <p>Good day %s,</p>

            <p>An administrator has created a profile for you on the Asset Management System.</p>
                <table style="background: #F1F7F3; border-radius: 8px; padding: 16px; margin: 16px 0;">
                    <tr><td style="padding: 4px 12px;"><strong>Email:</strong></td><td style="padding: 4px 12px;">%s</td></tr>
                    <tr><td style="padding: 4px 12px;"><strong>Login Password:</strong></td><td style="padding: 4px 12px;">%s</td></tr>
                </table>

            <p>Please log in and change your password as soon as possible.</p>
            <p style="margin-top: 24px;">
                <a href="http://localhost:8083/loginpage"
                        style="background: #2F5D50; color: white; padding: 10px 18px;
                        border-radius: 8px; text-decoration: none;">
                    Log In Now
                </a>
            </p>
        </body>
        </html>
        """.formatted(savedUser.getName(), savedUser.getEmail(), generatedPassword);
        emailService.sendHtmlEmail(savedUser.getEmail(), "Your Asset Management System Account", htmlBody);

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
