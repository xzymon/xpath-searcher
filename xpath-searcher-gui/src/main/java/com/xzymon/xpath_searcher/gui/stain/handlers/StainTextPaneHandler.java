package com.xzymon.xpath_searcher.gui.stain.handlers;

import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;

import com.xzymon.xpath_searcher.gui.JTextPaneWrapper;
import com.xzymon.xpath_searcher.gui.stain.XmlStylePalettesManager;

public class StainTextPaneHandler implements ProcessingHandler {
	private JTextPaneWrapper textPaneWrapper;
	private XmlStylePalettesManager palettesManager;
	private byte[] savedStream;
	
	public StainTextPaneHandler(JTextPaneWrapper paneWrapper, XmlStylePalettesManager palettesManager, byte[] documentChars){
		this.textPaneWrapper = paneWrapper;
		this.palettesManager = palettesManager;
		this.savedStream = documentChars;
	}
	
	public JTextPaneWrapper getTextPaneWrapper() {
		return textPaneWrapper;
	}

	public void setTextPaneWrapper(JTextPaneWrapper textPaneWrapper) {
		this.textPaneWrapper = textPaneWrapper;
	}
	
	public XmlStylePalettesManager getPalettesManager() {
		return palettesManager;
	}

	public void setPalettesManager(XmlStylePalettesManager styleManager) {
		this.palettesManager = styleManager;
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
		SimpleAttributeSet sas = palettesManager.getCurrentPalette().getOther();
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
		SimpleAttributeSet sas = palettesManager.getCurrentPalette().getTagCasing();
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
		SimpleAttributeSet sas = palettesManager.getCurrentPalette().getTagName();
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
		SimpleAttributeSet sas = palettesManager.getCurrentPalette().getTagGap();
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
		SimpleAttributeSet sas = palettesManager.getCurrentPalette().getAttributeName();
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
		SimpleAttributeSet sas = palettesManager.getCurrentPalette().getAttributeEqualsSign();
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
		SimpleAttributeSet sas = palettesManager.getCurrentPalette().getAttributeValue();
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
		SimpleAttributeSet sas = palettesManager.getCurrentPalette().getTagCasing();
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
		SimpleAttributeSet sas = palettesManager.getCurrentPalette().getError();
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
		SimpleAttributeSet sas = palettesManager.getCurrentPalette().getClosingSlash();
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
		SimpleAttributeSet sas = palettesManager.getCurrentPalette().getRawText();
		try {
			textPaneWrapper.appendStyledString(toInsert, sas);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
