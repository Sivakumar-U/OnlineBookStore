package com.bridgelabz.onlinebookstore.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;

@AllArgsConstructor

public class RegistrationDto {

	@Pattern(regexp = "^[A-Z][a-z]+\\s?[A-Z][a-z]+$", message = "Please enter full name")
	// @Size(min = 3, max = 14)
	@NotBlank
	private String fullName;

	@Pattern(regexp = "^[a-zA-Z0-9]{1,}([_+-.][a-zA-Z0-9]+)*@[a-zA-Z0-9]{2,}.[a-z]{2,4}([.][a-zA-Z]{2})*$", message = "Please enter correct email Id")
	@NotBlank
	// @Size(min = 7, max = 12)
	private String emailId;

	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,20}$", message = "Password length should be 8 must contain at least one uppercase, lowercase, special character and number")
	@NotBlank
	// @Size(min = 8, max = 12)
	private String password;

	public String getFullName() {
		return fullName;
	}

	public String getEmailId() {
		return emailId;
	}

	public String getPassword() {
		return password;
	}
}
