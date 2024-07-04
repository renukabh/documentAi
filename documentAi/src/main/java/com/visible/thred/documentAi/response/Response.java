package com.visible.thred.documentAi.response;

import org.springframework.http.HttpStatus;

public class Response <T>{

	private HttpStatus status;
	private String message;
	private T data;
	private Exception exception;
	
	public Response() {
		super();
	}
	
	public Response(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	
	public Response(HttpStatus status, String message,Exception exception) {
		super();
		this.status = status;
		this.message = message;
		this.exception=exception;
	}
	public Response(HttpStatus status, String message, T data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}
	public HttpStatus getstatus() {
		return status;
	}
	public void setstatus(HttpStatus status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	
	
}
