package com.bridgelabz.onlinebookstore.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

	private int status;
	private String message;
	private Object data;

	public Response(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public Response(int status, String message, Object data) {
		this.message = message;
		this.status = status;
		this.data = data;
	}

}
