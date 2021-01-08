package com.bridgelabz.onlinebookstore.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserException extends Exception {

	public UserException(String msg) {
		super(msg);
	}

	private String message;

	public enum ExceptionType {
		INVALID_USER, INVALID_CREDENTIALS, ALREADY_VERFIED
	}

	public ExceptionType type;

}