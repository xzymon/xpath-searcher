package com.xzymon.xpath_searcher.gui.stain.handlers.control;

import com.xzymon.xpath_searcher.gui.stain.exceptions.InvalidCharacterException;


public class WhitespaceControlPoint extends MultiValuedAbstractControlPoint {
	
	public WhitespaceControlPoint(int position, char character) throws InvalidCharacterException{
		super(position, character);
		if(!test(character)){
			throw new InvalidCharacterException(character);
		}
	}
	
	private boolean test(char character){
		return Character.isWhitespace(character);
	}

}
