package com.example.tasktestravenapplicationtwo.advices;

import com.example.tasktestravenapplicationtwo.exceptions.CustomerException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles global exceptions for the application.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * Handles the given method argument not valid exception and returns a response.
     *
     * @param ex The exception to handle.
     * @return A map containing the details of the exception.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            log.error("Validation error in field '{}': {}", fieldName, errorMessage);
        });
        return errors;
    }

    /**
     * Handles the given constraint violation exception and returns a response.
     *
     * @param ex The exception to handle.
     * @return A map containing the details of the exception.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error("ConstraintViolationException occurred: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return errors;
    }

    /**
     * Handles the given customer exception and returns a response.
     *
     * @param ex The exception to handle.
     * @return A map containing the details of the exception.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomerException.class)
    public Map<String, String> handleCustomerException(CustomerException ex) {
        log.error("CustomerException occurred: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return errors;
    }

    /**
     * Handles the given exception and returns a response.
     *
     * @param ex The exception to handle.
     * @return A map containing the details of the exception.
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, String> handleAllOtherExceptions(Exception ex) {
        log.error("Unexpected error occurred: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return errors;
    }
}