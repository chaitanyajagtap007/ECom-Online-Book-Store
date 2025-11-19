package com.mh.crj.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.mh.crj.entity.UserRegister;
import com.mh.crj.model.UserRequestDto;
import com.mh.crj.repository.UserRegisterRepo;
import com.mh.crj.serviceImpl.UserRegisterServiceimpl;

@SpringBootTest
public class UserRegisterServiceTest {

	// fake DB
	@MockBean
	private UserRegisterRepo userRegisterRepo;
	
	@Autowired
	private UserRegisterServiceimpl userRegisterServiceimpl;
	
	
	@Test
	public void testInsertUserRegister(){
		
		// 1 Create input data
		UserRequestDto input = new UserRequestDto();
		input.setEmail("rohit@gmail.com");
		input.setFirstName("Rohit");
		input.setLastName("sharma");
		input.setPassword("pass@123");
		
		// 2 create fake output data
		UserRegister output = new UserRegister();
		output.setId(1L);
		output.setFirstName("Srinu");
		output.setLastName("Lateesha");
		output.setEmail("gopi@gmail.com");
		output.setPassword(Base64.getEncoder().encodeToString("pass@123".getBytes()));

		
		// 3 when save method called , return fake user
		when(userRegisterRepo.save(any(UserRegister.class))).thenReturn(output);
		
		// 4 Call actual service method
		UserRegister result = userRegisterServiceimpl.insertUserRegister(input);
		
		// 5 check (verify) output
		assertNotNull(result);		//user should not be null
		assertEquals("Rohit", result.getFirstName());
		assertEquals("rohit@gmail.com", result.getEmail());
		// save() method call only once
		verify(userRegisterRepo,times(1)).save(any(UserRegister.class));
		
	}
	
	@Test
	public void testFetchAllUser() {
		
		// 2 create fake output data
		
		
		UserRegister user1 = new UserRegister();
		user1.setId(1L);
		user1.setFirstName("Srinu");
		user1.setLastName("Lateesha");
		user1.setEmail("gopi@gmail.com");
		user1.setPassword(Base64.getEncoder().encodeToString("pass@123".getBytes()));

		UserRegister user2 = new UserRegister();
		user2.setId(2L);
		user2.setFirstName("Rohit");
		user2.setLastName("Sharma");
		user2.setEmail("rohit@gmail.com");
		user2.setPassword(Base64.getEncoder().encodeToString("Rohit@123".getBytes()));
		
		List<UserRegister> opList = Arrays.asList(user1,user2);
		
		
		// 3 when findById() called , return fake user
		when(userRegisterRepo.findAll()).thenReturn(opList);
	 	
		// 4 call actual service method
		List<UserRegister> result = userRegisterServiceimpl.fetchAllUser();

		// 5 check verify op
		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("Srinu", result.get(0).getFirstName());
		verify(userRegisterRepo,times(1)).findAll();
	}

	@Test
	public void testCheckUserDetails() {
	    // GIVEN
	    UserRequestDto input = new UserRequestDto();
	    input.setEmail("rohit@gmail.com");
	    input.setFirstName("Rohit");
	    input.setLastName("sharma");
	    input.setPassword("pass@123");

	    UserRegister fakeUser = new UserRegister();
	    fakeUser.setId(1L);
	    fakeUser.setFirstName("Rohit");
	    fakeUser.setLastName("sharma");
	    fakeUser.setEmail("rohit@gmail.com");
	    fakeUser.setPassword(Base64.getEncoder().encodeToString("pass@123".getBytes()));

	    when(userRegisterRepo.findByEmail(input.getEmail())).thenReturn(Optional.of(fakeUser));

	    // WHEN
	    UserRegister result = userRegisterServiceimpl.checkUserDetails(input);

	    // THEN
	    assertNotNull(result, "Expected non-null user");
	    assertEquals("Rohit", result.getFirstName());
	    assertEquals("rohit@gmail.com", result.getEmail());
	    verify(userRegisterRepo, times(1)).findByEmail("rohit@gmail.com");
	}
	
	
	
	@Test
	public void testGetAllUserUsingPagination() {

		
		// input
		
		int pageNo =1;
		int pageSize =2;
		String sortField ="FirstName" ;
		String sortOrder="ASC";
		
		UserRegister user1 = new UserRegister();
		user1.setId(1L);
		user1.setFirstName("Srinu");
		user1.setLastName("Lateesha");
		user1.setEmail("gopi@gmail.com");
		user1.setPassword(Base64.getEncoder().encodeToString("pass@123".getBytes()));

		UserRegister user2 = new UserRegister();
		user2.setId(2L);
		user2.setFirstName("Rohit");
		user2.setLastName("Sharma");
		user2.setEmail("rohit@gmail.com");
		user2.setPassword(Base64.getEncoder().encodeToString("Rohit@123".getBytes()));
		

		Page<UserRegister> result =  new PageImpl<>(Arrays.asList(user1,user2));
		
		Sort sort = Sort.by(sortOrder.equalsIgnoreCase("asc")? Sort.Direction.ASC : Sort.Direction.DESC,sortField);
		PageRequest pageRequest = PageRequest.of(pageNo,pageSize,sort);
		
		when(userRegisterRepo.findAll(pageRequest)).thenReturn(result);
		
		// 4 Call actual service method
		userRegisterServiceimpl.getAllUserUsingPagination(pageNo, pageSize, sortField, sortOrder);

	
		// 5 check (verify) output
		assertNotNull(result);		//user should not be null
		assertEquals(2, result.getContent().size());
		assertEquals("Rohit", result.getContent().get(1).getFirstName());
		// save() method call only once
		verify(userRegisterRepo,times(1)).findAll(pageRequest);
		
	}


}
