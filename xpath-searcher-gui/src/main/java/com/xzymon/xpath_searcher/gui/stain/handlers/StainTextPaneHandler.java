package com.xzymon.xpath_searcher.gui.stain.handlers;

import javax.swing.JTextPane;

public class StainTextPaneHandler implements ProcessingHandler {
	private JTextPane textPane;
	
	public JTextPane getTextPane() {
		return textPane;
	}

	public void setTextPane(JTextPane textPane) {
		this.textPane = textPane;
	}

	@Override
	public void otherTag(int startPos, int length) {
		// TODO Auto-generated method stub

	}

	@Override
	public void lessThanStartChar(int position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void tagName(int startPos, int length) {
		// TODO Auto-generated method stub

	}

	@Override
	public void attributeName(int startPos, int length) {
		// TODO Auto-generated method stub

	}

	@Override
	public void attributeEqualsSign(int position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void attributeValue(int startPos, int length) {
		// TODO Auto-generated method stub

	}

	@Override
	public void greaterThanEndingChar(int position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(int startPos, int length) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rawTest(int startPos, int lastPos) {
		// TODO Auto-generated method stub
		
	}

}
