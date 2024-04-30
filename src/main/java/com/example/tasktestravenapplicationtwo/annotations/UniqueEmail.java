package com.example.tasktestravenapplicationtwo.annotations;

import com.example.tasktestravenapplicationtwo.validators.UniqueEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Annotation for validating unique email using UniqueEmailValidator.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {
    String message() default "Duplicate value found";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

