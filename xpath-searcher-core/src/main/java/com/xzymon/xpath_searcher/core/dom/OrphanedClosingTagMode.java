package com.xzymon.xpath_searcher.core.dom;

/**
 * <p>
 * Enumeracja reprezentująca tryb postępowania z osieroconymi tagami
 * zamykającymi przy budowie drzewa {@link DocumentTreeRepresentation}
 * - czyli z tagami zamykającymi które są w takiej sytuacji że nie
 * istnieje dla nich tag otwierający.
 * </p>
 * @author Szymon Ignaciuk
 *
 */
public enum OrphanedClosingTagMode {
	/**
	 * Strategia DEFAULT zakłada wstawienie tagu otwierającego bezpośrednio
	 * przed osieroconym tagiem zamykającym - tak że nie jest on już osierocony.
	 * Tak uzyskany element jest pusty. Np. jeżeli osierocony tag to &lt;/p>, 
	 * to zastosowanie tej strategii spowoduje że w jego miejscu strumień
	 * uzyskany z drzewa będzie zawierał &lt;p>&lt;/p> (pusty elemen p).
	 */
	DEFAULT,
	/**
	 * 
	 * Strategia REMOVE zakłada usunięcie takiego osieroconego tagu
	 * zamykającego, tj. sprawienie że tworzone drzewo
	 * {@link DocumentTreeRepresentation} nie będzie zawierało żadnego śladu po
	 * owym osieroconym tagu zamykającym.
	 */
	REMOVE
}
