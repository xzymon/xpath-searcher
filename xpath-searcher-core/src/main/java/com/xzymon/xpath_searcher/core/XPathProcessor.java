package com.xzymon.xpath_searcher.core;

import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xzymon.xpath_searcher.core.listener.BindingListener;
import com.xzymon.xpath_searcher.core.listener.XPathSearchingListener;

/**
 * Interface dla klas przetwarzających dokument XML w poszukiwaniu węzłów
 * spełniających kryteria wskazanego wyrażenia XPath.
 * @author Szymon Ignaciuk
 *
 */
public interface XPathProcessor {
	/**
	 * Zwraca dokument w którym wyszukiwane są wyrażenia XPath.
	 * @return - dokument XML
	 */
	Document getXmlDocument();

	/**
	 * Zwraca listę listenerów aktualnie zarejestrowanych do nasłuchiwania 
	 * zdarzeń z tego obiektu.
	 * @return
	 */
	List<BindingListener> getBindingListeners();

	/**
	 * Zwraca listę listenerów aktualnie zarejestrowanych do nasłuchiwania 
	 * zdarzeń z tego obiektu.
	 * @return
	 */
	List<XPathSearchingListener> getSearchingListeners();

	/**
	 * Dokonuje rejestracji listenera do nasłuchiwania na tym obiekcie.
	 * @param listener
	 */
	void addSearchingListener(XPathSearchingListener listener);

	/**
	 * Wyrejestrowuje listener z nasłuchiwania na tym obiekcie.
	 * @param listener
	 */
	void removeSearchingListener(XPathSearchingListener listener);

	/**
	 * Testuje czy obiekt aktualnie posiada ustawione wyrażenie XPath
	 * do wyszukiwania węzłów.
	 * @return 
	 */
	boolean hasExpression();

	/**
	 * Zwraca aktualnie ustawione wyrażenie XPath do wyszukiwania węzłów przez
	 * ten obiekt.
	 * @return
	 */
	String getExpression();

	/**
	 * <p>Wywołanie tej metody powoduje przeprowadzenie nowego wyszukiwania 
	 * węzłów XPath dla wskazanego wyrażenia XPath w przechowywanym 
	 * dokumencie XML.</p>
	 * <p>Ponadto powoduje wygenerowanie powiadomień przez wyzwolenie metod
	 * {@link XPathSearchingListener#foundNodesCount(String, int)} w zarejestrowanych listenerach.</p>
	 * @param expression - wyrażenie XPath
	 * @return - ilość znalezionych węzłów 
	 * @throws XPathExpressionException
	 */
	int findNodes(String expression) throws XPathExpressionException;

	/**
	 * <p>Zwraca listę węzłów znalezionych podczas wykonania ostatniego 
	 * wyszukiwania (czyli podczas ostatniego wykonania metody {@link #findNodes(String)}</p>
	 * @return
	 */
	NodeList getAllFoundNodes();

	/**
	 * <p>Metoda której wykonanie powoduje zwrócenie kolejnego węzła z listy
	 * znalezionych przy ostatnim wyszukiwaniu węzłów</p>
	 * <p>Ponadto powoduje wygenerowanie powiadomień przez wyzwolenie metod
	 * {@link XPathSearchingListener#nextNode(Node, String, int)} w zarejestrowanych listenerach.</p>
	 * @return
	 */
	Node nextNode();

	/**
	 * Testuje czy są jeszcze jakieś węzły które nie zostały jeszcze przekazane
	 * zwrócone przez metodę {@link #findNodes(String)}.
	 * @return
	 */
	boolean hasMoreNodes();

	/**
	 * <p>
	 * Po wykonaniu tej metody węzły zwracane z metody {@link #nextNode()}
	 * będą zwracane od początku.</p>
	 * <p>Ponadto powoduje wygenerowanie powiadomień przez wyzwolenie metod
	 * {@link XPathSearchingListener#stateReset(String)} w zarejestrowanych listenerach.</p>
	 */
	void reset();

	/**
	 * <p> Opróżnienie processora z informacji wymaganych do przeprowadzenia 
	 * wyszukiwania. Aby otrzymać wynik z metody {@link #nextNode()} trzeba
	 * będzie najpierw wywołać metodę {@link #findNodes(String)}.</p>
	 * <p>Ponadto powoduje wygenerowanie powiadomień przez wyzwolenie metod
	 * {@link XPathSearchingListener#stateClear()} w zarejestrowanych listenerach.</p>
	 */
	void clear();

}