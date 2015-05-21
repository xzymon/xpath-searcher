package com.xzymon.xpath_searcher.core;

import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xzymon.xpath_searcher.core.dom.NodeRepresentation;
import com.xzymon.xpath_searcher.core.listener.XPathSearchingListener;
import com.xzymon.xpath_searcher.core.parser.AttributeRepresentation;

public interface StateHolder {
	int newSearch(String expression) throws XPathExpressionException;
	void nextNode();
	void reset();
	void clear();
	boolean isEmpty();
	NodeList getFoundNodeList();
	NodeRepresentation getBoundSlicedNode(Node node);
	AttributeRepresentation getBoundAttributeRepresentation(Node node);
	List<XPathSearchingListener> getSearchingListeners();
	void addSearchingListener(XPathSearchingListener listener);
	void removeSearchingListener(XPathSearchingListener listener);
}
