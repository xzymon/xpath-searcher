package com.xzymon.xpath_searcher.gui;

public class SliceAttribute {
	private int firstCharOffset = -1;
	private int equalsSignOffset = -1;
	private int length = -1;
	
	public int getFirstCharOffset() {
		return firstCharOffset;
	}
	public void setFirstCharOffset(int firstCharOffset) {
		this.firstCharOffset = firstCharOffset;
	}
	public int getEqualsSignOffset() {
		return equalsSignOffset;
	}
	public void setEqualsSignOffset(int equalsSignOffset) {
		this.equalsSignOffset = equalsSignOffset;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	@Override
	public String toString() {
		return "SliceAttribute [firstCharOffset=" + firstCharOffset
				+ ", equalsSignOffset=" + equalsSignOffset + ", length="
				+ length + "]";
	}
	
}
