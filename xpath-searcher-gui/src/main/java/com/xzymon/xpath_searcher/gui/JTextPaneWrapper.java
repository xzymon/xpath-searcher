package com.xzymon.xpath_searcher.gui;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import javax.xml.xpath.XPathExpressionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xzymon.xpath_searcher.core.StateHolder;
import com.xzymon.xpath_searcher.core.XMLStateHolder;
import com.xzymon.xpath_searcher.core.dom.SlicedNode;
import com.xzymon.xpath_searcher.core.listener.LoggingParsingListener;
import com.xzymon.xpath_searcher.core.listener.ParsingListener;
import com.xzymon.xpath_searcher.core.listener.SlicingListener;
import com.xzymon.xpath_searcher.core.listener.XPathSearchingListener;
import com.xzymon.xpath_searcher.core.parsing.AttributeRepresentation;
import com.xzymon.xpath_searcher.core.parsing.SliceRepresentation;
import com.xzymon.xpath_searcher.gui.stain.XmlStylePalette;

public class JTextPaneWrapper extends JTextPane implements StateHolder, SlicingListener, XPathSearchingListener{
	private static final long serialVersionUID = 3456655673318302723L;
	private static final Logger logger = LoggerFactory.getLogger(JTextPaneWrapper.class.getName());
	
	private boolean wrapState = true;
	//private ImprovedSlicer slicer = null;
	//private ProcessingHandler slicingHandler = null;
	
	private XMLStateHolder stateHolder;
	
	private ParsingListener pl = null;
	private List<ParsingListener> parsingListeners = null;
	
	private XmlStylePalette palette = null;
	
	public JTextPaneWrapper() {
		super();
		this.newDocument();
		init();
	}

	private void init(){
		setLineWrap(false);
		setAutoscrolls(true);
		getImmutableXMLDocument().immutable();
		
		parsingListeners = new LinkedList<ParsingListener>();
		pl = new LoggingParsingListener();
		parsingListeners.add(pl);
	}
	
	public boolean loadStream(InputStream is) {
		boolean result = false;
		try{
			newDocument();
			stateHolder = new XMLStateHolder(is, parsingListeners);
			stateHolder.addSlicingListener(this);
			stateHolder.addSearchingListener(this);
			stateHolder.invokeSlicerListeners();
			result = true;
		} catch (com.xzymon.xpath_searcher.core.exception.SlicingException e) {
			e.printStackTrace();
		} finally {
			
		}
		return result;
	}
	
	/*
	 * Akceptuje tylko ImmutableXMLDocument
	 * (non-Javadoc)
	 * @see javax.swing.JTextPane#setStyledDocument(javax.swing.text.StyledDocument)
	 */
	@Override
	public void setStyledDocument(StyledDocument doc) {
		if(doc!=null && doc instanceof ImmutableXMLDocument){
			super.setStyledDocument(doc);
		} else {
			logger.info("invalid document type - not instance of ImmutableXMLDocument");
		}
	}
	
	private ImmutableXMLDocument getImmutableXMLDocument(){
		return (ImmutableXMLDocument)this.getStyledDocument();
	}
	
	public void newDocument(){
		this.setStyledDocument(new ImmutableXMLDocument());
	}

	public boolean getScrollableTracksViewportWidth() {
		return wrapState;
	}

	public boolean isLineWrap() {
		return wrapState;
	}

	public void setLineWrap(boolean wrapState) {
		this.wrapState = wrapState;
	}
	
	public XmlStylePalette getPalette() {
		return palette;
	}

