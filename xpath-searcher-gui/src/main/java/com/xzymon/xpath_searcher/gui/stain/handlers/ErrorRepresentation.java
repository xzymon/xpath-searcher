package com.xzymon.xpath_searcher.gui.stain.handlers;

public class ErrorRepresentation implements SliceInterior {
	private int startsAt = -1;
	private int endsAt = -1;
	
	@Override
	public int getStartPosition() {
		return startsAt;
	}
	public void setStartPosition(int pos) {
		this.startsAt = pos;
	}
	@Override
	public int getEndPosition() {
		return endsAt;
	}
	public void setEndPosition(int pos) {
		this.endsAt = pos;
	}
}
