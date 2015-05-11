package com.xzymon.xpath_searcher.gui;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

public class JTextPaneWrapper extends JTextPane {
	private static final long serialVersionUID = 3456655673318302723L;
	
	private boolean wrapState = true;

	public JTextPaneWrapper() {
		super();
	}

	public JTextPaneWrapper(StyledDocument sdoc) {
		super(sdoc);
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

	public void appendStyledString(String str, SimpleAttributeSet sas) throws BadLocationException {
		StyledDocument document = (StyledDocument) getDocument();
		document.insertString(document.getLength(), str, sas);
	}
}
