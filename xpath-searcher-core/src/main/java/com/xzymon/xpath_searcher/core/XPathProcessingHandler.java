package com.xzymon.xpath_searcher.core;

import org.w3c.dom.Node;

public interface XPathProcessingHandler {
	void aboutToParseDocument();
	void parsingFinished();
	void nodeFound(Node node);
}
