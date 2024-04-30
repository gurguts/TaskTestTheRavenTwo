package com.example.tasktestravenapplicationtwo.validators;

import com.example.tasktestravenapplicationtwo.exceptions.CustomerException;
import com.example.tasktestravenapplicationtwo.models.Customer;
import com.example.tasktestravenapplicationtwo.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for validating updated customer data.
 * Provides a method to validate the ID and email of a customer before updating.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerUpdateValidator {
    private final CustomerRepository customerRepository;

    /**
     * Validates the updated customer data.
     * Checks if the IDs match, if the customer exists, and if the emails match.
     * Throws a CustomerException if any of these checks fail.
     *
     * @param id    The ID of the customer to be updated.
     * @param oldId The old ID of the customer.
     * @param email The email of the customer.
     * @return The Customer object if all checks pass.
     */
    public Customer validate(Long id, Long oldId, String email) {
        log.info("Validation of updated data has begun");
        if (!id.equals(oldId)) {
            throw new CustomerException("IDs don't match");
        }

        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isEmpty()) {
            throw new CustomerException(String.format("Customer with id %d not found", id));
        }

        Customer customer = customerOptional.get();
        if (!customer.getEmail().equals(email)) {
            throw new CustomerException("Email doesn't match");
        }
        return customer;
    }
}
