package com.bridgelabz.onlinebookstore.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class ForgotPasswordDto {

	@NotEmpty
	@Email(message = "Enter valid email Id")
	private String emailId;
}
