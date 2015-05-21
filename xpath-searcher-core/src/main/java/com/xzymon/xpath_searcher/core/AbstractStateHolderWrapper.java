package com.xzymon.xpath_searcher.core;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xzymon.xpath_searcher.core.dom.NodeRepresentation;
import com.xzymon.xpath_searcher.core.listener.LoggingBindingListener;
import com.xzymon.xpath_searcher.core.listener.BindingListener;
import com.xzymon.xpath_searcher.core.listener.XPathSearchingListener;
import com.xzymon.xpath_searcher.core.parser.AttributeRepresentation;

public abstract class AbstractStateHolderWrapper implements StateHolder {
	private XMLStateHolder stateHolder;
	
	private BindingListener pl = null;
	private List<BindingListener> bindingListeners = null;
	
	public AbstractStateHolderWrapper() {
		init();
	}
	
	private void init(){
		bindingListeners = new LinkedList<BindingListener>();
		pl = new LoggingBindingListener();
		bindingListeners.add(pl);
	}
	
	public boolean loadStream(InputStream is) {
		boolean result = false;
		try{
			stateHolder = new XMLStateHolder(is, bindingListeners);
			stateHolder.invokeParserListeners();
			result = true;
		} catch (com.xzymon.xpath_searcher.core.exception.ParserException e) {
			e.printStackTrace();
		} finally {
			
		}
		return result;
	}
	
	public int newSearch(String expression) throws XPathExpressionException{
		return stateHolder.newSearch(expression);
	}
	
	public void nextNode(){
		if(stateHolder.hasExpression()){
			stateHolder.nextNode();
		}
	}
	
	public void reset(){
		stateHolder.reset();
	}
	
	public void clear(){
		stateHolder.clear();
	}
	
	public boolean isEmpty(){
		return stateHolder!=null?stateHolder.isEmpty():true;
	}
	
	public NodeList getFoundNodeList(){
		return stateHolder.getFoundNodeList();
	}
	
	public NodeRepresentation getBoundSlicedNode(Node node){
		return stateHolder.getBoundSlicedNode(node);
	}
	
	public AttributeRepresentation getBoundAttributeRepresentation(Node node){
		return stateHolder.getBoundAttributeRepresentation(node);
	}
	
	public List<XPathSearchingListener> getSearchingListeners() {
		return stateHolder.getSearchingListeners();
	}

	public void addSearchingListener(XPathSearchingListener listener) {
		stateHolder.addSearchingListener(listener);
	}

	public void removeSearchingListener(XPathSearchingListener listener) {
		stateHolder.removeSearchingListener(listener);
	}

}
