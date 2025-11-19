package com.mh.crj.controller.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mh.crj.controller.UserRegisterController;
import com.mh.crj.entity.UserRegister;
import com.mh.crj.model.UserRequestDto;
import com.mh.crj.service.UserRegisterService;

@WebMvcTest(UserRegisterController.class)
public class UserRegisterControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserRegisterService userRegisterService;

    
	
	@Test
	public void testUserRegistration() throws Exception{

		// 1️ Input data
		UserRequestDto userRequest = new UserRequestDto();
		userRequest.setEmail("test@gmail.com");
		userRequest.setPassword("pass@123");

		// 2️ Mock service response
		UserRegister mockResponse = new UserRegister();
		mockResponse.setId(1L);
		mockResponse.setEmail("test@gmail.com");

		// 3️ Mock service call
		when(userRegisterService.insertUserRegister(userRequest)).thenReturn(mockResponse);

		// 4️ Perform POST and validate response
		mockMvc.perform(post("/userregister").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(userRequest)))
				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	
	@Test
	public void testUserLogin() throws Exception{
		
		// 1️ Input data
		UserRequestDto userRequest = new UserRequestDto();
		userRequest.setEmail("test@gmail.com");
		userRequest.setPassword("pass@123");
		
		// 2️ Mock service response
		UserRegister mockResponse = new UserRegister();
		mockResponse.setId(1L);
		mockResponse.setEmail("test@gmail.com");
		
		// 3️ Mock service call
		when(userRegisterService.checkUserDetails(userRequest)).thenReturn(mockResponse);
		
		// 4️ Perform POST and validate response
		mockMvc.perform(post("/userlogin").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(userRequest)))
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	
	@Test
	public void getAllUserTest() throws JsonProcessingException, Exception {

		// input request
		UserRegister list1 = new UserRegister();
		list1.setFirstName("Sai");
		list1.setLastName("Kamal");
		list1.setEmail("abc@gmail.com");

		// Mock Response
		UserRegister list2 = new UserRegister();
		list2.setLastName("hello");
		list2.setEmail("xyz@gmail.com");

		List<UserRegister> asList = Arrays.asList(list1, list2);

		when(userRegisterService.fetchAllUser()).thenReturn(asList);

		mockMvc.perform(get("/getAllUsers").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(new ObjectMapper().writeValueAsString(asList)));

	}
}
