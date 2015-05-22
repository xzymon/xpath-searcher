package com.xzymon.xpath_searcher.core.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

public class LoggingXPathSearchingListener implements XPathSearchingListener {
		private static final Logger logger = LoggerFactory.getLogger(LoggingXPathSearchingListener.class.getName());

		@Override
		public void nextNode(Node node, String expression, int nodeId) {
			logger.info(String.format("nextNode(node, %2$s, %3$d)", node, expression, nodeId));
		}

		@Override
		public void nodesExhausted(String expression) {
			logger.info(String.format("nodesExhausted(%1$s)", expression));
		}

		@Override
		public void foundNodesCount(String expression, int count) {
			logger.info(String.format("foundNodesCount(%1$s, %2$d)", expression, count));
		}

		@Override
		public void stateReset(String expression) {
			logger.info(String.format("stateReset(%1$s)", expression));
		}

		@Override
		public void stateClear() {
			logger.info("stateClear()");
		}

}
