package com.xzymon.xpath_searcher.gui;

public interface ProcessingHandler {
	// dostarczanie informacji o trybach pracy w przedziałach znaków
	void otherTag(int startPos, int length);
	void lessThanStartChar(int position);
	void tagName(int startPos, int length);
	void attributeName(int startPos, int length);
	void attributeEqualsSign(int position);
	void attributeValue(int startPos, int length);
	void greaterThanEndingChar(int position);
	void error(int startPos, int length);
}
