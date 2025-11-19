package com.mh.crj.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mh.crj.service.FileMngtService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class FileController {
	
	@Autowired
	private FileMngtService fileMngtService;
	
	@Operation(
		    summary = "Add new File",
		    description = "Insert a new file into the database",
		    responses = {
		        @ApiResponse(responseCode = "201", description = "file created successfully"),
		        @ApiResponse(responseCode = "400", description = "Invalid file data"),
		        @ApiResponse(responseCode = "500", description = "Internal server error")
		    }
		)
	@PostMapping(value ="/uploadfile" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String>  uploadFile(@RequestParam MultipartFile file) throws IOException{

		String msg = fileMngtService.storeFile(file);
		return ResponseEntity.ok(msg);
	}
	
	
	@PostMapping(value="/uploadmutliFiles", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<List<String>> uploadFiles(@RequestParam MultipartFile[] files) throws IOException{
		
		List<String> responses = Arrays.stream(files)
		        .map(file -> {
		            try {
		                String msg = fileMngtService.storeFile(file);
		                return "✅ Uploaded: " + file.getOriginalFilename() + " → " + msg;
		            } catch (Exception e) {
		                return "❌ Failed: " + file.getOriginalFilename() + " → " + e.getMessage();
		            }
		        })
		        .collect(Collectors.toList());;
	return ResponseEntity.ok(responses);
		
		
	}

	
	
	

}
