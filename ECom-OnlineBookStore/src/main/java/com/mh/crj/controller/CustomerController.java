package com.mh.crj.controller;


import java.net.HttpURLConnection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mh.crj.entity.Customer;
import com.mh.crj.model.ResponseMessage;
import com.mh.crj.service.CustomerService;
import com.mh.crj.utility.Constants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "CustomerController",description = "Customer save and update")
@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@Operation(summary = "Save Customer")
	@ApiResponses({
	     @ApiResponse(responseCode = "201",description = "Customer save successfully"),
	     @ApiResponse(responseCode = "400",description = "Customer save failure"),
	     @ApiResponse(responseCode = "500",description = "Internal server error")
	     })
	@PostMapping("/saveCustomer")
	public ResponseEntity<ResponseMessage> saveCustomer(@RequestBody Customer customer) {
		try {
			
			if(customer.getEmail()==null || customer.getEmail().isEmpty() || customer.getName() == null || customer.getName().isEmpty()|| customer.getPhone() == null || customer.getPhone().isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "email and passowrd and phone cannot be empty"));
			}
			
			Customer savedCustomer = customerService.insertCustomer(customer);
			
			if(savedCustomer != null){
				return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(HttpURLConnection.HTTP_CREATED,Constants.SUCCESS,"Customer saved successfully",savedCustomer));
			}
			else {				
				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Faield to register Customer"));
			}
		}catch (Exception e) {
			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_INTERNAL_ERROR, Constants.FAILED, "Internal server error"));
		}
		
	}
	
	@Operation(summary = "Update Customer")
	@ApiResponses({
	     @ApiResponse(responseCode = "201",description = "Customer update successfully"),
	     @ApiResponse(responseCode = "400",description = "Customer update failure"),
	     @ApiResponse(responseCode = "500",description = "Internal server error")
	     })
	@PutMapping("/update")
	public ResponseEntity<ResponseMessage> updateCustomer(@RequestBody Customer customer) {
		try {
			
			if(customer.getEmail()==null || customer.getEmail().isEmpty() || customer.getName() == null || customer.getName().isEmpty() || customer.getPhone() == null || customer.getPhone().isEmpty() || customer.getId() == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Email, name, and phone cannot be empty"));
			}
			
			Customer updatedCustomer = customerService.updateCustomer(customer);
			
			if(updatedCustomer != null){
				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_CREATED,Constants.SUCCESS,"Customer updated successfully",updatedCustomer));
			}
			else {				
				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Faield to Update Customer"));
			}
		}catch (Exception e) {
			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_INTERNAL_ERROR, Constants.FAILED, "Internal server error"));
		}
		
	}
	

	@Operation(summary = "Save and Update Customer")
	@PutMapping("/saveOrUpdate")
	public ResponseEntity<ResponseMessage> saveOrUpdateCustomer(@RequestBody Customer customer) {
	
		try {
			
			if(customer.getEmail()==null || customer.getEmail().isEmpty() || customer.getName() == null || customer.getName().isEmpty() || customer.getPhone() == null || customer.getPhone().isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Email, name, and phone cannot be empty"));
			}
			
			Long isId = customer.getId();
			Customer data = customerService.saveOrUpdateCustomer(customer);
			if(isId!=null) {
				
				if(data != null){
					return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_CREATED,Constants.SUCCESS,"Customer Updated successfully",data));
				}
				else {				
					return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Failed to Update Customer"));
				}
				
			}else {		
				if(data != null){
					return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_CREATED,Constants.SUCCESS,"Customer saved successfully",data));
				}
				else {				
					return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Failed to Save Customer"));
				}
				
			}
		}catch (Exception e) {
			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_INTERNAL_ERROR, Constants.FAILED, "Internal server error"));
		}
		
	}
	
	
	@Operation(summary = "get Customer by Id")
	@GetMapping("/getCustomer/{id}")
	public ResponseEntity<ResponseMessage> getCustomerById(@PathVariable Long id){
		Customer cust = customerService.getById(id);
		if(cust!=null) {
			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK, Constants.SUCCESS,"Customer fetch successfully ", cust));
		}else {
			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_INTERNAL_ERROR, Constants.FAILED, "Internal server Error"));
		}
	}
	

	@Operation(summary = "get All Customer")
	@GetMapping("/getAllCustomer")
	public ResponseEntity<ResponseMessage> getAllCustomer(){
		List<Customer> list = customerService.getAllCustomers();
		if(list.isEmpty()) {
			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_NOT_FOUND, Constants.FAILED,"No Customer is Available ...!"));				
		}else {
			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK, Constants.SUCCESS,"List of Customer is Available ...! with count "+list.size(),list));								
		}
	}
	
	
}
