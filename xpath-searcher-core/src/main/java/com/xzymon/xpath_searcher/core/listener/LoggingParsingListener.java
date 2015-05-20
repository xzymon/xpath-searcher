package com.xzymon.xpath_searcher.core.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingParsingListener implements ParsingListener {
	private static final Logger logger = LoggerFactory.getLogger(LoggingParsingListener.class.getName());

	public void aboutToParseDocument() {
		logger.info("about to parse Document...");
	}

	public void parsingFinished() {
		logger.info("Document parsing finished.");
	}

	public void nodesBound(int boundElementsCount, int boundAttributesCount) {
		logger.info(String.format("Nodes bound : elements=%1$d, attributes=%2$d", boundElementsCount, boundAttributesCount));
	}

}
