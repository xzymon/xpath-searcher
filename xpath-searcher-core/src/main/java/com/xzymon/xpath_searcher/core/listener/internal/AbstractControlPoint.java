package com.xzymon.xpath_searcher.core.listener.internal;

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
