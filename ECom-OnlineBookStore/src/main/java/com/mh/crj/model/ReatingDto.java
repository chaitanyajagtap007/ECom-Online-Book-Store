package com.mh.crj.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReatingDto {

	private Long userId;
	private Long bookId;	
	private int rate;
	private String reviewText;
	
	
}
