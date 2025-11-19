package com.mh.crj.entity.mongo;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Document(collection = "files")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FIlesMongo {


	@Id
	private String id;
	
	private String fileName;
	
	private String fileType;
	
	private byte[] data;

	
	@CreationTimestamp
	public LocalDateTime createdDate;
	
	@UpdateTimestamp
	public LocalDateTime updatedDate;

}
