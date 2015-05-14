package com.xzymon.xpath_searcher.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XPathEngineWrapper {
	private Document xmlDocument = null;
	private XPathProcessingHandler handler = null;
	
	public XPathEngineWrapper(String xmlString){
		this.handler = new StandardHandler();
		init(xmlString);
	}
	
	public Document getXmlDocument() {
		return xmlDocument;
	}

	public XPathProcessingHandler getHandler() {
		return handler;
	}

	public void setHandler(XPathProcessingHandler handler) {
		this.handler = handler;
	}

	private void init(String xmlString) {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = builderFactory.newDocumentBuilder();
			handler.aboutToParseDocument();
			xmlDocument = builder.parse(new ByteArrayInputStream(xmlString.getBytes()));
			handler.parsingFinished();
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
	
	public NodeList find(String expression) throws XPathExpressionException{
		XPath xPath = XPathFactory.newInstance().newXPath();
		//String expression = "/Employees/Employee[@emplid='3333']/email";
		Node node = null;
		
		// read a nodelist using xpath
		NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
		for(int in=0; in<nodeList.getLength();in++){
			node = nodeList.item(in);
			handler.nodeFound(node);
		}
		return nodeList;
	}
}
