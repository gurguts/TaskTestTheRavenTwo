package com.example.tasktestravenapplicationtwo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * CustomerDTO with Lombok annotations to concisely represent the customer information being updated using
 * auto-generated code.
 */
@Data
public class CustomerUpdateDTO {
    private Long id;
    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 50, message = "Full name must be between 2 and 50 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(min = 2, max = 100, message = "Email must be between 2 and 100 characters")
    private String email;

    @Pattern(regexp = "^\\+[0-9]{6,14}$", message = "Phone number should start with + and contain only digits, " +
            "between 6 and 14 characters")
    private String phone;
}

