package com.xzymon.xpath_searcher.gui.stain.handlers;

import java.util.LinkedList;
import java.util.List;

public class Slice {
	private int firstByteInStreamPosition = -1;
	private SliceType type = SliceType.WITH_CONTENT_OPENING;
	private int tagNameStartOffset = -1;
	private int tagNameLength = -1;
	private int closingByteOffset = -1;
	private List<SliceAttribute> attributes = null;
	
	public int getFirstByteInStreamPosition() {
		return firstByteInStreamPosition;
	}
	public void setFirstByteInStreamPosition(int firstByteInStreamPosition) {
		this.firstByteInStreamPosition = firstByteInStreamPosition;
	}
	public SliceType getType() {
		return type;
	}
	public void setType(SliceType type) {
		this.type = type;
	}
	public int getTagNameStartOffset() {
		return tagNameStartOffset;
	}
	public void setTagNameStartOffset(int tagNameStartOffset) {
		this.tagNameStartOffset = tagNameStartOffset;
	}
	public int getTagNameLength() {
		return tagNameLength;
	}
	public void setTagNameLength(int tagNameLength) {
		this.tagNameLength = tagNameLength;
	}
	public int getClosingByteOffset() {
		return closingByteOffset;
	}
	public void setClosingByteOffset(int closingByteOffset) {
		this.closingByteOffset = closingByteOffset;
	}
	public List<SliceAttribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<SliceAttribute> attributes) {
		this.attributes = attributes;
	}
	public void addAttribute(SliceAttribute attribute){
		if(attributes==null){
			attributes = new LinkedList<SliceAttribute>();
		}
		attributes.add(attribute);
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if(attributes!=null && !attributes.isEmpty()){
			for(SliceAttribute attr: getAttributes()){
				if(sb.length()!=0){
					sb.append(", ");
				}
				sb.append(attr);
			}
		} else {
			sb.append("null");
		}
		return "Slice [firstByteInStreamPosition=" + firstByteInStreamPosition
				+ ", type=" + type + ", tagNameStartOffset="
				+ tagNameStartOffset + ", tagNameLength=" + tagNameLength
				+ ", closingByteOffset=" + closingByteOffset + ", attributes={"
				+ sb.toString() + "}]";
	}
	
}
