package com.mh.crj.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

	private String firstName;
	private String lastName;
	
	@NotBlank(message = "email cannot blank")
	@Schema(description = "email",example = "enter the email")
	@Column(name = "email")
	private String email;
	
	@NotBlank(message = "password cannot blank")
	@Schema(description = "password",example = "enter the password")
	@Column(name = "password")
	private String password;
	
	private long contactId;
}
