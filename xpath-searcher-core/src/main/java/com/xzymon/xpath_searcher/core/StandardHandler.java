package com.xzymon.xpath_searcher.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

public class StandardHandler implements XPathProcessingHandler {
	private static final Logger logger = LoggerFactory.getLogger(StandardHandler.class.getName());

	@Override
	public void aboutToParseDocument() {
		logger.info("parsing file to build Document");
	}

	@Override
	public void parsingFinished() {
		logger.info("File parsed.");
	}

	@Override
	public void nodeFound(Node node) {
		logger.info(String.format("node: %1$s", node.getNodeName()));
	}

}
