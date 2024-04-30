package com.example.tasktestravenapplicationtwo.validators;

import com.example.tasktestravenapplicationtwo.models.Customer;
import com.example.tasktestravenapplicationtwo.repositories.CustomerRepository;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UniqueEmailValidatorTest {

    @InjectMocks
    UniqueEmailValidator uniqueEmailValidator;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    ConstraintValidatorContext constraintValidatorContext;

    @Test
    public void testIsValid_EmailExists() {
        String email = "johndoe@example.com";

        Customer customer = new Customer();
        customer.setEmail(email);

        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(customer));

        boolean result = uniqueEmailValidator.isValid(email, constraintValidatorContext);

        assertFalse(result);
    }

    @Test
    public void testIsValid_EmailDoesNotExist() {
        String email = "johndoe@example.com";

        when(customerRepository.findByEmail(email)).thenReturn(Optional.empty());

        boolean result = uniqueEmailValidator.isValid(email, constraintValidatorContext);

        assertTrue(result);
    }
}