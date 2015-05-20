package com.xzymon.xpath_searcher.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import com.xzymon.xpath_searcher.core.listener.XPathSearchingListener;

public class StandardHandler implements XPathSearchingListener {
	private static final Logger logger = LoggerFactory.getLogger(StandardHandler.class.getName());

	public void nextNode(Node node, String expression, int nodeId) {
		// TODO Auto-generated method stub
		
	}

	public void nodesExhausted(String expression) {
		// TODO Auto-generated method stub
		
	}

	public void foundNodesCount(String expression, int count) {
		// TODO Auto-generated method stub
		
	}

	public void stateReset(String expression) {
		// TODO Auto-generated method stub
		
	}

	public void stateClear() {
		// TODO Auto-generated method stub
		
	}

	
}
