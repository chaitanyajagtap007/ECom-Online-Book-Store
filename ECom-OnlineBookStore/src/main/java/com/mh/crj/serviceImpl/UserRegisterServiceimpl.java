package com.mh.crj.serviceImpl;

import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mh.crj.entity.UserRegister;
import com.mh.crj.entity.mongo.UserRegisterMongo;
import com.mh.crj.exception.CustomerNotFoundException;
import com.mh.crj.model.UserRequestDto;
import com.mh.crj.repository.UserRegisterRepo;
import com.mh.crj.repository.mongo.UserRegisterMongoRepo;
import com.mh.crj.service.UserRegisterService;

@Service
public class UserRegisterServiceimpl implements UserRegisterService ,UserDetailsService {

	@Autowired
	private UserRegisterRepo userRegisterRepo;
	
	@Autowired
	private UserRegisterMongoRepo userRegisterMongoRepo;
	
	@Override
	public UserRegister insertUserRegister(UserRequestDto userRequestDto) {

		UserRegister user = new UserRegister();
		try {
			
			user.setFirstName(userRequestDto.getFirstName());
			user.setLastName(userRequestDto.getLastName());
			user.setEmail(userRequestDto.getEmail());
			user.setPassword(Base64.getEncoder().encodeToString(userRequestDto.getPassword().getBytes()));
			user.setContactId(userRequestDto.getContactId());
			userRegisterRepo.save(user);
			
			
			UserRegisterMongo userMongo = new UserRegisterMongo();
			
			userMongo.setFirstName(userRequestDto.getFirstName());
			userMongo.setLastName(userRequestDto.getLastName());
			userMongo.setEmail(userRequestDto.getEmail());
			userMongo.setPassword(Base64.getEncoder().encodeToString(userRequestDto.getPassword().getBytes()));
			userMongo.setContactId(userRequestDto.getContactId());
			userRegisterMongoRepo.save(userMongo);
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return user;
	}

	@Override
	@Cacheable(value = "getAll")
	public List<UserRegister> fetchAllUser() {
		List<UserRegister> list = userRegisterRepo.findAll();
		System.err.println("Retrive Data from the database");
		System.out.println(list);
		return list;
	}

	@Override
	public UserRegister checkUserDetails(UserRequestDto userRequestDto){
		Optional<UserRegister> byEmail = userRegisterRepo.findByEmail(userRequestDto.getEmail());
		
		if(!byEmail.isPresent()) {			
			throw new CustomerNotFoundException("Customer is not Available");
		}
		
		UserRegister user = byEmail.get();
		String decode = new String(Base64.getDecoder().decode(user.getPassword()));
		if(decode.equals(userRequestDto.getPassword())) {
			user.setPassword(decode);
			return user;
		}else {
			throw new CustomerNotFoundException("Wrong Passward");
		}
	
	}

	@Override
	public Page<UserRegister> getAllUserUsingPagination(int pageNo, int pageSize, String sortField, String sortOrder) {

		Sort sort = Sort.by(sortOrder.equalsIgnoreCase("asc")? Sort.Direction.ASC : Sort.Direction.DESC,sortField);
		PageRequest pageRequest = PageRequest.of(pageNo,pageSize,sort);
		
		return userRegisterRepo.findAll(pageRequest);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		UserRegister user = userRegisterRepo.findByEmail(email)
				.orElseThrow(()-> new UsernameNotFoundException("user not found"));


		return new User(user.getEmail(), user.getPassword(), Collections.emptyList());
	}
	
}
