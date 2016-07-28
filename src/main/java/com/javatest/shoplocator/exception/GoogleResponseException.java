package com.javatest.shoplocator.exception;

public class GoogleResponseException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public GoogleResponseException(String message){
		super(message);
	}
}
