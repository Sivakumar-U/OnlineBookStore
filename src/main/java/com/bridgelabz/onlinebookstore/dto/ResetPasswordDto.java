package com.bridgelabz.onlinebookstore.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDto {

	@NotEmpty
	@Size(min = 3)
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,20}$", message = "length should be 8 must contain atleast one uppercase, lowercase, special character and number")
	private String newPassword;

	@NotEmpty
	@Size(min = 3)
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,20}$", message = "password should be same as above")
	private String confirmPassword;
}
