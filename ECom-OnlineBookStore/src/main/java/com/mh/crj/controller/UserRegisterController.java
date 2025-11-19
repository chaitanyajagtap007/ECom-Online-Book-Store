package com.mh.crj.controller;

import java.net.HttpURLConnection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mh.crj.entity.UserRegister;
import com.mh.crj.model.ResponseMessage;
import com.mh.crj.model.UserRequestDto;
import com.mh.crj.service.UserRegisterService;
import com.mh.crj.utility.Constants;
import com.mh.crj.utility.JwtUtilService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name="UserRegisterController",description = "UserRegister Regsiter and Login")
@RestController
public class UserRegisterController {
	
	@Autowired
	private UserRegisterService userRegisterService;

	@Autowired
	private JwtUtilService jwtUtilService;
	
	
	@Operation(summary = "Create User Register",description = "e commerece online books store  register the users")
    @ApiResponses({
     @ApiResponse(responseCode = "201",description = "user register successfully"),
     @ApiResponse(responseCode = "400",description = "user register failure"),
     @ApiResponse(responseCode = "500",description = "Internal server error")
     })
	@PostMapping("/userregister")
	public ResponseEntity<ResponseMessage> createUserRegister(@RequestBody UserRequestDto userRequestDto){
		try {
			if(userRequestDto.getEmail()==null || userRequestDto.getEmail().isEmpty() || userRequestDto.getPassword() == null || userRequestDto.getPassword().isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "email and passowrd cannot be empty"));
			}
			
			UserRegister userRegister = userRegisterService.insertUserRegister(userRequestDto);
			
			if(userRegister !=null) {
				return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(HttpURLConnection.HTTP_CREATED, Constants.SUCCESS, "online bookstre save successfully",userRegister));
			}else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "User Register Failed ",userRegister));				
			}
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(HttpURLConnection.HTTP_INTERNAL_ERROR, Constants.FAILED, "Internal server error"));				
		}
	}
	
	
	@PostMapping("/userlogin")
	public ResponseEntity<ResponseMessage> checkLogin(@RequestBody UserRequestDto userRequestDto){
		
		if(userRequestDto.getEmail()==null || userRequestDto.getEmail().isEmpty() || userRequestDto.getPassword() == null || userRequestDto.getPassword().isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "email and passowrd cannot be empty"));
		}
		
		UserRegister checkUserDetails = userRegisterService.checkUserDetails(userRequestDto);
		//create JWT token
		String token = jwtUtilService.generateToken(checkUserDetails.getEmail());
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpURLConnection.HTTP_OK, Constants.SUCCESS, "Login successfully this is you JWT token : "+token));
		
		
	}
	
	
	@GetMapping("/getAllUsers")
	@Operation(summary = "Get All User Details",description = "e commerece online books store  Fetch All User Details")
	public ResponseEntity<List<UserRegister>> getAllUserDetails(){
		List<UserRegister> list = userRegisterService.fetchAllUser();
		
		if(list.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/getAllUserUsingPaging")
	@Operation(summary = "Get All User Details using pageing",description = "e commerece online books store  Fetch All User Details")
	public Page<UserRegister> getAllUserUsingPaging(@RequestParam int pageNo, @RequestParam  int pageSize, @RequestParam String sortField,@RequestParam String sortOrder){
		
		
		return userRegisterService.getAllUserUsingPagination(pageNo, pageSize, sortField, sortOrder);
	}
	
}
