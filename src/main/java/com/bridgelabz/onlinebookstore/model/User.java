package com.bridgelabz.onlinebookstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "user")
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;

	@NotEmpty(message = "Name cannot be null")
	@Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message = "Incorrect User name")
	@Column(name = "userName")
	private String userName;

	@Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message = "\n Incorrect Email Id")
	private String emailId;

	// Minimum eight and maximum 10 characters, at least one uppercase letter, one
	// lowercase letter, one number and one special character:
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,10}$", message = "Incorrect password")
	private String password;

	public Users() {
	}

	public Users(int id, String emailId, String userName, String password) {
		super();
		this.userId = id;
		this.emailId = emailId;
		this.userName = userName;
		this.password = password;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int id) {
		this.userId = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

//	@Override
//	public String toString() {
//		return "Users [userId=" + userId + ", name=" + userName + ", email=" + emailId + ", password=" + password + "]";
//	}

}