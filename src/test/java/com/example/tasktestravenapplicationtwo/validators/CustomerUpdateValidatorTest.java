package com.example.tasktestravenapplicationtwo.validators;

import com.example.tasktestravenapplicationtwo.exceptions.CustomerException;
import com.example.tasktestravenapplicationtwo.models.Customer;
import com.example.tasktestravenapplicationtwo.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerUpdateValidatorTest {

    @InjectMocks
    CustomerUpdateValidator customerUpdateValidator;

    @Mock
    CustomerRepository customerRepository;

    @Test
    public void testValidate() {
        Long id = 1L;
        Long oldId = 1L;
        String email = "johndoe@example.com";

        Customer customer = new Customer();
        customer.setId(id);
        customer.setEmail(email);

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        Customer result = customerUpdateValidator.validate(id, oldId, email);

        assertEquals(customer, result);
    }

    @Test
    public void testValidate_IdMismatch() {
        Long id = 1L;
        Long oldId = 2L;
        String email = "johndoe@example.com";

        assertThrows(CustomerException.class, () -> customerUpdateValidator.validate(id, oldId, email));
    }

    @Test
    public void testValidate_NotFound() {
        Long id = 1L;
        Long oldId = 1L;
        String email = "johndoe@example.com";

        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CustomerException.class, () -> customerUpdateValidator.validate(id, oldId, email));
    }

    @Test
    public void testValidate_EmailMismatch() {
        Long id = 1L;
        Long oldId = 1L;
        String email = "johndoe@example.com";

        Customer customer = new Customer();
        customer.setId(id);
        customer.setEmail("janedoe@example.com");

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        assertThrows(CustomerException.class, () -> customerUpdateValidator.validate(id, oldId, email));
    }
}