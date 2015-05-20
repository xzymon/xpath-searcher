package com.xzymon.xpath_searcher.core.listener;

import org.w3c.dom.Node;

public interface XPathSearchingListener {
	void nextNode(Node node, String expression, int nodeId);
	void nodesExhausted(String expression);
	void foundNodesCount(String expression, int count);
	void stateReset(String expression);
	void stateClear();
}