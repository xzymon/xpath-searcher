package com.xzymon.xpath_searcher.core.listener;

public interface ParsingListener {
	void aboutToParseDocument();
	void parsingFinished();
	void nodesBound(int boundElementsCount, int boundAttributesCount);
}
