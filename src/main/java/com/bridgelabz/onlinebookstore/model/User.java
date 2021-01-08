package com.bridgelabz.onlinebookstore.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "user")
@RequiredArgsConstructor
public @Data class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private long userId;
	private String fullName; // name
	private String emailId; // emailId
	private String password; // password
	private boolean isVerify; // for verification

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date_and_time")
	private Date createdDateAndTime; // To Identify Complete User registration Information

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_login_date")
	private Date lastLoginDate; // To identify user's last login

	public User(String fullName, String emailId, String password) {

		this.fullName = fullName;
		this.emailId = emailId;
		this.password = password;
	}
}