package com.example.demo.service;


import com.example.demo.dto.CreateUserRequestDTO;
import com.example.demo.entity.User;
import com.example.demo.exception.AccountInactiveException;
import com.example.demo.exception.InvalidCredentialsException;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.LoanRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

import java.nio.file.Files;
import java.nio.file.Path;



//Hashing Password

@Service
public class UserService {

    // Inject the UserRepository to interact with the database
    private final UserRepository userRepository;

    private final EmailService emailService;

    // Password encoder removed to avoid dependency on Spring Security here.

   // Constructor-based dependency injection
   // BCryptPasswordEncoder encoder

    public UserService(UserRepository userRepository, EmailService emailService) 
    {
        this.userRepository = userRepository;
        this.emailService = emailService;
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
    public User createUserByAdmin(CreateUserRequestDTO dto) {

        if (!dto.getEmail().toLowerCase().endsWith("@sageassets.co.za")) 
        {
            throw new RuntimeException("Email must end with @sageassets.co.za");
        }

        if (userRepository.existsByEmail(dto.getEmail())) 
        {
            throw new UserAlreadyExistsException("Email " + dto.getEmail() + " already exists.");
        }



    String tempPassword = dto.getPasswordHash(); // raw password, before hashing

    User newUser = User.builder()
            .name(dto.getName())
            .department(dto.getDepartment())
            .email(dto.getEmail())
            .passwordHash(tempPassword)
            .createdAt(LocalDateTime.now())
            .role(dto.getRole())
            .status(User.UserStatus.ACTIVE)
            .build();

        String htmlBody = """
            <html>
            <body style="font-family: Arial, sans-serif; color: #deede8; max-width: 500px;">
                <h2 style="color: #2F5D50;">SAGE ASSETS | Assets Management System</h2>
                <p>Welcome %s,</p>
                <p>An administrator has created a profile for you on the Asset Management System.</p>

                <table style="background: #F1F7F3; border-radius: 8px; padding: 16px; margin: 16px 0;">
                    <tr><td style="padding: 4px 12px;"><strong>Email:</strong></td><td style="padding: 4px 12px;">%s</td></tr>
                    <tr><td style="padding: 4px 12px;"><strong>Temporary Password:</strong></td><td style="padding: 4px 12px;">%s</td></tr>
                </table>

                <p>Please log in and change your password as soon as possible.</p>

                <p style="margin-top: 24px;">
                    <a href="http://localhost:8083/loginpage"
                    style="background: #2F5D50; color: white; padding: 10px 18px;
                            border-radius: 8px; text-decoration: none;">
                        Log In Now
                    </a>
                </p>

                <p style="color: #6B7280; font-size: 13px; margin-top: 24px;">
                    If you weren't expecting this account, please contact your IT administrator.
                </p>
            </body>
        </html>
                """.formatted(newUser.getName(), newUser.getEmail(), tempPassword); //change getPasswordHash() to the auto generated


        User savedUser = userRepository.save(newUser);

        emailService.sendHtmlEmail(newUser.getEmail(), "Your Profile has been created", htmlBody);

      

        emailService.sendHtmlEmail(
    "hmonwabise@gmail.com",
    "Your Profile has been created",
    htmlBody
);
        //return userRepository.save(newUser);  //send the email before saving
        return savedUser; //email succeeds and database save too
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
        // Compare provided password with stored password (should be hashed in production)
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
