package com.mh.crj.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "register")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegister {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "firstName")
	private String firstName;
	@Column(name = "lastName")
	private String lastName;
	
	@Column(name = "email")
	private String email;
	@Column(name = "password")
	private String password;
	
	@Column(name = "contactId")
	private long contactId;
	
	@Column(name = "prime",columnDefinition = "TINYINT(1)")
	private Boolean prime;
	
	@CreationTimestamp
	@Column(name = "createdDate")
	public LocalDateTime createdDate;
	
	@CreationTimestamp
	@Column(name = "updatedDate")
	public LocalDateTime updatedDate;
}
