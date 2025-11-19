package com.mh.crj.controller.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mh.crj.controller.CustomerController;
import com.mh.crj.entity.Customer;
import com.mh.crj.service.CustomerService;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CustomerService customerService;
	
	
	@Test
	public void testSaveCustomer() throws Exception {
		// 1 Input data
		Customer customerRequest = new Customer();
		customerRequest.setName("Rohit Sharma");
		customerRequest.setEmail("rohit@gmail.com");
		customerRequest.setPhone("1234567890");

		// 2 Mock service response
		
		Customer mockResponse = new Customer();
		mockResponse.setId(1l);
		mockResponse.setName("Rohit Sharma");
		mockResponse.setEmail("rohit@gmail.com");
		mockResponse.setPhone("1234567890");

		// 3 mock service call
		when(customerService.insertCustomer(customerRequest)).thenReturn(mockResponse);
		
		// 4 proform post and validate
		mockMvc.perform(post("/saveCustomer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(customerRequest)))
				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
	
	}
	
	
}
