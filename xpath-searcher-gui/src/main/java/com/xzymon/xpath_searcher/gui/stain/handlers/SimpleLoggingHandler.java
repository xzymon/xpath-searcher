package com.xzymon.xpath_searcher.gui.stain.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleLoggingHandler implements ProcessingHandler {
	private static final Logger logger = LoggerFactory.getLogger(SimpleLoggingHandler.class.getName());

	@Override
	public void otherTag(int startPos, int lastPos) {
		logger.info(String.format("handler invoked for event %1$s(%2$d, %3$d)", "otherTag", startPos, lastPos));
	}

	@Override
	public void lessThanStartChar(int position) {
		logger.info(String.format("handler invoked for event %1$s(%2$d)", "lessThanStartChar", position));
	}

	@Override
	public void tagName(int startPos, int lastPos) {
		logger.info(String.format("handler invoked for event %1$s(%2$d, %3$d)", "tagName", startPos, lastPos));
	}

	@Override
	public void attributeName(int startPos, int lastPos) {
		logger.info(String.format("handler invoked for event %1$s(%2$d, %3$d)", "attributeName", startPos, lastPos));
	}

	@Override
	public void attributeEqualsSign(int position) {
		logger.info(String.format("handler invoked for event %1$s(%2$d)", "attributeEqualsSign", position));
	}

	@Override
	public void attributeValue(int startPos, int lastPos) {
		logger.info(String.format("handler invoked for event %1$s(%2$d, %3$d)", "attributeValue", startPos, lastPos));
	}

	@Override
	public void greaterThanEndingChar(int position) {
		logger.info(String.format("handler invoked for event %1$s(%2$d)", "greaterThanEndingChar", position));
	}

	@Override
	public void error(int startPos, int lastPos) {
		logger.info(String.format("handler invoked for event %1$s(%2$d, %3$d)", "error", startPos, lastPos));
	}

	@Override
	public void rawTest(int startPos, int lastPos) {
		logger.info(String.format("handler invoked for event %1$s(%2$d, %3$d)", "rawTest", startPos, lastPos));
	}

}
