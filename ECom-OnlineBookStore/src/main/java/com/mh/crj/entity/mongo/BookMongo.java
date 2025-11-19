package com.mh.crj.entity.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookMongo {

	@Id
	private String id;
	
	private String title;
	
	private String author;
	
	private Double price;
	
	private Integer stock;
	
	
}
