package com.tech.examportal.exception;

public class UserFoundException extends RuntimeException{
	
	public UserFoundException(String msg) {
		super(msg);
	}
	
	public UserFoundException() {
		super();
	}

}
