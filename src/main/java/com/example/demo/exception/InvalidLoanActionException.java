package com.example.demo.exception;

public class InvalidLoanActionException extends RuntimeException {

    public InvalidLoanActionException(String message) {
        super(message);
    }
}