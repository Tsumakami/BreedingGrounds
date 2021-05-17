package com.BreedingGrounds.exceptions;

public class JwtTokenException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public JwtTokenException(String exception) {
		super(exception);
	}
}
