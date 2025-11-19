package com.mh.crj.controller;

import java.net.HttpURLConnection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mh.crj.entity.CartModule;
import com.mh.crj.model.ResponseMessage;
import com.mh.crj.service.CartModuleService;
import com.mh.crj.utility.Constants;



@RestController
public class CartController {

	@Autowired
	private CartModuleService cartModuleService;
	
	@PostMapping("/addtocart")
	public ResponseEntity<ResponseMessage> addedToCarts(@RequestParam Long customerId,
														@RequestParam Long bookId,
														@RequestParam Integer quantity) {
		try {
			CartModule toCart = cartModuleService.addToCart(customerId,bookId,quantity);
			if(toCart!=null) {				
				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_CREATED, Constants.SUCCESS, "Added the cart into books successfully",toCart));
			}else {
				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILURE, "Added the cartare failure",toCart));
			}
		}catch (Exception e) {
			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILURE, "Add the card books geatting failure",e.getMessage()));
		}
	}
	
	@DeleteMapping("/removefromcart/{id}")
	public ResponseEntity<ResponseMessage> removeFromCart(@PathVariable Long id){
		cartModuleService.removeFromCart(id);
		return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILURE, id+" :id  Remove the cart from books successfully"));
		
	}
	
	@GetMapping("/retrivecarts")
	public ResponseEntity<ResponseMessage> retriveCarts(){
		try {
			List<CartModule> retriveCarts = cartModuleService.retriveCarts();
			if(!retriveCarts.isEmpty()) {				
				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK, Constants.SUCCESS, "Retrive  cart successfully",retriveCarts));
			}else {
				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILURE, "Retrive  cart failure",retriveCarts));
			}
		}catch (Exception e) {
			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILURE, "Retrive  cart  geatting failure",e.getMessage()));
		}
	}
	
	@GetMapping("/retrivecartsByCustomerId/{id}")
	public ResponseEntity<ResponseMessage> retriveCartsByCustomerId(@PathVariable Long id){
		try {
			List<CartModule> retriveCarts = cartModuleService.retriveCartsByCustomerId(id);
			if(!retriveCarts.isEmpty()) {				
				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_OK, Constants.SUCCESS, "Retrive  cart successfully",retriveCarts));
			}else {
				return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILURE, "Retrive  cart failure",retriveCarts));
			}
		}catch (Exception e) {
			return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILURE, "Retrive  cart  geatting failure",e.getMessage()));
		}
	}
	
}
