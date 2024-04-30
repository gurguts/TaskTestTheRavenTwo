package com.example.tasktestravenapplicationtwo.restControllers;

import com.example.tasktestravenapplicationtwo.dto.CustomerDTO;
import com.example.tasktestravenapplicationtwo.dto.CustomerUpdateDTO;
import com.example.tasktestravenapplicationtwo.mappers.CustomerMapper;
import com.example.tasktestravenapplicationtwo.models.Customer;
import com.example.tasktestravenapplicationtwo.services.impl.ICustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
    @InjectMocks
    CustomerController customerController;
    @Mock
    ICustomerService customerService;
    @Mock
    CustomerMapper customerMapper;

    @Test
    public void testCreateCustomer() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFullName("John Doe");
        customerDTO.setEmail("johndoe@example.com");
        customerDTO.setPhone("+1234567890");

        Customer customer = new Customer();
        customer.setFullName("John Doe");
        customer.setEmail("johndoe@example.com");
        customer.setPhone("+1234567890");

        when(customerMapper.customerDTOToCustomer(customerDTO)).thenReturn(customer);
        when(customerService.createCustomer(customer)).thenReturn(customerDTO);

        ResponseEntity<?> responseEntity = customerController.createCustomer(customerDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customerDTO, responseEntity.getBody());
    }

    @Test
    public void testReadAllCustomers() {
        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setFullName("John Doe");
        customerDTO1.setEmail("johndoe@example.com");
        customerDTO1.setPhone("+1234567890");

        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setFullName("Jane Doe");
        customerDTO2.setEmail("janedoe@example.com");
        customerDTO2.setPhone("+0987654321");

        List<CustomerDTO> customerDTOList = Arrays.asList(customerDTO1, customerDTO2);

        when(customerService.readAllCustomers()).thenReturn(customerDTOList);

        ResponseEntity<List<CustomerDTO>> responseEntity = customerController.readAllCustomers();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customerDTOList, responseEntity.getBody());
    }

    @Test
    public void testReadCustomer() {
        Long id = 1L;

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(id);
        customerDTO.setFullName("John Doe");
        customerDTO.setEmail("johndoe@example.com");
        customerDTO.setPhone("+1234567890");

        when(customerService.readCustomer(id)).thenReturn(customerDTO);

        ResponseEntity<CustomerDTO> responseEntity = customerController.readCustomer(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(customerDTO, responseEntity.getBody());
    }

    @Test
    public void testUpdateCustomer() {
        Long id = 1L;

        CustomerUpdateDTO customerUpdateDTO = new CustomerUpdateDTO();
        customerUpdateDTO.setId(id);
        customerUpdateDTO.setFullName("John Doe Updated");
        customerUpdateDTO.setEmail("johndoeupdated@example.com");
        customerUpdateDTO.setPhone("+0987654321");

        CustomerDTO updatedCustomerDTO = new CustomerDTO();
        updatedCustomerDTO.setId(id);
        updatedCustomerDTO.setFullName("John Doe Updated");
        updatedCustomerDTO.setEmail("johndoeupdated@example.com");
        updatedCustomerDTO.setPhone("+0987654321");

        when(customerService.updateCustomer(id, customerUpdateDTO.getId(), customerUpdateDTO.getFullName(),
                customerUpdateDTO.getEmail(), customerUpdateDTO.getPhone())).thenReturn(updatedCustomerDTO);

        ResponseEntity<?> responseEntity = customerController.updateCustomer(id, customerUpdateDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedCustomerDTO, responseEntity.getBody());
    }

    @Test
    public void testDeleteCustomer() {
        Long id = 1L;

        doNothing().when(customerService).deleteCustomer(id);

        ResponseEntity<String> responseEntity = customerController.deleteCustomer(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(String.format("Customer with id %d deleted", id), responseEntity.getBody());
    }

}
