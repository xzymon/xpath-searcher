package com.xzymon.xpath_searcher.gui.stain.handlers.control;

import com.xzymon.xpath_searcher.gui.stain.exceptions.InvalidCharacterException;

public class MultiValuedAbstractControlPoint implements ControlPoint {
	private int position;
	private char character = 0x0;
	
	public MultiValuedAbstractControlPoint(int position, char character) throws InvalidCharacterException{
		this.position = position;
		this.character = character;
	}

	@Override
	public int getPosition() {
		return position;
	}

	@Override
	public char getChar() {
		return character;
	}
}
