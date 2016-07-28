package com.javatest.shoplocator.exception;

public class InsufficientInputException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public InsufficientInputException(String message){
		super(message);
	}
}
