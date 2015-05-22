package com.xzymon.xpath_searcher.core.listener;

import org.w3c.dom.Node;

public class SystemOutStreamXPathSearchingListener implements XPathSearchingListener {

	@Override
	public void nextNode(Node node, String expression, int nodeId) {
		System.out.format("nextNode(node, %2$s, %3$d)\n", node, expression, nodeId);
	}

	@Override
	public void nodesExhausted(String expression) {
		System.out.format("nodesExhausted(%1$s)\n", expression);
	}

	@Override
	public void foundNodesCount(String expression, int count) {
		System.out.format("foundNodesCount(%1$s, %2$d)\n", expression, count);
	}

	@Override
	public void stateReset(String expression) {
		System.out.format("stateReset(%1$s)\n", expression);
	}

	@Override
	public void stateClear() {
		System.out.format("stateClear()\n");
	}

}
