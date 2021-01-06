package com.bridgelabz.onlinebookstore.exception;

public class EmailSendingException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private final int status;

	public EmailSendingException(String message, int status) {
		super(message);
		this.status = status;
	}

}