package com.xzymon.xpath_searcher.core.listener.internal;

import com.xzymon.xpath_searcher.core.exception.InvalidCharacterException;

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
