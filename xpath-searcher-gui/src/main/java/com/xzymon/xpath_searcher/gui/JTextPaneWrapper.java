package com.xzymon.xpath_searcher.gui;

import java.io.InputStream;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzymon.xpath_searcher.gui.stain.XmlStylePalette;
import com.xzymon.xpath_searcher.gui.stain.exceptions.SlicingException;
import com.xzymon.xpath_searcher.gui.stain.handlers.ProcessingHandler;
import com.xzymon.xpath_searcher.gui.stain.handlers.StainTextPaneHandler;
import com.xzymon.xpath_searcher.gui.stain.slicers.ImprovedSlicer;

public class JTextPaneWrapper extends JTextPane {
	private static final long serialVersionUID = 3456655673318302723L;
	private static final Logger logger = LoggerFactory.getLogger(JTextPaneWrapper.class.getName());
	
	// inner-only variables
	private boolean wrapState = true;
	private ImprovedSlicer slicer = null;
	private ProcessingHandler slicingHandler = null;
	
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
	}
	
	public boolean loadStream(InputStream is) {
		boolean result = false;
		try{
			slicer = new ImprovedSlicer(is);
			newDocument();
			slicingHandler = new StainTextPaneHandler(this, palette, slicer.getSavedChars());
			slicer.setProcessingHandler(slicingHandler);
			slicer.slice();
			result = true;
		} catch (SlicingException e1) {
			e1.printStackTrace();
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

	public void appendStyledString(String str, SimpleAttributeSet sas) throws BadLocationException {
		getImmutableXMLDocument().mutable();
		StyledDocument document = (StyledDocument) getDocument();
		document.insertString(document.getLength(), str, sas);
		getImmutableXMLDocument().immutable();
	}
	
	public void stainAgain(){
		newDocument();
		slicer.useHandler();
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
		String str = new String(slicer.getSavedChars(), mark, length);
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
}
