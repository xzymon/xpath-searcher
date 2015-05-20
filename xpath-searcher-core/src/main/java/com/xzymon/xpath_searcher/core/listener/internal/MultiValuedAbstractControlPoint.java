package com.xzymon.xpath_searcher.core.listener.internal;

import com.xzymon.xpath_searcher.core.exception.InvalidCharacterException;


public class MultiValuedAbstractControlPoint implements ControlPoint {
	private int position;
	private char character = 0x0;
	
	public MultiValuedAbstractControlPoint(int position, char character) throws InvalidCharacterException{
		this.position = position;
		this.character = character;
	}

	public int getPosition() {
		return position;
	}

	public char getChar() {
		return character;
	}
}
