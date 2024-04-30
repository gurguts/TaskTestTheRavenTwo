package com.example.tasktestravenapplicationtwo.restControllers;

import com.example.tasktestravenapplicationtwo.dto.CustomerDTO;
import com.example.tasktestravenapplicationtwo.dto.CustomerUpdateDTO;
import com.example.tasktestravenapplicationtwo.mappers.CustomerMapper;
import com.example.tasktestravenapplicationtwo.services.impl.ICustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This Spring @RestController, CustomerRestController, handles CRUD operations for customers.
 * It uses a CustomerService to create, read, update, and delete customer data.
 * It uses a custom Mapper to transform DTO objects
 */
@Slf4j
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final ICustomerService customerService;
    private final CustomerMapper customerMapper;

    /**
     * Handles POST requests to create a new customer.
     * Validates the input CustomerDTO and returns the created CustomerDTO.
     *
     * @param customerDTO The CustomerDTO object to be created.
     * @return The created CustomerDTO object wrapped in a ResponseEntity.
     */
    @PostMapping
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        log.debug("Creating customer with email: {}", customerDTO.getEmail());
        CustomerDTO newCustomer = customerService.createCustomer(customerMapper.customerDTOToCustomer(customerDTO));
        return ResponseEntity.ok(newCustomer);
    }

    /**
     * Handles GET requests to retrieve all active customers.
     * Returns a list of CustomerDTO objects wrapped in a ResponseEntity.
     *
     * @return A ResponseEntity containing a list of all active CustomerDTO objects.
     */
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> readAllCustomers() {
        log.debug("Reading all active customers");
        List<CustomerDTO> customers = customerService.readAllCustomers();
        log.info("Found {} active customers", customers.size());
        return ResponseEntity.ok(customers);
    }

    /**
     * Handles GET requests to retrieve a specific customer by ID.
     * Returns the CustomerDTO object wrapped in a ResponseEntity.
     *
     * @param id The ID of the customer to be retrieved.
     * @return A ResponseEntity containing the CustomerDTO object.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> readCustomer(@PathVariable Long id) {
        log.debug("Reading customer with id: {}", id);
        return ResponseEntity.ok(customerService.readCustomer(id));
    }

    /**
     * Handles PUT requests to update a specific customer by ID.
     * Validates the input CustomerUpdateDTO and returns the updated CustomerDTO.
     *
     * @param id                The ID of the customer to be updated.
     * @param customerUpdateDTO The CustomerUpdateDTO object with updated fields.
     * @return A ResponseEntity containing the updated CustomerDTO object.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(
            @PathVariable Long id, @Valid @RequestBody CustomerUpdateDTO customerUpdateDTO) {
        log.debug("Updating customer with id: {}", id);

        return ResponseEntity.ok(customerService.updateCustomer(id, customerUpdateDTO.getId(),
                customerUpdateDTO.getFullName(), customerUpdateDTO.getEmail(), customerUpdateDTO.getPhone()));
    }

    /**
     * Handles DELETE requests to deactivate a specific customer by ID.
     * Returns a success message wrapped in a ResponseEntity.
     *
     * @param id The ID of the customer to be deactivated.
     * @return A ResponseEntity containing a success message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        log.debug("Deactivating customer with id: {}", id);
        customerService.deleteCustomer(id);
        log.info("Customer with id {} deactivated successfully", id);
        return ResponseEntity.ok(String.format("Customer with id %d deleted", id));
    }
}
