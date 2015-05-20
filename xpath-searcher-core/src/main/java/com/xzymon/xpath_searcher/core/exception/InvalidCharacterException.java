package com.xzymon.xpath_searcher.core.exception;

public class InvalidCharacterException extends Exception {
	private static final long serialVersionUID = -693511940567176310L;
	private char character;
	
	public InvalidCharacterException(char character){
		
	}

	@Override
	public String getMessage() {
		return String.format("character value=%1$d [%2$s]", (int)character, character);
	}
	
	
}
