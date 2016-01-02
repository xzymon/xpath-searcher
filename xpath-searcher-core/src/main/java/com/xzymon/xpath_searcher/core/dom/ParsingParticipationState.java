package com.xzymon.xpath_searcher.core.dom;

import com.xzymon.xpath_searcher.core.parser.HalfElementRepresentation;

/**
 * Enumeracja reprezentująca stan elementu {@link HalfElementRepresentation} podczas
 * naprawiania obszarów drzewa {@link DocumentTreeRepresentation} zawierających błędy,
 * tzn. takich obszarów w których występują tagi {@link HalfElementRepresentation}
 * posiadające stan {@link TagTreeBuildingStatus#SKIPPED}.
 * @author Szymon Ignaciuk
 *
 */
public enum ParsingParticipationState {
	/**
	 * Tag o takiej wartości jest tagiem znajdującym się poza obszarem naprawiania.
	 */
	OUTER,
	/**
	 * Wartość, którą oznaczone zostają tagi otwierający i zamykający węzła 
	 * {@link NodeRepresentation} który jest rodzicem (wspólnym korzeniem)
	 * dla tagów zawartych w przetwarzanym obszarze. Cały przetwarzany
	 * obszar (w którym naprawiane są tagi) zawiera się wewnątrz węzła którego
	 * tagi (otwierający i zamykający) są oznaczone tą wartością. Zatem tagi
	 * oznaczone tą wartością wyznaczają bieżące granice (początek i koniec)
	 * naprawianego obszaru.
	 */
	PARENT, 
	/**
	 * Tak oznaczony tag podlega manipulowaniu przez algorytm naprawiający obszar.
	 */
	INCLUDED, 
	/**
	 * <p>
	 * Tak oznaczony tag został wyłączony z manipulowania przez algorytm
	 * naprawiający obszar. Oznacza to, że jeśli podczas naprawiania tego obszaru
	 * tag ma status {@link TagTreeBuildingStatus#SKIPPED} to status ten nie 
	 * zostanie zmodyfikowany (podczas naprawiania <u>bieżącego</u> obszaru.
	 * </p>
	 * <p>
	 * Jeżeli natomiast tak oznaczony tag ma status inny niż
	 * {@link TagTreeBuildingStatus#SKIPPED} - to węzeł zbudowany w oparciu o
	 * ten tag może zostać przemieszczony w strukturze (tzn. jego rodzic lub 
	 * rodzic jego rodzica może zostać zmieniony).
	 * </p>
	 */
	EXCLUDED
}
