package com.bridgelabz.onlinebookstore.dto;

import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;

@AllArgsConstructor

public class RegistrationDto {

	@Pattern(regexp = "^[A-Z][a-z]+\\s?[A-Z][a-z]+$", message = "Please Enter Valid FullName")
	private String  fullName;

	@Pattern(regexp = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",message = "EmailId Should follow this pattern abc.xyz@gmail.com.in")
	private String  emailId;
	
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,20}$", message = "Password length should be 8 must contain at least one uppercase, lowercase, special character and number")
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
