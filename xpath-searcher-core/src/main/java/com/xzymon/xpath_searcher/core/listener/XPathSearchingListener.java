package com.xzymon.xpath_searcher.core.listener;

import org.w3c.dom.Node;

/**
 * Interface dla listenerów nasłuchujących zdarzeń generowanych przez parser
 * XPath
 * @author root
 *
 */
public interface XPathSearchingListener {
	/**
	 * Metoda przeznaczona do obsługi kolejnego węzła z listy węzłów wykrytych
	 * przez parser XPath podczas przeszukiwania dokumentu w poszukiwaniu węzłów
	 * spełniających kryteria podanego do parsera wyrażenia XPath.
	 * @param node - wykryty węzeł
	 * @param expression - wyrażenie dla którego parser dokonuje przeszukiwania
	 * @param nodeId - index węzła w liście wykrytych węzłów
	 */
	void nextNode(Node node, String expression, int nodeId);
	/**
	 * Metoda przeznaczona do obsługi powiadomienia o zakończeniu pracy przez
	 * parser XPath. Wywołanie tej metody przez parser oznacza, że wszystkie
	 * wykryte węzły spełniające podane wyrażenie XPath zostały już przekazane
	 * do obsługi listenerom tego typu {@link XPathSearchingListener} i że już
	 * żaden więcej węzeł nie zostanie zwrócony dla tego wyrażenia w bieżącym
	 * wyszukiwaniu.
	 * @param expression - wyrażenie dla którego parser wyszukiwał węzły
	 */
	void nodesExhausted(String expression);
	/**
	 * Metoda przeznaczona do obsługi powiadomienia o ilości węzłów znalezionych
	 * przez parser XPath dla podanego wyrażenia XPath.
	 * @param expression - wyrażenie dla którego wykonywano wyszukiwanie
	 * @param count - liczba znalezionych węzłów
	 */
	void foundNodesCount(String expression, int count);
	/**
	 * Metoda przeznaczona do obsługi powiadomienia o tym. że nastąpił reset
	 * stanu parsera.
	 * Parser XPath zwraca wyniki wyszukiwania w postaci Listy węzłów.
	 * Metoda {@link #nextNode(Node, String, int)} otrzymuje powiadomienie o 
	 * indeksie otrzymanego węzła na liście znalezionych węzłów. Reset powoduje
	 * wyzerowanie indeksu węzła który ma być zwrócony jako kolejny, zatem 
	 * o ile nie zostanie wywołane kolejne wyszukiwanie to następny węzeł 
	 * zwrócony przez metodę {@link #nextNode(Node, String, int)} będzie miał 
	 * nodeId = 0 - co oznacza że węzły będą ponownie zwracane od początku.
	 * @param expression - wyrażenie dla którego wykonywano ostatnie wyszukiwanie
	 */
	void stateReset(String expression);
	/**
	 * Metoda przeznaczona do obsługi powiadomienia o tym, że wewnętrzny stan
	 * parsera został wyczyszczony - usunięto z niego informacje o wynikach
	 * ostatniego wyszukiwania oraz samo wyrażenie XPath.
	 */
	void stateClear();
}