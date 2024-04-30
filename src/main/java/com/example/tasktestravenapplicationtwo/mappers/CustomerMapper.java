package com.example.tasktestravenapplicationtwo.mappers;

import com.example.tasktestravenapplicationtwo.dto.CustomerDTO;
import com.example.tasktestravenapplicationtwo.models.Customer;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between Customer and CustomerDTO objects.
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerDTO customerToCustomerDTO(Customer customer);

    Customer customerDTOToCustomer(CustomerDTO customerDTO);
}
