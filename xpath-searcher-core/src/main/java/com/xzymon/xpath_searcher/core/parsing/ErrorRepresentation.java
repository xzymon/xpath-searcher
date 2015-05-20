package com.xzymon.xpath_searcher.core.parsing;

public class ErrorRepresentation implements SliceInterior {
	private int startsAt = -1;
	private int endsAt = -1;
	
	public int getStartPosition() {
		return startsAt;
	}
	public void setStartPosition(int pos) {
		this.startsAt = pos;
	}
	public int getEndPosition() {
		return endsAt;
	}
	public void setEndPosition(int pos) {
		this.endsAt = pos;
	}
}
