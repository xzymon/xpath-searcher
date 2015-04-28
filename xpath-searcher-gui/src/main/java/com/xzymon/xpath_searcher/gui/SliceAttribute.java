package com.xzymon.xpath_searcher.gui;

public class SliceAttribute {
	private int firstCharOffset;
	private int equalsSignOffset;
	private int length;
	
	public SliceAttribute(int firstCharOffset, int equalsSignOffset, int length){
		this.firstCharOffset = firstCharOffset;
		this.equalsSignOffset = equalsSignOffset;
		this.length = length;
	}
}
