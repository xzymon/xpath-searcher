package com.xzymon.xpath_searcher.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.xzymon.xpath_searcher.core.listener.BindingListener;
import com.xzymon.xpath_searcher.core.listener.XPathSearchingListener;

/**
 * Zadaniem tej klasy jest wykonywanie przeszukiwania przesłanego strumienia
 * w poszukiwaniu wyrażeń XPath (do tego celu jest wewnętrznie używana klasa 
 * {@link javax.xml.xpath.XPath}).
 * @author root
 *
 */
public class XPathProcessorImpl implements XPathProcessor {
	private static final Logger logger = LoggerFactory.getLogger(XPathProcessorImpl.class.getName());
	
	private Document xmlDocument = null;
	private List<BindingListener> bindingListeners;
	private List<XPathSearchingListener> searchingListeners;
	
	private String expression = null;
	private NodeList curNodes = null;
	private int nextNodeId = -1;
	private boolean endProcessed = false;
	
	public XPathProcessorImpl(InputStream is, List<BindingListener> bindingListenersList){
		this.bindingListeners = bindingListenersList;
		init(is);
	}
	
	/* (non-Javadoc)
	 * @see com.xzymon.xpath_searcher.core.XPathProcessor#getXmlDocument()
	 */
	@Override
	public Document getXmlDocument() {
		return xmlDocument;
	}

	/* (non-Javadoc)
	 * @see com.xzymon.xpath_searcher.core.XPathProcessor#getBindingListeners()
	 */
	@Override
	public List<BindingListener> getBindingListeners() {
		return bindingListeners;
	}
	
	/* (non-Javadoc)
	 * @see com.xzymon.xpath_searcher.core.XPathProcessor#getSearchingListeners()
	 */
	@Override
	public List<XPathSearchingListener> getSearchingListeners() {
		return searchingListeners;
	}
	
	/* (non-Javadoc)
	 * @see com.xzymon.xpath_searcher.core.XPathProcessor#addSearchingListener(com.xzymon.xpath_searcher.core.listener.XPathSearchingListener)
	 */
	@Override
	public void addSearchingListener(XPathSearchingListener listener){
		if(searchingListeners==null){
			searchingListeners = new LinkedList<XPathSearchingListener>();
		}
		searchingListeners.add(listener);
	}
	
	/* (non-Javadoc)
	 * @see com.xzymon.xpath_searcher.core.XPathProcessor#removeSearchingListener(com.xzymon.xpath_searcher.core.listener.XPathSearchingListener)
	 */
	@Override
	public void removeSearchingListener(XPathSearchingListener listener){
		if(searchingListeners!=null){
			searchingListeners.remove(listener);
		}
	}

	private void init(InputStream is) {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = builderFactory.newDocumentBuilder();
			logger.info(String.format("DocumentBuilder : namespaceAware=%1$s, validating=%2$s, xIncludeAware=%3$s", builder.isNamespaceAware(), builder.isValidating(), builder.isXIncludeAware()));
			xmlDocument = builder.parse(is);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.xzymon.xpath_searcher.core.XPathProcessor#hasExpression()
	 */
	@Override
	public boolean hasExpression(){
		return expression!=null;
	}
	
	/* (non-Javadoc)
	 * @see com.xzymon.xpath_searcher.core.XPathProcessor#getExpression()
	 */
	@Override
	public String getExpression(){
		return expression;
	}
	
	/* (non-Javadoc)
	 * @see com.xzymon.xpath_searcher.core.XPathProcessor#findNodes(java.lang.String)
	 */
	@Override
	public int findNodes(String expression) throws XPathExpressionException{
		int result = 0;
		this.expression = expression;
		if(hasExpression()){
			XPath xPath = XPathFactory.newInstance().newXPath();
			//String expression = "/Employees/Employee[@emplid='3333']/email";
			this.curNodes = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
			reset();
			if(searchingListeners!=null){
				for(XPathSearchingListener xl: searchingListeners){
					xl.foundNodesCount(expression, curNodes.getLength());
				}
			}
			result = curNodes.getLength();
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.xzymon.xpath_searcher.core.XPathProcessor#getAllFoundNodes()
	 */
	@Override
	public NodeList getAllFoundNodes() {
		return this.curNodes;
	}
	
	/* (non-Javadoc)
	 * @see com.xzymon.xpath_searcher.core.XPathProcessor#nextNode()
	 */
	@Override
	public Node nextNode(){
		Node result = null;
		if(hasMoreNodes()){
			result = curNodes.item(nextNodeId);
			if(searchingListeners!=null){
				for(XPathSearchingListener xl: searchingListeners){
					xl.nextNode(result, expression, nextNodeId);
				}
			}
			nextNodeId++;
		} else { 
			if(endProcessed==false){
				if(searchingListeners!=null){
					for(XPathSearchingListener xl: searchingListeners){
						xl.nodesExhausted(expression);
					}
				}
				this.endProcessed = true;
			}
		}
		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.xzymon.xpath_searcher.core.XPathProcessor#hasMoreNodes()
	 */
	@Override
	public boolean hasMoreNodes(){
		return (nextNodeId>-1)?(nextNodeId<curNodes.getLength()):false;
	}
	
	/* (non-Javadoc)
	 * @see com.xzymon.xpath_searcher.core.XPathProcessor#reset()
	 */
	@Override
	public void reset(){
		if(this.curNodes!=null && this.curNodes.getLength()>0){
			this.nextNodeId = 0;
			this.endProcessed = false;
		} else {
			this.nextNodeId = -1;
			this.endProcessed = true;
		}
		if(searchingListeners!=null){
			for(XPathSearchingListener xl: searchingListeners){
				xl.stateReset(expression);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.xzymon.xpath_searcher.core.XPathProcessor#clear()
	 */
	@Override
	public void clear(){
		this.nextNodeId = -1;
		this.curNodes = null;
		this.expression = null;
		if(searchingListeners!=null){
			for(XPathSearchingListener xl: searchingListeners){
				xl.stateClear();
			}
		}
	}
}
