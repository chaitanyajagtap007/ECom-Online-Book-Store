package com.mh.crj.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "rate")
	private int rate;
	
	@Column(name = "reviewText")
	private String reviewText;
	
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private UserRegister user;
	
	@ManyToOne
	@JoinColumn(name = "bookId")
	private Book book;
	

	@Column(name= "created_date", updatable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;
	
	@Column(name = "updated_date")
	@UpdateTimestamp
	private LocalDateTime updatedDate;
	
}
