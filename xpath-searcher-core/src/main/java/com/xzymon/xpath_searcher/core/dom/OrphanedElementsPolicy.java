package com.xzymon.xpath_searcher.core.dom;

/**
 * Klasa (niemodyfikowalna) używana do enkapsulacji wybranych strategii postępowania z tagami
 * w sytuacjach gdy powodują one problemy w budowie drzewa {@link DocumentTreeRepresentation}. 
 * @author Szymon Ignaciuk
 *
 */
public class OrphanedElementsPolicy {
	private OrphanedOpeningTagMode openingMode;
	private OrphanedClosingTagMode closingMode;
	
	public OrphanedElementsPolicy(OrphanedOpeningTagMode opening, OrphanedClosingTagMode closing){
		this.openingMode = opening;
		this.closingMode = closing;
	}

	public OrphanedOpeningTagMode getOpeningMode() {
		return openingMode;
	}

	public OrphanedClosingTagMode getClosingMode() {
		return closingMode;
	}
	
	public boolean isOpeningDefault(){
		return openingMode.equals(OrphanedOpeningTagMode.DEFAULT);
	}
	
	public boolean isOpeningSelf(){
		return openingMode.equals(OrphanedOpeningTagMode.SELF_CLOSED);
	}
	
	public boolean isOpeningNextSameSpecies(){
		return openingMode.equals(OrphanedOpeningTagMode.CLOSE_ON_NEXT_SAME_SPECIES);
	}
	
	public boolean isClosingDefault(){
		return closingMode.equals(OrphanedClosingTagMode.DEFAULT);
	}
	
	public boolean isClosingRemove(){
		return closingMode.equals(OrphanedClosingTagMode.REMOVE);
	}
}
