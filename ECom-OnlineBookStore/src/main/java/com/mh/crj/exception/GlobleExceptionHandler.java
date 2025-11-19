package com.mh.crj.exception;

import java.net.HttpURLConnection;
import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mh.crj.model.ErrorResponseMessage;
import com.mh.crj.utility.Constants;

@RestControllerAdvice
public class GlobleExceptionHandler {

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<Object> handleCustomerNotFoundException(CustomerNotFoundException ex){
		HashMap<String, String> hm = new HashMap<>();
		hm.put("Error Details", "Customer Id Not Found");
		hm.put("Error Messae", ex.getLocalizedMessage());
		hm.put("TimeStamp",System.currentTimeMillis()+"");
		
		ErrorResponseMessage errorResponseMessage = new ErrorResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILURE,"Customer is not Found in DB",hm);
		
		return ResponseEntity.ok(errorResponseMessage);
	}
	
	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<Object> handleBookNotFoundException(BookNotFoundException ex){
		HashMap<String, String> hm = new HashMap<>();
		hm.put("Error Details", "Book Id Not Found");
		hm.put("Error Messae", ex.getLocalizedMessage());
		hm.put("TimeStamp",System.currentTimeMillis()+"");
		
		ErrorResponseMessage errorResponseMessage = new ErrorResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILURE,"Book is not Found in DB",hm);
		
		return ResponseEntity.ok(errorResponseMessage);
	}
	
	@ExceptionHandler(InternalServerException.class)
	public ResponseEntity<Object> handleInternalServerException(InternalServerException ex){
		HashMap<String, String> hm = new HashMap<>();
		hm.put("Error Details", "Internal Server Error");
		hm.put("Error Messae", ex.getLocalizedMessage());
		hm.put("TimeStamp",System.currentTimeMillis()+"");
		
		ErrorResponseMessage errorResponseMessage = new ErrorResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILURE,"Internal server Error",hm);
		
		return ResponseEntity.ok(errorResponseMessage);
	}
	
	
	
	
	
}