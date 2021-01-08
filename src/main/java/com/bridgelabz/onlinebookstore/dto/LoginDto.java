package com.bridgelabz.onlinebookstore.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginDto {

	private String emailId;

	private String password;

	public String getEmailId() {
		return this.emailId;
	}

	public String getPassword() {
		return this.password;
	}
}
