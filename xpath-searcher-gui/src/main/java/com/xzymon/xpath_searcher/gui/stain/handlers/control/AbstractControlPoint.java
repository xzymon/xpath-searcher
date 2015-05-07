package com.xzymon.xpath_searcher.gui.stain.handlers.control;

public abstract class AbstractControlPoint implements ControlPoint{
	private int position;
	private char character = 0x0;
	
	public AbstractControlPoint(int position){
		this.position = position;
	}

	@Override
	public int getPosition() {
		return position;
	}

	@Override
	public char getChar() {
		return character;
	}
	
	protected void setChar(char character){
		this.character = character;
	}
}
