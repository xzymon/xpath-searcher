package com.xzymon.xpath_searcher.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

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

public class StandardMain {
	private static final Logger logger = LoggerFactory.getLogger(StandardMain.class.getName());
	private String xmlFileAsString = null;

	public static final void main(String[] args) {
		StandardMain std = new StandardMain();
		std.loadFile();
		std.process();
	}

	private void process() {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = builderFactory.newDocumentBuilder();
			Document xmlDocument = builder.parse(new ByteArrayInputStream(xmlFileAsString.getBytes()));
			XPath xPath = XPathFactory.newInstance().newXPath();
			
			//String expression = "/Employees/Employee[@emplid='3333']/email";
			String expression = "/html/*";

			// read an xml node using xpath
			//Node node = (Node) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODE);
			Node node = null;
			
			// read a nodelist using xpath
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
			for(int in=0; in<nodeList.getLength();in++){
				node = nodeList.item(in);
				logger.info(String.format("node: %1$s", node.getNodeName()));
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void loadFile() {
		InputStream iStream = null;
		try {
			iStream = this.getClass().getResourceAsStream("/hibernate.xml");
			int avail = iStream.available();
			if (avail > 0) {
				byte[] bytes = new byte[avail];
				iStream.read(bytes);
				xmlFileAsString = new String(bytes);
			} else {
				xmlFileAsString = null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (iStream != null) {
				try {
					iStream.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}
