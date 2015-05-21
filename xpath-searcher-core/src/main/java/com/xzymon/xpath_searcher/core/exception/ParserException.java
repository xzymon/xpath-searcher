package com.xzymon.xpath_searcher.core.exception;

import com.xzymon.xpath_searcher.core.listener.internal.ControlPoint;

public class ParserException extends Exception {
	private static final long serialVersionUID = -2832820851192457719L;
	private ControlPoint cp;
	
	public ParserException(ControlPoint cp){
		this.cp = cp;
	}

	@Override
	public String getMessage() {
		return String.format("ControlPoint char=%1$d [%2$s] at position=%3$d", (int)cp.getChar(), cp.getChar(), cp.getPosition());
	}
	
	public ControlPoint getControlPoint(){
		return this.cp;
	}
}
