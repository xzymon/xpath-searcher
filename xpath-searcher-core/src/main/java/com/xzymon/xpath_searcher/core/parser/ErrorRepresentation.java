package com.xzymon.xpath_searcher.core.parser;

/**
 * Klasa reprezentująca spójny obszar tekstu przetwarzanego przez parser 
 * w sytuacji gdy ten obszar zawiera błąd (np. jest błędnie położony).
 * @author root
 *
 */
public class ErrorRepresentation implements ElementInterior {
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
