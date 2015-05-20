package com.xzymon.xpath_searcher.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xzymon.xpath_searcher.core.decomposition.Slicer;
import com.xzymon.xpath_searcher.core.dom.SlicedNode;
import com.xzymon.xpath_searcher.core.exception.BuildingNodeStructureException;
import com.xzymon.xpath_searcher.core.exception.SlicingException;
import com.xzymon.xpath_searcher.core.listener.ParsingListener;
import com.xzymon.xpath_searcher.core.listener.SlicingListener;
import com.xzymon.xpath_searcher.core.listener.XPathSearchingListener;
import com.xzymon.xpath_searcher.core.parsing.AttributeRepresentation;

public class XMLStateHolder implements StateHolder{
	private static final Logger logger = LoggerFactory.getLogger(XMLStateHolder.class.getName());
	
	private char[] savedChars;
	private Map<Node, SlicedNode> elementsMap = null;
	private Map<Node, AttributeRepresentation> attributesMap = null;
	private XPathProcessor engine = null;
	private Slicer slicer = null;
	private boolean empty;
	
	private List<ParsingListener> parsingListeners;
	
	public XMLStateHolder(InputStream is, List<ParsingListener> parsingListenersList) throws SlicingException{
		empty = true;
		try{
			this.parsingListeners = parsingListenersList;
			int avail = is.available();
			if(avail>0){
				byte[] savedStream = new byte[avail];
				is.read(savedStream);
				ByteArrayInputStream bais = new ByteArrayInputStream(savedStream);
				InputStreamReader reader = new InputStreamReader(bais);
				char[] charBuf = new char[savedStream.length];
				int read = reader.read(charBuf);
				savedChars = new char[read];
				System.arraycopy(charBuf, 0, savedChars, 0, read);
				slicer = new Slicer(savedChars);
				engine = new XPathProcessor(new ByteArrayInputStream(savedStream), parsingListeners);
				int boundElements = bindElementsToText();
				int boundAttributes = bindAttributesToText();
				for(ParsingListener pl : parsingListeners){
					pl.nodesBound(boundElements, boundAttributes);
				}
				empty = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean hasExpression(){
		return engine.hasExpression();
	}
	
	public String getExpression(){
		return engine.getExpression();
	}
	
	public int newSearch(String expression) throws XPathExpressionException{
		return engine.findNodes(expression);
	}
	
	public void nextNode(){
		engine.nextNode();
	}
	
	public void reset(){
		engine.reset();
	}
	
	public void clear(){
		engine.clear();
	}
	
	public boolean isEmpty(){
		return empty;
	}
	
	public char[] getData(){
		return this.savedChars;
	}
	
	public NodeList getFoundNodeList(){
		return engine.getAllFoundNodes();
	}
	
	public SlicedNode getBoundSlicedNode(Node node){
		return elementsMap.get(node);
	}
	
	public AttributeRepresentation getBoundAttributeRepresentation(Node node){
		return attributesMap.get(node);
	}
	
	public void invokeSlicerListeners(){
		slicer.invokeListeners();
	}
	
	public List<SlicingListener> getSlicingListeners(){
		return slicer.getSlicingListeners();
	}
	
	public void addSlicingListener(SlicingListener listener){
		slicer.addSlicingListener(listener);
	}
	
	public void removeSlicingListener(SlicingListener listener){
		slicer.removeSlicingListener(listener);
	}
	
	public List<XPathSearchingListener> getSearchingListeners() {
		return engine.getSearchingListeners();
	}
	
	public void addSearchingListener(XPathSearchingListener listener){
		engine.addSearchingListener(listener);
	}
	
	public void removeSearchingListener(XPathSearchingListener listener){
		engine.removeSearchingListener(listener);
	}
	
	private int bindElementsToText(){
		int boundElements = 0;
		Node xPathNode = null;
		SlicedNode slicedNode = null;
		elementsMap = new HashMap<Node, SlicedNode>();
		try {
			engine.findNodes("//*");
			NodeList nodeList = engine.getAllFoundNodes();
			SlicedNode rootSlicedNode = slicer.buildStructure();
			SlicedNode.SlicedNodeIterator snIt = rootSlicedNode.iterator();
			for(int nodeLoop=0; nodeLoop<nodeList.getLength(); nodeLoop++){
				xPathNode = nodeList.item(nodeLoop);
				if(snIt.hasNext()){
					slicedNode = snIt.next();
					logger.info(String.format("parallel nodes: xpath=%2$s(%1$d), sliced=%3$s", nodeLoop, xPathNode.getNodeName(), slicedNode.getName()));
					if(xPathNode.getNodeName().equals(slicedNode.getName())){
						elementsMap.put(xPathNode, slicedNode);
						boundElements++;
					} else {
						logger.info(String.format("names NOT EQUAL: xpath=%2$s(%1$d), sliced=%3$s", nodeLoop, xPathNode.getNodeName(), slicedNode.getName()));
					}
				}
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BuildingNodeStructureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return boundElements;
	}
	
	private int bindAttributesToText(){
		int boundAttributes = 0;
		Node xPathNode = null;
		SlicedNode slicedNode = null;
		attributesMap = new HashMap<Node, AttributeRepresentation>();
		Iterator<AttributeRepresentation> attrIt = null;
		int attrCount;
		int processedAttrs;
		List<AttributeRepresentation> attrs = null;
		try {
			engine.findNodes("//*/@*");
			NodeList nodeList = engine.getAllFoundNodes();
			SlicedNode rootSlicedNode = slicer.buildStructure();
			SlicedNode.SlicedNodeIterator snIt = rootSlicedNode.iterator();
			for(int nodeLoop=0; nodeLoop<nodeList.getLength(); /*nodeLoop++*/){
				if(snIt.hasNext()){
					slicedNode = snIt.next();
					logger.info("processing node element " + slicedNode.getName());
					if(slicedNode.hasAttributes()){
						attrs = slicedNode.getAttributes();
						attrCount = attrs.size();
						attrIt = attrs.iterator();
						for(AttributeRepresentation ar : attrs){
							logger.info(String.format("known attribute: %1$s", ar.getName()));
						}
						processedAttrs = 0;
						while(processedAttrs<attrCount){
							xPathNode = nodeList.item(nodeLoop);
							for(AttributeRepresentation attrNode : attrs){
								logger.info(String.format("parallel nodes: xpath=%2$s(%1$d), attr=%3$s", nodeLoop, xPathNode.getNodeName(), attrNode.getName()));
								if(xPathNode.getNodeName().equals(attrNode.getName())){
									attributesMap.put(xPathNode, attrNode);
									boundAttributes++;
									nodeLoop++;
									processedAttrs++;
									break;
								} else {
									logger.info(String.format("names NOT EQUAL: xpath=%2$s(%1$d), attr=%3$s", nodeLoop, xPathNode.getNodeName(), attrNode.getName()));
								}
							}
						}
					}
					
				}
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BuildingNodeStructureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return boundAttributes;
	}
	
}
