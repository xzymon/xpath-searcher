package com.xzymon.xpath_searcher.gui.stain.handlers;

import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;

import com.xzymon.xpath_searcher.gui.JTextPaneWrapper;
import com.xzymon.xpath_searcher.gui.stain.XmlStylePalette;

public class StainTextPaneHandler implements ProcessingHandler {
	private JTextPaneWrapper textPaneWrapper;
	private XmlStylePalette palette;
	private char[] savedChars;
	
	public StainTextPaneHandler(JTextPaneWrapper paneWrapper, XmlStylePalette palette, char[] documentChars){
		this.textPaneWrapper = paneWrapper;
		this.palette = palette;
		this.savedChars = documentChars;
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
	
	public char[] getSavedChars() {
		return savedChars;
	}

	public void setSavedChars(char[] chars) {
		this.savedChars = chars;
	}

	@Override
	public void otherTag(int startPos, int lastPos) {
		String toInsert = new String(savedChars, startPos, lastPos-startPos+1);
		SimpleAttributeSet sas = palette.getOther();
		try {
			textPaneWrapper.appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void lessThanStartChar(int position) {
		String toInsert = new String(savedChars, position, 1);
		SimpleAttributeSet sas = palette.getTagCasing();
		try {
			textPaneWrapper.appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void tagName(int startPos, int lastPos) {
		String toInsert = new String(savedChars, startPos, lastPos-startPos+1);
		SimpleAttributeSet sas = palette.getTagName();
		try {
			textPaneWrapper.appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void tagGap(int startPos, int lastPos) {
		String toInsert = new String(savedChars, startPos, lastPos-startPos+1);
		SimpleAttributeSet sas = palette.getTagGap();
		try {
			textPaneWrapper.appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void attributeName(int startPos, int lastPos) {
		String toInsert = new String(savedChars, startPos, lastPos-startPos+1);
		SimpleAttributeSet sas = palette.getAttributeName();
		try {
			textPaneWrapper.appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void attributeEqualsSign(int position) {
		String toInsert = new String(savedChars, position, 1);
		SimpleAttributeSet sas = palette.getAttributeEqualsSign();
		try {
			textPaneWrapper.appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void attributeValue(int startPos, int lastPos) {
		String toInsert = new String(savedChars, startPos, lastPos-startPos+1);
		SimpleAttributeSet sas = palette.getAttributeValue();
		try {
			textPaneWrapper.appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void greaterThanEndingChar(int position) {
		String toInsert = new String(savedChars, position, 1);
		SimpleAttributeSet sas = palette.getTagCasing();
		try {
			textPaneWrapper.appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void error(int startPos, int lastPos) {
		String toInsert = new String(savedChars, startPos, lastPos-startPos+1);
		SimpleAttributeSet sas = palette.getError();
		try {
			textPaneWrapper.appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void closingSlash(int position) {
		String toInsert = new String(savedChars, position, 1);
		SimpleAttributeSet sas = palette.getClosingSlash();
		try {
			textPaneWrapper.appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void rawText(int startPos, int lastPos) {
		String toInsert = new String(savedChars, startPos, lastPos-startPos+1);
		SimpleAttributeSet sas = palette.getRawText();
		try {
			textPaneWrapper.appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

}
