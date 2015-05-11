package com.xzymon.xpath_searcher.gui.stain.handlers;

public interface ProcessingHandler {
	// dostarczanie informacji o trybach pracy w przedziałach znaków
	void otherTag(int startPos, int lastPos);
	void lessThanStartChar(int position);
	void tagName(int startPos, int lastPos);
	void tagGap(int startPos, int lastPos);
	void attributeName(int startPos, int lastPos);
	void attributeEqualsSign(int position);
	void attributeValue(int startPos, int lastPos);
	void greaterThanEndingChar(int position);
	void error(int startPos, int lastPos);
	void closingSlash(int position);
	void rawText(int startPos, int lastPos);
}
