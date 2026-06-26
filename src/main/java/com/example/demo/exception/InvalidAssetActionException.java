package com.example.demo.exception;

public class InvalidAssetActionException extends RuntimeException {
    public InvalidAssetActionException(String message) {
        super(message);
    }
}
