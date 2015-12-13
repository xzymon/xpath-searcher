package com.xzymon.xpath_searcher.core.listener;

/**
 * Interface przeznaczony do odbierania od parsera XML/HTML informacji
 * o wykryciu podczas parsowania pozycji kolejnego znaku - co umożliwia 
 * budowę obiektowego drzewa dokumentu.
 * @author Szymon Ignaciuk
 */
public interface ParserListener {
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
