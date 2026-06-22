package com.example.demo.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AssetNotFoundException.class)
    public String handleAssetNotFound(
            AssetNotFoundException ex,
            Model model) {

        model.addAttribute(
                "errorMessage",
                ex.getMessage()
        );

        return "error";
    }

    @ExceptionHandler(LoanNotFoundException.class)
    public String handleLoanNotFound(
            LoanNotFoundException ex,
            Model model) {

        model.addAttribute(
                "errorMessage",
                ex.getMessage()
        );

        return "error";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFound(
            UserNotFoundException ex,
            Model model) {

        model.addAttribute(
                "errorMessage",
                ex.getMessage()
        );

        return "error";
    }

    @ExceptionHandler(AssetUnavailableException.class)
    public String handleAssetUnavailable(
            AssetUnavailableException ex,
            Model model) {

        model.addAttribute(
                "errorMessage",
                ex.getMessage()
        );

        return "error";
    }

    @ExceptionHandler(InvalidLoanActionException.class)
    public String handleInvalidLoanAction(
            InvalidLoanActionException ex,
            Model model) {

        model.addAttribute(
                "errorMessage",
                ex.getMessage()
        );

        return "error";
    }

    @ExceptionHandler(AssetAlreadyExistsException.class)
    public String handleAssetAlreadyExists(
            AssetAlreadyExistsException ex,
            Model model) {

        model.addAttribute(
                "errorMessage",
                ex.getMessage()
        );

        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(
            Exception ex,
            Model model) {

        model.addAttribute(
                "errorMessage",
                "Unexpected error occurred."
        );

        return "error";
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserAlreadyExists(
            UserAlreadyExistsException ex,
            Model model) {

        model.addAttribute("errorMessage", ex.getMessage());

        return "error";
    }
}
