package com.desafio.starwars.exception;

public class CustomException extends Exception {

	private static final long serialVersionUID = -5131627198288498493L;

	public CustomException(final String string) {
		super(string);
	}
}