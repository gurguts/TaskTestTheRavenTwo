package com.example.tasktestravenapplicationtwo.services;

import com.example.tasktestravenapplicationtwo.dto.CustomerDTO;
import com.example.tasktestravenapplicationtwo.exceptions.CustomerException;
import com.example.tasktestravenapplicationtwo.mappers.CustomerMapper;
import com.example.tasktestravenapplicationtwo.models.Customer;
import com.example.tasktestravenapplicationtwo.repositories.CustomerRepository;
import com.example.tasktestravenapplicationtwo.validators.CustomerUpdateValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    CustomerMapper customerMapper;

    @Mock
    CustomerUpdateValidator customerUpdateValidator;

    @Test
    public void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setFullName("John Doe");
        customer.setEmail("johndoe@example.com");
        customer.setPhone("+1234567890");

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFullName("John Doe");
        customerDTO.setEmail("johndoe@example.com");
        customerDTO.setPhone("+1234567890");

        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.customerToCustomerDTO(customer)).thenReturn(customerDTO);

        CustomerDTO createdCustomerDTO = customerService.createCustomer(customer);

        assertEquals(customerDTO, createdCustomerDTO);
    }

    @Test
    public void testReadAllCustomers() {
        Customer customer1 = new Customer();
        customer1.setFullName("John Doe");
        customer1.setEmail("johndoe@example.com");
        customer1.setPhone("+1234567890");

        Customer customer2 = new Customer();
        customer2.setFullName("Jane Doe");
        customer2.setEmail("janedoe@example.com");
        customer2.setPhone("+0987654321");

        List<Customer> customerList = Arrays.asList(customer1, customer2);

        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setFullName("John Doe");
        customerDTO1.setEmail("johndoe@example.com");
        customerDTO1.setPhone("+1234567890");

        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setFullName("Jane Doe");
        customerDTO2.setEmail("janedoe@example.com");
        customerDTO2.setPhone("+0987654321");

        List<CustomerDTO> customerDTOList = Arrays.asList(customerDTO1, customerDTO2);

        when(customerRepository.findByIsActiveTrue()).thenReturn(customerList);
        when(customerMapper.customerToCustomerDTO(customer1)).thenReturn(customerDTO1);
        when(customerMapper.customerToCustomerDTO(customer2)).thenReturn(customerDTO2);

        List<CustomerDTO> result = customerService.readAllCustomers();

        assertEquals(customerDTOList, result);
    }

    @Test
    public void testReadCustomer() {
        Long id = 1L;

        Customer customer = new Customer();
        customer.setFullName("John Doe");
        customer.setEmail("johndoe@example.com");
        customer.setPhone("+1234567890");
        customer.setIsActive(true);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFullName("John Doe");
        customerDTO.setEmail("johndoe@example.com");
        customerDTO.setPhone("+1234567890");

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        when(customerMapper.customerToCustomerDTO(customer)).thenReturn(customerDTO);

        CustomerDTO result = customerService.readCustomer(id);

        assertEquals(customerDTO, result);
    }

    @Test
    public void testReadCustomer_NotFound() {
        Long id = 1L;

        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CustomerException.class, () -> customerService.readCustomer(id));
    }

    @Test
    public void testUpdateCustomer() {
        Long id = 1L;
        Long oldId = 1L;
        String fullName = "John Doe Updated";
        String email = "johndoeupdated@example.com";
        String phone = "+0987654321";

        Customer customer = new Customer();
        customer.setFullName("John Doe");
        customer.setEmail("johndoe@example.com");
        customer.setPhone("+1234567890");
        customer.setIsActive(true);

        Customer updatedCustomer = new Customer();
        updatedCustomer.setFullName(fullName);
        updatedCustomer.setEmail(email);
        updatedCustomer.setPhone(phone);
        updatedCustomer.setIsActive(true);

        CustomerDTO updatedCustomerDTO = new CustomerDTO();
        updatedCustomerDTO.setFullName(fullName);
        updatedCustomerDTO.setEmail(email);
        updatedCustomerDTO.setPhone(phone);

        when(customerUpdateValidator.validate(id, oldId, email)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(updatedCustomer);
        when(customerMapper.customerToCustomerDTO(updatedCustomer)).thenReturn(updatedCustomerDTO);

        CustomerDTO result = customerService.updateCustomer(id, oldId, fullName, email, phone);

        assertEquals(updatedCustomerDTO, result);
    }

    @Test
    public void testDeleteCustomer() {
        Long id = 1L;

        when(customerRepository.existsByIdAndIsActiveIsTrue(id)).thenReturn(true);
        doNothing().when(customerRepository).deactivateCustomer(id);

        customerService.deleteCustomer(id);
    }

    @Test
    public void testDeleteCustomer_NotFound() {
        Long id = 1L;

        when(customerRepository.existsByIdAndIsActiveIsTrue(id)).thenReturn(false);

        assertThrows(CustomerException.class, () -> customerService.deleteCustomer(id));
    }

}
