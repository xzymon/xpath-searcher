package com.xzymon.xpath_searcher.core;

import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xzymon.xpath_searcher.core.dom.NodeRepresentation;
import com.xzymon.xpath_searcher.core.listener.XPathSearchingListener;
import com.xzymon.xpath_searcher.core.parser.AttributeRepresentation;

/**
 * Wspólny Interface dla klas umożliwiających wykonanie interaktywnego 
 * wyszukiwania
 * @author Szymon Ignaciuk
 *
 */
public interface StateHolder {
	/**
	 * Inicjuje wykonanie kolejnego wyszukiwania przez parser XPath.
	 * @param expression - wyrażenie XPath do wyszukania węzłów.
	 * @return ilość znalezionych wezłów odpowiadających wyrażeniu XPath
	 * @throws XPathExpressionException
	 */
	int newSearch(String expression) throws XPathExpressionException;
	/**
	 * Żądanie przekazania zarejestrowanym {@link XPathSearchingListener}'om 
	 * kolejnego węzła z listy węzłów otrzymanych przez ostatnie wyszukiwanie
	 * XPath.
	 */
	void nextNode();
	/**
	 * Wymuszenie by znalezione węzły były przekazywane zarejestrowanym
	 * {@link XPathSearchingListener}'om od początku.
	 */
	void reset();
	/**
	 * Usunięcie wyników ostatniego wyszukiwania. Konieczne będzie wykonanie 
	 * nowego wyszukiwania by otrzymać następne wyniki.
	 */
	void clear();
	/**
	 * Testuje czy obiekt został prawidłowo zainicjowany.
	 * @return
	 */
	boolean isEmpty();
	/**
	 * Zwraca pełną listę wyników ostatniego wyszukiwania.
	 * @return
	 */
	NodeList getFoundNodeList();
	/**
	 * Zwraca obiekt wiążący węzeł obiektowego modelu dokumentu (DOM) 
	 * z miejscami w strumieniu w których występują ciągi znaków z których
	 * stworzono ten węzeł
	 * @param node - węzeł z obiektowego modelu dokumentu (DOM)
	 * @return
	 */
	NodeRepresentation getBoundSlicedNode(Node node);
	/**
	 * Zwraca obiekt wiążący węzeł obiektowego modelu dokumentu (DOM) - który jest atrybutem -
	 * z miejscami w strumieniu w których występują ciągi znaków z których
	 * stworzono ten węzeł
	 * @param node - węzeł z obiektowego modelu dokumentu (DOM)
	 * @return
	 */
	AttributeRepresentation getBoundAttributeRepresentation(Node node);
	List<XPathSearchingListener> getSearchingListeners();
	void addSearchingListener(XPathSearchingListener listener);
	void removeSearchingListener(XPathSearchingListener listener);
}
