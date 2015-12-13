package com.xzymon.xpath_searcher.core.parser;

/**
 * Interface reprezentujący spójny obszar tekstu podlegającego 
 * przetwarzaniu przez parser.
 * @author Szymon Ignaciuk
 */
public interface ElementInterior {
	/**
	 * Zwraca pozycję pierwszego znaku należącego do spójnego obszaru
	 * wykrytego przez parser. 
	 * @return pozycja znaku w tekście, 0+ i nie większa niż wartość zwracana przez {@link #getEndPosition()}
	 */
	int getStartPosition();
	/**
	 * Zwraca pozycję ostatniego znaku należącego do spójnego obszaru
	 * wykrytego przez parser.
	 * @return pozycja znaku z tekści, 0+ i nie mniejsza niż wartość zwracana przez {@link #getStartPosition()}
	 */
	int getEndPosition();
}
