package com.xzymon.xpath_searcher.core.exception;

public class ParserListenerException extends RuntimeException{
	private static final long serialVersionUID = 1312372503441110703L;
	private String name;
	private String hint;
	
	public ParserListenerException(String notificationName, String hint){
		this.name = notificationName;
		this.hint = hint;
	}

	@Override
	public String getMessage() {
		return "from " + name + ": " + hint;
	}
	
}
