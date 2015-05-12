package com.xzymon.xpath_searcher.gui;

import java.io.IOException;
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
	private byte[] savedStream = null;
	
	private XmlStylePalette palette = null;
	
	public JTextPaneWrapper() {
		super();
		this.newDocument();
		init();
	}

	private void init(){
		setLineWrap(false);
		setAutoscrolls(true);
		((ImmutableXMLDocument)this.getStyledDocument()).immutable();
	}
	
	public boolean loadStream(InputStream is) {
		boolean result = false;
		int avail;
		try{
			avail = is.available();
			if(avail>0){
				savedStream = new byte[avail];
				is.read(savedStream);
				
				slicer = new ImprovedSlicer(savedStream);
				newDocument();
				slicingHandler = new StainTextPaneHandler(this, palette, slicer.getSavedStream());
				slicer.setProcessingHandler(slicingHandler);
				slicer.slice();
				result = true;
			}
		} catch (IOException ex) {
			//logger.error(String.format("IOException during: new FileInputStream(\"%1$s\")", file.getAbsolutePath()));
			ex.printStackTrace();
		} catch (SlicingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			
		}
		return result;
	}
	
	@Override
	public void setStyledDocument(StyledDocument doc) {
		if(doc!=null && doc instanceof ImmutableXMLDocument){
			super.setStyledDocument(doc);
		} else {
			logger.info("invalid document type - not instance of ImmutableXMLDocument");
		}
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
		((ImmutableXMLDocument)this.getStyledDocument()).mutable();
		StyledDocument document = (StyledDocument) getDocument();
		document.insertString(document.getLength(), str, sas);
		((ImmutableXMLDocument)this.getStyledDocument()).immutable();
	}
	
	public void stainAgain(){
		newDocument();
		slicer.useHandler();
	}

	public void selectText(){
		ImmutableXMLDocument idoc = (ImmutableXMLDocument)getStyledDocument();
		Caret caret = this.getCaret();
		
		//caret.setDot(dot);
		//caret.moveDot(dot);
		int dot = caret.getDot();
		int mark = caret.getMark();
		int length = dot-mark;
		String str = new String(savedStream, mark, length);
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
