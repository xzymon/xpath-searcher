package com.xzymon.xpath_searcher.gui;

import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;

public class JTextPaneWrapper extends JTextPane {
	private boolean wrapState = true;

	public JTextPaneWrapper(){
		super();
	}
	
	public JTextPaneWrapper(StyledDocument sdoc){
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
	
}
