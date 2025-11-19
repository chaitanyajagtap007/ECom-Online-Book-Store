package com.mh.crj.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.mh.crj.entity.Customer;
import com.mh.crj.repository.CustomerRepository;
import com.mh.crj.serviceImpl.CustomerServiceImpl;

@SpringBootTest
public class CustomerServiceTest {

	
	@MockBean
	private CustomerRepository customerRepository;
	
	@Autowired
	private CustomerServiceImpl customerServiceImpl;
	
	
	
	@Test
	public void insertCustomerTest() {
		
		// 1 Input
		Customer customer = new Customer();
		customer.setName("Rohit");
		customer.setEmail("rohit@gmail.com");
		customer.setPhone("1234567890");

		// 2 Output
		Customer savedCustomer = new Customer();
		savedCustomer.setId(1l);
		savedCustomer.setName("Rohit");
		savedCustomer.setEmail("rohit@gmail.com");
		savedCustomer.setPhone("1234567890");

		// 3 when save called then return savedCustomer (fake DB data)
		when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
		
		// 4 call actual service method
		Customer result = customerServiceImpl.insertCustomer(customer);
		
		// 5 verify the output
		assertNotNull(result);
		assertEquals("Rohit", result.getName());
		assertEquals("rohit@gmail.com", result.getEmail());
		// save() call only once
		verify(customerRepository,times(1)).save(any(Customer.class));
	}
	
}
