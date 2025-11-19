package com.mh.crj.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mh.crj.entity.Rating;
import com.mh.crj.model.ReatingDto;
import com.mh.crj.model.ResponseMessage;
import com.mh.crj.service.ReatingService;
import com.mh.crj.utility.Constants;

import java.net.HttpURLConnection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/rate")
public class ReatingController {

	@Autowired
	private ReatingService reatingService;
	
	@PostMapping("/ratingBook")
	public ResponseEntity<ResponseMessage> doReating(@RequestBody ReatingDto reatingDto) {
		
		if(reatingDto.getReviewText() == null || reatingDto.getReviewText().isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "viewText cannot be empty"));
		}
		
		Rating saveReating = reatingService.saveReating(reatingDto);
		
		if(saveReating !=null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(HttpURLConnection.HTTP_CREATED, Constants.SUCCESS, "Reating save successfully",saveReating));
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Reating Failed to Save ",saveReating));				
		}
		
	}
	
	@PutMapping("/bookRateingUpdate")
	public ResponseEntity<ResponseMessage> updateReating(@RequestBody ReatingDto reatingDto) {
		
		if(reatingDto.getReviewText() == null || reatingDto.getReviewText().isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "viewText cannot be empty"));
		}
		
		Rating saveReating = reatingService.updadteReating(reatingDto);
		
		if(saveReating !=null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(HttpURLConnection.HTTP_CREATED, Constants.SUCCESS, "Reating Update successfully",saveReating));
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Reating Failed to Update",saveReating));				
		}
		
	}
	
	
	
	@GetMapping("/getAllreviews")
	public ResponseEntity<ResponseMessage> getAllReating() {

		List<Rating> reatings = reatingService.fetchAllReating();
		if(reatings.isEmpty()) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(HttpURLConnection.HTTP_CREATED, Constants.SUCCESS, "No Reating"));
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(HttpURLConnection.HTTP_OK, Constants.SUCCESS, "Reatings fetch successfully",reatings));
		
	}
	
	
	@DeleteMapping("/deleteBookRateing")
	public ResponseEntity<ResponseMessage> removeReating(Long userId,Long bookId) {
		
		if(userId == null || bookId==null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "book id nad user must not be empty"));
		}
		
		String delete = reatingService.deleteReating(userId,bookId);
		
		if(delete !=null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(HttpURLConnection.HTTP_CREATED, Constants.SUCCESS,delete));
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Reating Failed to Update"));				
		}

	}
	
	
}