	public void setPalette(XmlStylePalette palette) {
		this.palette = palette;
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
	
	public SlicedNode getBoundSlicedNode(Node node){
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
	
	public void appendStyledString(String str, SimpleAttributeSet sas) throws BadLocationException {
		getImmutableXMLDocument().mutable();
		StyledDocument document = (StyledDocument) getDocument();
		document.insertString(document.getLength(), str, sas);
		getImmutableXMLDocument().immutable();
	}
	
	public void stainAgain(){
		newDocument();
		this.stateHolder.invokeSlicerListeners();
		//slicer.useHandler();
	}

	public void selectText(){
		ImmutableXMLDocument idoc = getImmutableXMLDocument();
		Caret caret = this.getCaret();
		
		//caret.setDot(dot);
		//caret.moveDot(dot);
		int dot = caret.getDot();
		int mark = caret.getMark();
		int length = dot-mark;
		if(length<0){
			int hlp = mark;
			mark = dot;
			dot = hlp;
			length = -length;
		}
		String str = new String(stateHolder.getData(), mark, length);
		try {
			idoc.mutable();
			idoc.remove(mark, length);
			idoc.insertString(mark, str, palette.getSelection());
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			idoc.immutable();
		}
		
	}
	
	

	@Override
	public void otherTag(int startPos, int lastPos) {
		String toInsert = new String(stateHolder.getData(), startPos, lastPos-startPos+1);
		SimpleAttributeSet sas = palette.getOther();
		try {
			appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void lessThanStartChar(int position) {
		String toInsert = new String(stateHolder.getData(), position, 1);
		SimpleAttributeSet sas = palette.getTagCasing();
		try {
			appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void tagName(int startPos, int lastPos) {
		String toInsert = new String(stateHolder.getData(), startPos, lastPos-startPos+1);
		SimpleAttributeSet sas = palette.getTagName();
		try {
			appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void tagGap(int startPos, int lastPos) {
		String toInsert = new String(stateHolder.getData(), startPos, lastPos-startPos+1);
		SimpleAttributeSet sas = palette.getTagGap();
		try {
			appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void attributeName(int startPos, int lastPos) {
		String toInsert = new String(stateHolder.getData(), startPos, lastPos-startPos+1);
		SimpleAttributeSet sas = palette.getAttributeName();
		try {
			appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void attributeEqualsSign(int position) {
		String toInsert = new String(stateHolder.getData(), position, 1);
		SimpleAttributeSet sas = palette.getAttributeEqualsSign();
		try {
			appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void attributeValue(int startPos, int lastPos) {
		String toInsert = new String(stateHolder.getData(), startPos, lastPos-startPos+1);
		SimpleAttributeSet sas = palette.getAttributeValue();
		try {
			appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void greaterThanEndingChar(int position) {
		String toInsert = new String(stateHolder.getData(), position, 1);
		SimpleAttributeSet sas = palette.getTagCasing();
		try {
			appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void error(int startPos, int lastPos) {
		String toInsert = new String(stateHolder.getData(), startPos, lastPos-startPos+1);
		SimpleAttributeSet sas = palette.getError();
		try {
			appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void closingSlash(int position) {
		String toInsert = new String(stateHolder.getData(), position, 1);
		SimpleAttributeSet sas = palette.getClosingSlash();
		try {
			appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void rawText(int startPos, int lastPos) {
		String toInsert = new String(stateHolder.getData(), startPos, lastPos-startPos+1);
		SimpleAttributeSet sas = palette.getRawText();
		try {
			appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	public void selectAllFoundNodes(){
		if(stateHolder.hasExpression()){
			stainAgain();
			NodeList list = getFoundNodeList();
			for(int nloop=0; nloop<list.getLength(); nloop++){
				logger.info("artificial invoke of nextNode");
				nextNode(list.item(nloop), stateHolder.getExpression(), nloop);
			}
		}
	}

	@Override
	public void nextNode(Node node, String expression, int nodeId) {
		logger.info(String.format("nextNode(node, %2$s, %3$d)", node, expression, nodeId));
		switch(node.getNodeType()){
		case Node.ELEMENT_NODE:
			//snode = bindingMap.get(node);
			SlicedNode snode = getBoundSlicedNode(node);
			SliceRepresentation srep = snode.getOpeningSlice();
			setSelectionStart(srep.getNameStartPosition());
			setSelectionEnd(srep.getNameEndPosition()+1);
			selectText();
			logger.info(String.format("found ELEMENT : \"%1$s\" value={%2$s}", node.getNodeName(), node.getNodeValue()));
			break;
		case Node.ATTRIBUTE_NODE:
			//attrRep = bindingAttrMap.get(node);
			AttributeRepresentation attrRep = getBoundAttributeRepresentation(node);
			setSelectionStart(attrRep.getStartPosition());
			setSelectionEnd(attrRep.getNameEndsAt()+1);
			selectText();
			logger.info(String.format("found ATTRIBUTE : \"%1$s\" value={%2$s}", node.getNodeName(), node.getNodeValue()));
			break;
		case Node.TEXT_NODE:
			logger.info(String.format("found TEXT : \"%1$s\" value={%2$s}", node.getNodeName(), node.getNodeValue()));
			break;
		default:
			logger.info(String.format("found \"%1$s\"", node.getNodeType()));
		}
		if(node.getNodeType()==Node.ELEMENT_NODE){
			
		}
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
