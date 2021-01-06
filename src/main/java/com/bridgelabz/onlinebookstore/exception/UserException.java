package com.bridgelabz.onlinebookstore.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserException extends Exception {

	public UserException(String msg) {
		// TODO Auto-generated constructor stub
		super(msg);
	}

	private String message;

	public enum ExceptionType {
		INVALID_USER, INVALID_CREDENTIALS, ALREADY_VERFIED
	}

	public ExceptionType type;

}