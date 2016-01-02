package com.xzymon.xpath_searcher.core.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingParserListener implements ParserListener {
	private static Logger logger = LoggerFactory.getLogger(LoggingParserListener.class);

	@Override
	public void otherTag(int startPos, int lastPos) {
		logger.info("otherTag("+ startPos + ", " + lastPos + ")");
	}

	@Override
	public void lessThanStartChar(int position) {
		logger.info("lessThanStartChar(" + position + "");
	}

	@Override
	public void tagName(int startPos, int lastPos) {
		logger.info("tagName("+ startPos + ", " + lastPos + ")");
	}

	@Override
	public void tagGap(int startPos, int lastPos) {
		logger.info("tagGap("+ startPos + ", " + lastPos + ")");
	}

	@Override
	public void attributeName(int startPos, int lastPos) {
		logger.info("attributeName("+ startPos + ", " + lastPos + ")");
	}

	@Override
	public void attributeEqualsSign(int position) {
		logger.info("attributeEqualsSign(" + position + "");
	}

	@Override
	public void attributeValue(int startPos, int lastPos) {
		logger.info("attributeValue("+ startPos + ", " + lastPos + ")");
	}

	@Override
	public void greaterThanEndingChar(int position) {
		logger.info("greaterThanEndingChar(" + position + "");
	}

	@Override
	public void error(int startPos, int lastPos) {
		logger.info("error("+ startPos + ", " + lastPos + ")");
	}

	@Override
	public void closingSlash(int position) {
		logger.info("closingSlash(" + position + "");
	}

	@Override
	public void rawText(int startPos, int lastPos) {
		logger.info("rawText("+ startPos + ", " + lastPos + ")");
	}

}
