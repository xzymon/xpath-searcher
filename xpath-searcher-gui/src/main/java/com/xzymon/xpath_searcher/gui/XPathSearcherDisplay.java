package com.xzymon.xpath_searcher.gui;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import javax.xml.xpath.XPathExpressionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xzymon.xpath_searcher.core.StateHolder;
import com.xzymon.xpath_searcher.core.XMLStateHolder;
import com.xzymon.xpath_searcher.core.XmlPreprocessingMode;
import com.xzymon.xpath_searcher.core.converter.HTMLStreamConverter;
import com.xzymon.xpath_searcher.core.converter.XMLStreamConverter;
import com.xzymon.xpath_searcher.core.dom.DocumentTreeRepresentation;
import com.xzymon.xpath_searcher.core.dom.HTMLDefaultOrphanedPolicies;
import com.xzymon.xpath_searcher.core.dom.NodeRepresentation;
import com.xzymon.xpath_searcher.core.dom.OrphanedPolicies;
import com.xzymon.xpath_searcher.core.exception.ParserException;
import com.xzymon.xpath_searcher.core.listener.LoggingBindingListener;
import com.xzymon.xpath_searcher.core.listener.BindingListener;
import com.xzymon.xpath_searcher.core.listener.ParserListener;
import com.xzymon.xpath_searcher.core.listener.XPathSearchingListener;
import com.xzymon.xpath_searcher.core.parser.AttributeRepresentation;
import com.xzymon.xpath_searcher.core.parser.HalfElementRepresentation;
import com.xzymon.xpath_searcher.gui.stain.XmlStylePalette;

/**
 * <p>Graficzny komponent umożliwiający wizualizacją dokumentu w którym zachodzi
 * wyszukiwanie XPath wraz z podświetlaniem wyników wyszukiwania.</p>
 * <p>Nie jest to komponent dostarczający sam z siebie pełnej funkcjonalności wyszukiwania,
 * choć odpowiednie spięcie go z innymi komponentami (wywołującymi odpowiednie metody
 * z tego komponentu) umożliwia uzyskanie takiej funkcjonalności.
 * </p>
 * @author Szymon Ignaciuk
 * @since 0.1.1.Final
 */
public class XPathSearcherDisplay extends JTextPane implements StateHolder, ParserListener, XPathSearchingListener{
	private static final long serialVersionUID = 3456655673318302723L;
	private static final Logger logger = LoggerFactory.getLogger(XPathSearcherDisplay.class.getName());
	
	private boolean wrapState = true;
	
	private XMLStateHolder stateHolder;
	
	private BindingListener bl = null;
	private List<BindingListener> bindingListeners = null;
	
	private XmlStylePalette palette = null;
	
	private List<String> standardCssElementsToRemove;
	
	public XPathSearcherDisplay() {
		super();
		this.newDocument();
		init();
	}

	private void init(){
		setLineWrap(false);
		setAutoscrolls(true);
		getImmutableXMLDocument().immutable();
		
		bindingListeners = new LinkedList<BindingListener>();
		bl = new LoggingBindingListener();
		bindingListeners.add(bl);
		
		standardCssElementsToRemove = new ArrayList<String>();
		standardCssElementsToRemove.add("head");
		standardCssElementsToRemove.add("img");
		standardCssElementsToRemove.add("input");
		standardCssElementsToRemove.add("br");
		standardCssElementsToRemove.add("area");
		standardCssElementsToRemove.add("button");
		standardCssElementsToRemove.add("script");
	}
	
	private boolean loadStream(InputStream is) {
		boolean result = false;
		try{
			newDocument();
			stateHolder = new XMLStateHolder(is, bindingListeners);
			stateHolder.addParserListener(this);
			stateHolder.addSearchingListener(this);
			stateHolder.invokeParserListeners();
			result = true;
		} catch (com.xzymon.xpath_searcher.core.exception.ParserException e) {
			e.printStackTrace();
		} finally {
			
		}
		return result;
	}
	
