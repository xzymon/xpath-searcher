package com.xzymon.xpath_searcher.gui.stain.handlers;

import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;

import com.xzymon.xpath_searcher.gui.JTextPaneWrapper;
import com.xzymon.xpath_searcher.gui.stain.XmlStylePalette;
import com.xzymon.xpath_searcher.gui.stain.XmlStylePalettesManager;

public class StainTextPaneHandler implements ProcessingHandler {
	private JTextPaneWrapper textPaneWrapper;
	private XmlStylePalette palette;
	private byte[] savedStream;
	
	public StainTextPaneHandler(JTextPaneWrapper paneWrapper, XmlStylePalette palette, byte[] documentChars){
		this.textPaneWrapper = paneWrapper;
		this.palette = palette;
		this.savedStream = documentChars;
	}
	
	public JTextPaneWrapper getTextPaneWrapper() {
		return textPaneWrapper;
	}

	public void setTextPaneWrapper(JTextPaneWrapper textPaneWrapper) {
		this.textPaneWrapper = textPaneWrapper;
	}
	
	public XmlStylePalette getPalette() {
		return palette;
	}

	public void setPalette(XmlStylePalette palette) {
		this.palette = palette;
	}
	
	public byte[] getSavedStream() {
		return savedStream;
	}

	public void setSavedStream(byte[] savedStream) {
		this.savedStream = savedStream;
	}

	@Override
	public void otherTag(int startPos, int lastPos) {
		String toInsert = new String(savedStream, startPos, lastPos-startPos+1);
		SimpleAttributeSet sas = palette.getOther();
		try {
			textPaneWrapper.appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void lessThanStartChar(int position) {
		String toInsert = new String(savedStream, position, 1);
		SimpleAttributeSet sas = palette.getTagCasing();
		try {
			textPaneWrapper.appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void tagName(int startPos, int lastPos) {
		String toInsert = new String(savedStream, startPos, lastPos-startPos+1);
		SimpleAttributeSet sas = palette.getTagName();
		try {
			textPaneWrapper.appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void tagGap(int startPos, int lastPos) {
		String toInsert = new String(savedStream, startPos, lastPos-startPos+1);
		SimpleAttributeSet sas = palette.getTagGap();
		try {
			textPaneWrapper.appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void attributeName(int startPos, int lastPos) {
		String toInsert = new String(savedStream, startPos, lastPos-startPos+1);
		SimpleAttributeSet sas = palette.getAttributeName();
		try {
			textPaneWrapper.appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void attributeEqualsSign(int position) {
		String toInsert = new String(savedStream, position, 1);
		SimpleAttributeSet sas = palette.getAttributeEqualsSign();
		try {
			textPaneWrapper.appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void attributeValue(int startPos, int lastPos) {
		String toInsert = new String(savedStream, startPos, lastPos-startPos+1);
		SimpleAttributeSet sas = palette.getAttributeValue();
		try {
			textPaneWrapper.appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void greaterThanEndingChar(int position) {
		String toInsert = new String(savedStream, position, 1);
		SimpleAttributeSet sas = palette.getTagCasing();
		try {
			textPaneWrapper.appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void error(int startPos, int lastPos) {
		String toInsert = new String(savedStream, startPos, lastPos-startPos+1);
		SimpleAttributeSet sas = palette.getError();
		try {
			textPaneWrapper.appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void closingSlash(int position) {
		String toInsert = new String(savedStream, position, 1);
		SimpleAttributeSet sas = palette.getClosingSlash();
		try {
			textPaneWrapper.appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void rawText(int startPos, int lastPos) {
		String toInsert = new String(savedStream, startPos, lastPos-startPos+1);
		SimpleAttributeSet sas = palette.getRawText();
		try {
			textPaneWrapper.appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
