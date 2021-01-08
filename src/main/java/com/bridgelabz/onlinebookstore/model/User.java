package com.bridgelabz.onlinebookstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

import lombok.Data;

@Entity
@Table(name = "user")
public @Data class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private long userId;
	private String fullName;// name
	private String emailId;// emailId
	private String password;// password
	private boolean isVerify;// for verification

	 public User() {
	 }

//	@ManyToMany(cascade = CascadeType.ALL)
//	@JoinColumn(name = "book
//	private List<Book> books;

	public User(String fullName, String emailId, String password) {

		this.fullName = fullName;
		this.emailId = emailId;
		this.password = password;
	}
}