	public boolean loadXMLStream(InputStream is) {
		boolean result = false;
		InputStream filteredIs = null;
		try{
			XMLStreamConverter xmlConverter = new XMLStreamConverter(is);
			filteredIs = xmlConverter.getConvertedStream();
			loadStream(filteredIs);
			result = true;
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParserException e1) {
			e1.printStackTrace();
		} finally {
			if(filteredIs!=null){
				try{
					filteredIs.close();
				} catch(IOException ex){
					ex.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public boolean loadHTMLStreamWithJSoup(InputStream is, List<String> cssElementsToRemove) {
		boolean result = false;
		InputStream filteredIs = null;
		InputStream preparedIs = null;
		
		if(cssElementsToRemove!=null && cssElementsToRemove.size()>0){
			standardCssElementsToRemove.addAll(cssElementsToRemove);
		}
		/*
		cssElementsToRemove.add("div.footer1");
		cssElementsToRemove.add("div.footer2");
		cssElementsToRemove.add("div.footer3");
		cssElementsToRemove.add("div.footer4");
		//cssElementsToRemove.add("div.center");
		cssElementsToRemove.add("div.askCookies");
		cssElementsToRemove.add("div.hidden");
		*/
		
		try{
			HTMLStreamConverter htmlConverter = new HTMLStreamConverter(is);
			filteredIs = htmlConverter.getConvertedStream();
			//preparedIs = prepareHtmlWithJsoup(filteredIs, standardCssElementsToRemove);
			preparedIs = prepareHtmlWithJsoup(filteredIs, standardCssElementsToRemove);
			result = loadStream(preparedIs);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			if(filteredIs!=null){
				try{
					filteredIs.close();
				} catch(IOException ex){
					ex.printStackTrace();
				}
			}
			if(preparedIs!=null){
				try{
					preparedIs.close();
				} catch(IOException ex){
					ex.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public boolean loadHTMLStream(InputStream is, List<String> cssElementsToRemove) {
		boolean result = false;
		InputStream filteredIs = null;
		InputStream preparedIs = null;
		
		if(cssElementsToRemove!=null && cssElementsToRemove.size()>0){
			standardCssElementsToRemove.addAll(cssElementsToRemove);
		}
		/*
		cssElementsToRemove.add("div.footer1");
		cssElementsToRemove.add("div.footer2");
		cssElementsToRemove.add("div.footer3");
		cssElementsToRemove.add("div.footer4");
		//cssElementsToRemove.add("div.center");
		cssElementsToRemove.add("div.askCookies");
		cssElementsToRemove.add("div.hidden");
		*/
		
		try{
			HTMLStreamConverter htmlConverter = new HTMLStreamConverter(is);
			filteredIs = htmlConverter.getConvertedStream();
			//preparedIs = prepareHtmlWithJsoup(filteredIs, standardCssElementsToRemove);
			preparedIs = prepareHtml(filteredIs, standardCssElementsToRemove);
			result = loadStream(preparedIs);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			if(filteredIs!=null){
				try{
					filteredIs.close();
				} catch(IOException ex){
					ex.printStackTrace();
				}
			}
			if(preparedIs!=null){
				try{
					preparedIs.close();
				} catch(IOException ex){
					ex.printStackTrace();
				}
			}
		}
		return result;
	}
	
	private InputStream prepareHtmlWithJsoup(InputStream srcStream, List<String> elementTypesToRemove) throws IOException{
		InputStream resultStream = null;
		int avail = srcStream.available();
		int removeCount;
		if(avail>0){
			byte[] bytes = new byte[avail];
			srcStream.read(bytes);
			String strSource = new String(bytes);
			logger.info(String.format("parsing by Jsoup..."));
			org.jsoup.nodes.Document parsedDoc = Jsoup.parse(strSource);
			for(String typeName : elementTypesToRemove){
				logger.info("JSoup: removing css elements \"" + typeName + "\"");
				Elements els = parsedDoc.select(typeName);
				removeCount = 0;
				for(Element el: els){
					el.remove();
					removeCount++;
				}
				logger.info("JSoup: " + removeCount +" of css elements \"" + typeName +"\" have been removed");
			}
			String fromHtml = parsedDoc.html();
			String corrected = fromHtml.replaceAll("&nbsp;", "&#160;");
			logger.info(String.format("html retrieved from Jsoup parsed document"));
			resultStream = new ByteArrayInputStream(corrected.getBytes());
		}
		return resultStream;
	}
	
	private InputStream prepareHtml(InputStream srcStream, List<String> elementTypesToRemove) throws IOException{
		InputStream resultStream = null;
		int avail = srcStream.available();
		int removeCount;
		if(avail>0){
			byte[] savedStream = new byte[avail];
			srcStream.read(savedStream);
			logger.info(String.format("building Document Tree (DocumentTreeRepresentation) ..."));
			OrphanedPolicies htmlDefaultPolicies = new HTMLDefaultOrphanedPolicies();
			com.xzymon.xpath_searcher.core.dom.DocumentTreeRepresentation docTree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
					htmlDefaultPolicies);
			logger.info("Document Tree built.");
			String fromHtml = docTree.stringifyTree();
			String corrected = fromHtml.replaceAll("&nbsp;", "&#160;");
			logger.info(String.format("Document Tree stringified - HTML prepared."));
			resultStream = new ByteArrayInputStream(corrected.getBytes());
		}
		return resultStream;
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
	
	/**
	 * Metoda której wywołanie powoduje wyczyszczenie stanu komponentu.
	 */
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
	
	public void appendStyledString(String str, SimpleAttributeSet sas) throws BadLocationException {
		getImmutableXMLDocument().mutable();
		StyledDocument document = (StyledDocument) getDocument();
		document.insertString(document.getLength(), str, sas);
		getImmutableXMLDocument().immutable();
	}
	
	public void stainAgain(){
		newDocument();
		this.stateHolder.invokeParserListeners();
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
			NodeRepresentation snode = getBoundSlicedNode(node);
			HalfElementRepresentation srep = snode.getOpeningSlice();
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
