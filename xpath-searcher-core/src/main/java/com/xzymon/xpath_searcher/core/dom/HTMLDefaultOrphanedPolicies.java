package com.xzymon.xpath_searcher.core.dom;


/**
 * Podklasa {@link OrphanedPolicies} dostarczająca predefiniowanych polityk
 * dla konkretnych osieroconych tagów HTML.
 * @author Szymon Ignaciuk
 *
 */
public class HTMLDefaultOrphanedPolicies extends OrphanedPolicies {

	public HTMLDefaultOrphanedPolicies(OrphanedElementsPolicy defaultPolicy) {
		super(defaultPolicy);
		predefinePolicies();
	}

	public HTMLDefaultOrphanedPolicies() {
		super(new OrphanedElementsPolicy(OrphanedOpeningTagMode.DEFAULT,
				OrphanedClosingTagMode.REMOVE));
		predefinePolicies();
	}

	private void predefinePolicies(){
		OrphanedElementsPolicy selfRemovePolicy = new OrphanedElementsPolicy(OrphanedOpeningTagMode.SELF_CLOSED, OrphanedClosingTagMode.REMOVE);
		super.setPolicyForTag("meta", selfRemovePolicy);
		super.setPolicyForTag("link", selfRemovePolicy);
		super.setPolicyForTag("img", selfRemovePolicy);
		super.setPolicyForTag("input", selfRemovePolicy);
		super.setPolicyForTag("area", selfRemovePolicy);
		super.setPolicyForTag("br", selfRemovePolicy);
		super.setPolicyForTag("hr", selfRemovePolicy);
		
		OrphanedElementsPolicy nextSameRemovePolicy = new OrphanedElementsPolicy(OrphanedOpeningTagMode.CLOSE_ON_NEXT_SAME_SPECIES, OrphanedClosingTagMode.REMOVE);
		super.setPolicyForTag("ul", nextSameRemovePolicy);
		super.setPolicyForTag("ol", nextSameRemovePolicy);
		super.setPolicyForTag("li", nextSameRemovePolicy);
		super.setPolicyForTag("p", nextSameRemovePolicy);
	}
}
