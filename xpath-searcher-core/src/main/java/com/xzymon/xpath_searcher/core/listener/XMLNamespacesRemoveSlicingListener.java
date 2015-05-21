package com.xzymon.xpath_searcher.core.listener;

public class XMLNamespacesRemoveSlicingListener implements SlicingListener {
	private StringBuilder collector;
	private char[] data;
	
	private String attrName = null;
	private int nextEqualsSignToIgnore = -1;
	private int nextAttributeValueToIgnore = -1;
	
	public XMLNamespacesRemoveSlicingListener(){
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
		attrName = new String(data, startPos, lastPos-startPos+1);
		if(attrName.startsWith("xmlns")){
			nextEqualsSignToIgnore = lastPos+1;
			nextAttributeValueToIgnore = lastPos+2;
		} else {
			collector.append(attrName);
		}
	}

	public void attributeEqualsSign(int position) {
		if(position!=nextEqualsSignToIgnore){
			collector.append(data[position]);
		}
	}

	public void attributeValue(int startPos, int lastPos) {
		if(startPos!=nextAttributeValueToIgnore){
			collector.append(data, startPos, lastPos-startPos+1);
		}
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
