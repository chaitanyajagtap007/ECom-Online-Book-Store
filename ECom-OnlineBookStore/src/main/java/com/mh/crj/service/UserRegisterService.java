package com.mh.crj.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mh.crj.entity.UserRegister;
import com.mh.crj.model.UserRequestDto;

public interface UserRegisterService {

	public UserRegister insertUserRegister(UserRequestDto userRequestDto);
	public List<UserRegister> fetchAllUser();
	public UserRegister checkUserDetails(UserRequestDto userRequestDto);
	public Page<UserRegister> getAllUserUsingPagination(int pageNo,int pageSize,String sortField,String sortOrder);
	UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
}
