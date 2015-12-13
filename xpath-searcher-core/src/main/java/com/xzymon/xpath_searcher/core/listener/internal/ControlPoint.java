package com.xzymon.xpath_searcher.core.listener.internal;

/**
 * Interfejs służący do reprezentacji pojedyńczego znaku specjalnego
 * - w założeniach mającego wpływ na proces parsowania źródłowego pliku
 * tekstowego.
 * @author Szymon Ignaciuk
 */
public interface ControlPoint {
	/**
	 * Dostarcza informacji o położeniu punktu kontrolnego w pliku tekstowym
	 * podlegającym parsowaniu.
	 * @return pozycja znaku w pliku ( >=0)
	 */
	int getPosition();
	/**
	 * Zwraca znak który opakowany przez ten punkt kontrolny
	 * @return znak (ASCII) na bazie którego zbudowano punkt kontrolny.
	 */
	char getChar();
}
