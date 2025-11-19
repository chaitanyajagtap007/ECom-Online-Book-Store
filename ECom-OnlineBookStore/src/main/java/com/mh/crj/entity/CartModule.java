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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class CartModule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NonNull
	private Integer quantity;
	
	private Double totalPrice;
	
	@NonNull
	@ManyToOne
	@JoinColumn(name = "book_id",updatable = false)
	private Book book;
	
	@NonNull
	@ManyToOne
	@JoinColumn(name = "customer_id",updatable = false)
	private Customer customer;
	
	@CreationTimestamp
	@Column(name= "createDate", updatable = false)
	private LocalDateTime createDate;
	
	@UpdateTimestamp
	@Column(name= "updateDate")
	private LocalDateTime updateDate;
}
