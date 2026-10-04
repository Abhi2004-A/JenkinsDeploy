package com.dcl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


public class AppException extends RuntimeException{
	
	private HttpStatus httpStatus;
	
	public HttpStatus gethttpStatus() {
		return httpStatus;
	}
	
	public AppException(String message, HttpStatus httpStatus) {
		super(message);
		this.httpStatus=httpStatus;
	}

}
