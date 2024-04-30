package com.example.tasktestravenapplicationtwo.exceptions;

/**
 * Custom exception class for customer-related errors.
 */
public class CustomerException extends IllegalArgumentException {
    public CustomerException(String message) {
        super(message);
    }
}
