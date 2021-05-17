package com.BreedingGrounds.exceptions;

public class JwtTokenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public JwtTokenException(String exception) {
		super(exception);
	}
	
	public JwtTokenException(String exception, Throwable cause) {
		super(exception, cause);
	}
	
	public JwtTokenException(String message, 
			Throwable cause, 
			boolean enableSuppression, 
			boolean writableStackTrace) {
		
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
