package com.mermer.userservice.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RequestLogin {

	@NotNull(message = "Email cannot be null")
	@Size(min = 2, message = "Email not be less than two characters")
	@Email
	private String email;
	@NotNull(message = "Password cannot be null")
	@Size(min = 13, message = "Password must be equals or grater than 13 characters")
	private String password;

}
