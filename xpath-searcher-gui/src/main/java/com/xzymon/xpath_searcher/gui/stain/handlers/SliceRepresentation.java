package com.xzymon.xpath_searcher.gui.stain.handlers;

import java.util.LinkedList;
import java.util.List;

public class SliceRepresentation {
	private int startsAt = -1;
	private int endsAt = -1;
	private int nameStartsAt = -1;
	private int nameEndsAt = -1;
	private int closingSlashAt = -1;
	private boolean other = false;
	private boolean raw = false;
	private List<AttributeRepresentation> attributes = null;
	private List<ErrorRepresentation> errors = null;
	private List<SliceInterior> interiors = null;
	
	public int getStartPosition() {
		return startsAt;
	}
	public void setStartPosition(int pos) {
		this.startsAt = pos;
	}
	public int getEndPosition() {
		return endsAt;
	}
	public void setEndPosition(int pos) {
		this.endsAt = pos;
	}
	public int getNameStartPosition() {
		return nameStartsAt;
	}
	public void setNameStartPosition(int pos) {
		this.nameStartsAt = pos;
	}
	public int getNameEndPosition() {
		return nameEndsAt;
	}
	public void setNameEndPosition(int pos) {
		this.nameEndsAt = pos;
	}
	public int getClosingSlashPosition(){
		return closingSlashAt;
	}
	public void setClosingSlashPosition(int pos){
		this.closingSlashAt = pos;
	}
	
	public boolean isClosing(){
		return (closingSlashAt==startsAt+1)?true:false;
	}
	public boolean isSelfClosing(){
		return (closingSlashAt==endsAt-1)?true:false;
	}
	public boolean isShutDown(){
		return endsAt!=-1;
	}
	public boolean isOther(){
		return other;
	}
	
	public void setOther(boolean other){
		this.other = other;
	}
	
	public boolean isRaw() {
		return raw;
	}
	public void setRaw(boolean raw) {
		this.raw = raw;
	}
	
	public boolean isOpening(){
		return !raw && !other && !isClosing();
	}
	
	public List<AttributeRepresentation> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<AttributeRepresentation> attributes) {
		this.attributes = attributes;
	}
	public void addAttribute(AttributeRepresentation attribute){
		if(attributes==null){
			attributes = new LinkedList<AttributeRepresentation>();
		}
		attributes.add(attribute);
	}
	
	public List<ErrorRepresentation> getErrors() {
		return errors;
	}
	public void setErrors(List<ErrorRepresentation> errors) {
		this.errors = errors;
	}
	public void addError(ErrorRepresentation error){
		if(errors==null){
			errors = new LinkedList<ErrorRepresentation>();
		}
		errors.add(error);
	}
	
	public List<SliceInterior> getInterior() {
		return interiors;
	}
	public void setInterior(List<SliceInterior> interiors) {
		this.interiors = interiors;
	}
	public void addInterior(SliceInterior interior){
		if(interiors==null){
			interiors = new LinkedList<SliceInterior>();
		}
		interiors.add(interior);
	}
	
	@Override
	public String toString() {
		StringBuffer attrSb = new StringBuffer();
		if(attributes!=null && !attributes.isEmpty()){
			for(AttributeRepresentation attr: getAttributes()){
				if(attrSb.length()!=0){
					attrSb.append(", ");
				}
				attrSb.append(attr);
			}
		} else {
			attrSb.append("null");
		}
		StringBuffer errSb = new StringBuffer();
		if(errors!=null && !errors.isEmpty()){
			for(ErrorRepresentation error: errors){
				if(errSb.length()!=0){
					errSb.append(", ");
				}
				errSb.append(error);
			}
		} else {
			errSb.append("null");
		}
		StringBuffer intSb = new StringBuffer();
		if(interiors!=null && !interiors.isEmpty()){
			for(SliceInterior interior: interiors){
				if(intSb.length()!=0){
					intSb.append(", ");
				}
				if(interior instanceof AttributeRepresentation){
					intSb.append("A");
				}
				if(interior instanceof ErrorRepresentation){
					intSb.append("E");
				}
			}
		} else {
			intSb.append("null");
		}
		return "SliceRepresentation [startsAt=" + startsAt + ", endsAt="
				+ endsAt + ", nameStartsAt=" + nameStartsAt + ", nameEndsAt="
				+ nameEndsAt + ", closingSlashAt=" + closingSlashAt
				+ ", closing=" + isClosing() + ", selfClosing=" + isSelfClosing()
				+ ", raw=" + raw + ", attributes={" + attributes 
				+ "}, errors={" + errSb.toString() + "}, interiors={"
				+ intSb.toString() + "}]";
	}
	
	
}
