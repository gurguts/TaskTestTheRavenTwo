package com.example.tasktestravenapplicationtwo.services.impl;

import com.example.tasktestravenapplicationtwo.dto.CustomerDTO;
import com.example.tasktestravenapplicationtwo.models.Customer;

import java.util.List;

/**
 * Interface for customer service operations.
 * Provides methods for creating, reading, updating, and deleting customers.
 */
public interface ICustomerService {
    CustomerDTO createCustomer(Customer customer);

    List<CustomerDTO> readAllCustomers();

    CustomerDTO readCustomer(Long id);

    CustomerDTO updateCustomer(Long id, Long oldId, String fullName, String email, String phone);

    void deleteCustomer(Long id);
}
