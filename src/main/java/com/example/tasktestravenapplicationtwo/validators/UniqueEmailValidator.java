package com.example.tasktestravenapplicationtwo.validators;

import com.example.tasktestravenapplicationtwo.annotations.UniqueEmail;
import com.example.tasktestravenapplicationtwo.repositories.CustomerRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

/**
 * Validator class for the UniqueEmail annotation.
 * Checks if an email is unique by querying the CustomerRepository.
 */
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final CustomerRepository customerRepository;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return customerRepository.findByEmail(value).isEmpty();
    }
}