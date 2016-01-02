package com.xzymon.xpath_searcher.core.listener;

public class OtherTagRemoveParserListener implements RemoveParserListener {
	private StringBuilder collector;
	private char[] data;
	
	public OtherTagRemoveParserListener(){
		collector = new StringBuilder(4096);
	}
	
	public void setData(char[] data){
		this.data = data;
	}
	
	public String getCollectedString(){
		return collector.toString();
	}
	
	public void otherTag(int startPos, int lastPos) {
		//ignore
	}

	public void lessThanStartChar(int position) {
		collector.append(data[position]);
	}

	public void tagName(int startPos, int lastPos) {
		collector.append(data, startPos, lastPos-startPos+1);
	}

	public void tagGap(int startPos, int lastPos) {
		collector.append(data, startPos, lastPos-startPos+1);
	}

	public void attributeName(int startPos, int lastPos) {
		collector.append(data, startPos, lastPos-startPos+1);
	}

	public void attributeEqualsSign(int position) {
		collector.append(data[position]);
	}

	public void attributeValue(int startPos, int lastPos) {
		collector.append(data, startPos, lastPos-startPos+1);
	}

	public void greaterThanEndingChar(int position) {
		collector.append(data[position]);
	}

	public void error(int startPos, int lastPos) {
		collector.append(data, startPos, lastPos-startPos+1);
	}

	public void closingSlash(int position) {
		collector.append(data[position]);
	}

	public void rawText(int startPos, int lastPos) {
		collector.append(data, startPos, lastPos-startPos+1);
	}

}
