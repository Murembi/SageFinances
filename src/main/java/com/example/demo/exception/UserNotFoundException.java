package com.example.demo.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException(Long userId) {
        super("User with ID " + userId + " not found.");
    }
}