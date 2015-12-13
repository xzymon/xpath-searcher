package com.xzymon.xpath_searcher.core.listener.internal;

import com.xzymon.xpath_searcher.core.exception.InvalidCharacterException;

/**
 * Punkt kontrolny obudowujący znak biały, tj. znak dla którego metoda 
 * java.lang.Character.isWhitespace(character) zwraca wartość true
 * @author Szymon Ignaciuk
 * @see ControlPoint
 * @see java.lang.Character#isWhitespace()
 */
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
