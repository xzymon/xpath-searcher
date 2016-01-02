package com.xzymon.xpath_searcher.core.dom;

import com.xzymon.xpath_searcher.core.parser.HalfElementRepresentation;

/**
 * Enumeracja do reprezentowania stanu tagu {@link HalfElementRepresentation}.
 * Stan odnosi się do tego w jaki sposób parser budujący drzewo 
 * {@link DocumentTreeRepresentation} poradził sobie z tym tagiem
 * w procesie tworzenia struktury drzewa.
 * @author root
 *
 */
public enum TagTreeBuildingStatus {
	/**
	 * Reprezentuje stan oznaczający że jeszcze nie rozpoczęto przetwarzania tagu
	 * i tag ma nie ustalony stan.
	 */
	UNDEFINED,
	/**
	 * Reprezentuje stan oznaczający że tag {@link HalfElementRepresentation}
	 * został prawidłowo wbudowany w drzewo {@link DocumentTreeRepresentation}
	 */
	BUILT_IN, 
	/**
	 * <p>
	 * Reprezentuje <u>tymczasowy</u> stan oznaczający że tag 
	 * {@link HalfElementRepresentation}
	 * nie został wbudowany w drzewo {@link DocumentTreeRepresentation} z 
	 * powodu wywoływania problemów w zbudowaniu drzewa. 
	 * </p>
	 * <p>Stan może być 
	 * tymczasowo przypisany tagowi:
	 * <ul>
	 * <li>występującemu w nieprawidłowej kolejności (przykład: 
	 * &lt;u>&lt;i>&lt;/u>&lt;i> - tu tagi &lt;i> i &lt;/i> będą miały stan
	 * SKIPPED),</li>
	 * <li>otwierającemu dla którego nie znaleziono tagu zamykającego, </li>
	 * <li>zamykającemu dla którego nie znaleziono tagu otwierającego.</li>
	 * </p>
	 * <p>
	 * To że jest to stan <u>tymczasowy</u> oznacza że ostatecznie nastąpi
	 * przejście albo do stanu FIXED, albo do stanu REMOVED.
	 * </p>
	 */
	SKIPPED,
	/**
	 * Reprezentuje stan który oznacza że uprzednio tag posiadał stan tymczasowy
	 * SKIPPED, ale problem powodowany przez ten tag został naprawiony, tak że
	 * ostatecznie na bazie tagu utworzono węzeł {@link NodeRepresentation} 
	 * który został wbudowany w drzewo {@link DocumentTreeRepresentation}.
	 */
	FIXED, 
	/**
	 * Reprezentuje stan który oznacza że uprzednio tag posiadał stan tymczasowy
	 * SKIPPED, ale problem powodowany przez ten tag został naprawiony poprzez
	 * usunięcie tego tagu - zatem stworzone drzewo 
	 *  {@link DocumentTreeRepresentation} nie zawiera żadnego węzła
	 * {@link NodeRepresentation} zbudowanego na bazie tego tagu.
	 */
	REMOVED
}
