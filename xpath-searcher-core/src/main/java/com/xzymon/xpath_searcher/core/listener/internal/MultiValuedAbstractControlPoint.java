package com.xzymon.xpath_searcher.core.listener.internal;

import com.xzymon.xpath_searcher.core.exception.InvalidCharacterException;

/**
 * Klasa abstrakcyjna stanowiąca podstawową implementację interface'u 
 * ControlPoint dla punktów kontrolnych które mogą obudować więcej niż jeden
 * konkretny znak (tzn. istnieje kilka różnych kodów znaków które mogą być 
 * obudowane przez taki punkt kontrolny).
 * @author Szymon Ignaciuk
 * @see ControlPoint
 */
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
