package com.xzymon.xpath_searcher.core.listener;

/**
 * Interface przeznaczony do nasłuchiwania informacji z parsera XML/HTML
 * o całkowitej ilości wykrytych elementów XML/HTML oraz 
 * o całkowitej ilości wykrytych atrybutów
 * @author Szymon Ignaciuk
 */
public interface BindingListener {
	/**
	 * Odbiera jako parametry informacje o całkowitej ilości wykrytych
	 * elementów XML/HTML oraz o całkowitej ilości atrybutów w tych elementach
	 * @param boundElementsCount - całkowita ilość elementów
	 * @param boundAttributesCount - całkowita ilość atrybutów
	 */
	void nodesBound(int boundElementsCount, int boundAttributesCount);
}
