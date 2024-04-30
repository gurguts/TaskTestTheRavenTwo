package com.example.tasktestravenapplicationtwo.services;

import com.example.tasktestravenapplicationtwo.dto.CustomerDTO;
import com.example.tasktestravenapplicationtwo.exceptions.CustomerException;
import com.example.tasktestravenapplicationtwo.mappers.CustomerMapper;
import com.example.tasktestravenapplicationtwo.models.Customer;
import com.example.tasktestravenapplicationtwo.repositories.CustomerRepository;
import com.example.tasktestravenapplicationtwo.services.impl.ICustomerService;
import com.example.tasktestravenapplicationtwo.validators.CustomerUpdateValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service class for managing customers.
 * Provides methods for creating, reading, updating, and deleting customers.
 * Uses CustomerRepository for data access, CustomerMapper for data conversion, and CustomerUpdateValidator
 * for validation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CustomerUpdateValidator customerUpdateValidator;

    /**
     * Creates a new customer.
     * Sets the creation time and active status, saves the customer to the repository,
     * and returns the corresponding CustomerDTO.
     *
     * @param customer The Customer object to be created.
     * @return The created CustomerDTO object.
     */
    public CustomerDTO createCustomer(Customer customer) {
        customer.setCreated(System.currentTimeMillis());
        customer.setIsActive(true);
        customerRepository.save(customer);
        log.info("Customer with email {} created successfully", customer.getEmail());
        return customerMapper.customerToCustomerDTO(customer);
    }

    /**
     * Retrieves all active customers.
     * Returns a list of CustomerDTO objects.
     *
     * @return A list of all active CustomerDTO objects.
     */
    public List<CustomerDTO> readAllCustomers() {
        log.debug("Reading all active customers");
        Iterable<Customer> customers = customerRepository.findByIsActiveTrue();
        return StreamSupport.stream(customers.spliterator(), false)
                .map(customerMapper::customerToCustomerDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific active customer by ID.
     * Throws a CustomerException if the customer is not found or is inactive.
     * Returns the corresponding CustomerDTO object.
     *
     * @param id The ID of the customer to be retrieved.
     * @return The CustomerDTO object of the active customer.
     */
    public CustomerDTO readCustomer(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isEmpty() || !customerOptional.get().getIsActive()) {
            throw new CustomerException(String.format("No customer found or inactive with id: %d", id));
        }
        log.info("Active customer found with id: {}", id);
        return customerMapper.customerToCustomerDTO(customerOptional.get());
    }

    /**
     * Updates a specific customer by ID.
     * Validates the input parameters, sets the updated fields, saves the customer to the repository,
     * and returns the corresponding CustomerDTO.
     *
     * @param id       The ID of the customer to be updated.
     * @param oldId    The old ID of the customer.
     * @param fullName The updated full name of the customer.
     * @param email    The updated email of the customer.
     * @param phone    The updated phone number of the customer.
     * @return The updated CustomerDTO object.
     */
    public CustomerDTO updateCustomer(Long id, Long oldId, String fullName, String email, String phone) {

        Customer customer = customerUpdateValidator.validate(id, oldId, email);

        log.debug("Updating customer with id: {}", id);

        customer.setFullName(fullName);
        customer.setPhone(phone);
        customer.setUpdated(System.currentTimeMillis());
        customer = customerRepository.save(customer);

        log.info("Customer with id {} updated successfully", id);
        return customerMapper.customerToCustomerDTO(customer);
    }

    /**
     * Deactivates a specific customer by ID.
     * Throws a CustomerException if the customer does not exist or is already inactive.
     *
     * @param id The ID of the customer to be deactivated.
     */
    public void deleteCustomer(Long id) {
        log.debug("Deactivating customer with id: {}", id);
        if (!customerRepository.existsByIdAndIsActiveIsTrue(id)) {
            throw new CustomerException(String.format("Customer with %d does not exist", id));
        }
        customerRepository.deactivateCustomer(id);
    }
}

