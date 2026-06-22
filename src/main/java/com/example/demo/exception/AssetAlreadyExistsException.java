package com.example.demo.exception;

public class AssetAlreadyExistsException extends RuntimeException {
    public AssetAlreadyExistsException(String message) {
        super(message);
    }
}
