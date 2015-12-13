package com.xzymon.xpath_searcher.core.listener.internal;

/**
 * Klasa abstrakcyjna stanowiąca podstawową implementację interface'u 
 * ControlPoint dla punktów kontrolnych które mogą obudować tylko jeden
 * konkretny znak
 * @author Szymon Ignaciuk
 * @see ControlPoint
 */
public abstract class AbstractControlPoint implements ControlPoint{
	private int position;
	private char character = 0x0;
	
	public AbstractControlPoint(int position){
		this.position = position;
	}

	public int getPosition() {
		return position;
	}

	public char getChar() {
		return character;
	}
	
	protected void setChar(char character){
		this.character = character;
	}
}
