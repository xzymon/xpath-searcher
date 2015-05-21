package com.xzymon.xpath_searcher.core.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingBindingListener implements BindingListener {
	private static final Logger logger = LoggerFactory.getLogger(LoggingBindingListener.class.getName());

	public void nodesBound(int boundElementsCount, int boundAttributesCount) {
		logger.info(String.format("Nodes bound : elements=%1$d, attributes=%2$d", boundElementsCount, boundAttributesCount));
	}

}
