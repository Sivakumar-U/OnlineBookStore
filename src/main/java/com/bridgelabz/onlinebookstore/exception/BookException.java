package com.bridgelabz.onlinebookstore.exception;

public class BookException extends Exception {

	public enum ExceptionType {
		BOOKS_NOT_AVAILABLE
	}

	public ExceptionType type;

	public BookException(String message, ExceptionType exceptionType) {
		super(message);
		this.type = exceptionType;
	}

	public BookException(String message) {
		super(message);
	}

}
