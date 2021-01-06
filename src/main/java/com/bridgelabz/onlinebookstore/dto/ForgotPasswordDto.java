package com.bridgelabz.onlinebookstore.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordDto {

	@NotEmpty
	@Email(message = "Enter valid email Id")
	private String emailId;
}
