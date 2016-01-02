package com.xzymon.xpath_searcher.core.dom;

/**
 * <p>
 * Enumeracja reprezentująca tryb postępowania z osieroconymi tagami
 * otwierającymi przy budowie drzewa {@link DocumentTreeRepresentation}
 * - czyli z tagami otwierającymi które są w takiej sytuacji że nie
 * istnieje dla nich tag zamykający.
 * </p>
 * @author Szymon Ignaciuk
 */
public enum OrphanedOpeningTagMode {
	/**
	 * Strategia domyślna - dołożenie tagu zamykającego natychmiast po tagu
	 * otwierającym.
	 */
	DEFAULT, 
	/**
	 * Wewnętrzne zamknięcie tagu - np. osierocony tag &lt;meta> zostanie
	 * zamieniony na tag &lt;meta />
	 */
	SELF_CLOSED,
	//TODO: dodanie CLOSE_ON_NEXT_NAMED - zamykanie węzła na kolejnym tagu posiadającym nazwę
	/**
	 * Zamknięcie osieroconego tagu zostanie dokonane w miejscu poprzedzającym
	 * kolejny tag otwierający tego samego <i>gatunku</i> (co tag osierocony)
	 * będący rodzeństwem dla tagu osieroconego, lub - jeżeli takie rodzeństwo
	 * nie zostanie znalezione - zamknięcie zostanie umieszczone na końcu 
	 * rodzica - będzie poprzedzać tag zamykający rodzica. Np. 
	 * &lt;ul>&lt;li>&lt;li>&lt;/li>&lt;/ul> zostanie zamienione na
	 * &lt;ul>&lt;li>&lt;/li>&lt;li>&lt;/li>&lt;/ul>, a
	 * &lt;ul>&lt;li>&lt;p>&lt;/p>&lt;/ul> zostanie zamienione na
	 * &lt;ul>&lt;li>&lt;p>&lt;/p>&lt;/li>&lt;/ul>, 
	 */
	CLOSE_ON_NEXT_SAME_SPECIES
}
