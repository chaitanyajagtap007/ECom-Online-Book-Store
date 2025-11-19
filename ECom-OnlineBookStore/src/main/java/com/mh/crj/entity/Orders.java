package com.mh.crj.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "bookId")
	private Long bookId;
	
	@Column(name = "userId")
	private Long userId;
	
	@Column(name = "ststus")
	private Boolean ststus;

	@CreationTimestamp
	@Column(name = "createdDate" ,updatable = false)
	public LocalDateTime createdDate;
	
	@UpdateTimestamp
	@Column(name = "updatedDate")
	public LocalDateTime updatedDate;

}