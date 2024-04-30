package com.example.tasktestravenapplicationtwo.repositories;

import com.example.tasktestravenapplicationtwo.models.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Repository interface for Customer with query methods for active customers and email lookup.
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    /**
     * Finds all active customers.
     *
     * @return An Iterable of Customer objects that are active.
     */
    Iterable<Customer> findByIsActiveTrue();

    /**
     * Finds a customer by their email.
     *
     * @param email The email of the customer to be found.
     * @return An Optional containing the Customer object if found.
     */
    Optional<Customer> findByEmail(String email);

    /**
     * Checks if an active customer exists by their ID.
     *
     * @param id The ID of the customer to be checked.
     * @return True if an active customer with the given ID exists, false otherwise.
     */
    boolean existsByIdAndIsActiveIsTrue(Long id);

    /**
     * Deactivates a specific customer by their ID.
     * This is done by setting the 'isActive' field of the customer to false.
     *
     * @param id The ID of the customer to be deactivated.
     */
    @Transactional
    @Modifying
    @Query("update Customer c set c.isActive = false where c.id = ?1")
    void deactivateCustomer(Long id);
}
