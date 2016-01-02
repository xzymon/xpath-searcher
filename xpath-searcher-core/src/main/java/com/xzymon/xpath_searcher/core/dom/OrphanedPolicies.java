package com.xzymon.xpath_searcher.core.dom;

import java.util.HashMap;
import java.util.Map;

import com.xzymon.xpath_searcher.core.parser.HalfElementRepresentation;

/**
 * Celem istnienia tej klasy jest dostarczenie do kreatora drzewa 
 * {@link DocumentTreeRepresentation#createDocument(java.io.InputStream, com.xzymon.xpath_searcher.core.XmlPreprocessingMode, OrphanedPolicies)}
 * możliwości elastycznego określania polityk postępowania z tagami
 * {@link HalfElementRepresentation} na wypadek gdyby sprawiły problemy przy
 * budownie drzewa. Klasa umożliwia wskazanie polityk szczegółowych (do stosowania
 * dla konkretych - określonych po nazwie - tagów) oraz domyślnej polityki (polityki
 * dla wszyskich tagów dla których nie wskazano polityki szczegółowej)
 * @author Szymon Ignaciuk
 *
 */
public class OrphanedPolicies {
	private OrphanedElementsPolicy defaultPolicy;
	private Map<String, OrphanedElementsPolicy> specificPolicies;
	
	public OrphanedPolicies(OrphanedElementsPolicy defaultPolicy){
		this.defaultPolicy = defaultPolicy;
		this.specificPolicies = new HashMap<String, OrphanedElementsPolicy>();
	}
	
	public OrphanedElementsPolicy getDefaultPolicy() {
		return defaultPolicy;
	}
	public void setDefaultPolicy(OrphanedElementsPolicy defaultPolicy) {
		this.defaultPolicy = defaultPolicy;
	}
	
	public final OrphanedElementsPolicy getPolicyForTag(String tagName){
		if(specificPolicies.containsKey(tagName)){
			OrphanedElementsPolicy policy = specificPolicies.get(tagName);
			return new OrphanedElementsPolicy(policy.getOpeningMode(), policy.getClosingMode());
		} else {
			return new OrphanedElementsPolicy(defaultPolicy.getOpeningMode(), defaultPolicy.getClosingMode());
		}
	}
	
	public final void setPolicyForTag(String tagName, OrphanedOpeningTagMode openingMode, OrphanedClosingTagMode closingMode){
		specificPolicies.put(tagName, new OrphanedElementsPolicy(openingMode, closingMode));
	}
	
	public final void setPolicyForTag(String tagName, OrphanedElementsPolicy policy){
		specificPolicies.put(tagName, policy);
	}
	/**
	 * Zwraca informację czy polityka dla danego tagu jest polityką jawnie 
	 * określoną czy domyślną.
	 * @param tagName - nazwa tagu
	 * @return true jeżeli macierzysty obiekt {@link OrphanedPolicies} zawiera
	 * jawnie zdefiniowaną politykę dla tego tagu, false - jeżeli dla tego tagu
	 * nie ma jawnie zdefiniowanej polityki - tj. metoda {@link #getPolicyForTag(String)}
	 * zwróci politykę domyślną.
	 */
	public final boolean hasSpecificPolicy(String tagName){
		return specificPolicies.containsKey(tagName);
	}
	/**
	 * Metoda wspomagająca - służąca jedynie dla wygody. Wykonuje kod 
	 * <pre>
	 * return getPolicyForTag(tagName).getOpeningMode();
	 * </pre>
	 * @param tagName
	 * @return
	 */
	public final OrphanedOpeningTagMode getOpeningModeForTag(String tagName){
		return getPolicyForTag(tagName).getOpeningMode();
	}
	/**
	 * Metoda wspomagająca - służąca jedynie dla wygody. Wykonuje kod 
	 * <pre>
	 * return getPolicyForTag(tagName).getClosingMode();
	 * </pre>
	 * @param tagName
	 * @return
	 */
	public final OrphanedClosingTagMode getClosingModeForTag(String tagName){
		return getPolicyForTag(tagName).getClosingMode();
	}
}
