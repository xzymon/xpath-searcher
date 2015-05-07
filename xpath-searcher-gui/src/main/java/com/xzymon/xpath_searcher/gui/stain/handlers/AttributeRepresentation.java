package com.xzymon.xpath_searcher.gui.stain.handlers;

public class AttributeRepresentation implements SliceInterior {
	
	private SliceRepresentation owner;
	private int startsAt = -1;
	private int nameEndsAt = -1;
	private int equalsSignAt = -1;
	private int startQuotationMarkAt = -1;
	private int endQuotationMarkAt = -1;
	private boolean hasValue = false;
	private boolean singleQuoted = false;
	private boolean doubleQuoted = false;
	
	public boolean hasOwner(){
		return owner!=null;
	}
	public int getOwnerPosition() {
		if(hasOwner()){
			return owner.getStartPosition();
		}
		return -1;
	}
	public void setOwner(SliceRepresentation slice) {
		this.owner = slice;
	}
	public int getStartsAt() {
		return startsAt;
	}
	public void setStartsAt(int startsAt) {
		this.startsAt = startsAt;
	}
	public int getNameEndsAt() {
		return nameEndsAt;
	}
	public void setNameEndsAt(int nameEndsAt) {
		this.nameEndsAt = nameEndsAt;
	}
	public int getEqualsSignAt() {
		return equalsSignAt;
	}
	public void setEqualsSignAt(int equalsSignAt) {
		this.equalsSignAt = equalsSignAt;
	}
	public int getStartQuotationMarkAt() {
		return startQuotationMarkAt;
	}
	public void setStartQuotationMarkAt(int startQuotationMarkAt) {
		this.startQuotationMarkAt = startQuotationMarkAt;
	}
	public int getEndQuotationMarkAt() {
		return endQuotationMarkAt;
	}
	public void setEndQuotationMarkAt(int endQuotationMarkAt) {
		this.endQuotationMarkAt = endQuotationMarkAt;
	}
	public boolean hasValue() {
		return (startQuotationMarkAt > 0 && startQuotationMarkAt < endQuotationMarkAt);
	}
	public boolean isSingleQuoted() {
		return singleQuoted;
	}
	public void setSingleQuoted(boolean singleQuoted) {
		this.singleQuoted = singleQuoted;
	}
	public boolean isDoubleQuoted() {
		return doubleQuoted;
	}
	public void setDoubleQuoted(boolean doubleQuoted) {
		this.doubleQuoted = doubleQuoted;
	}
	public int parentRelativeOffset() {
		return startsAt - getOwnerPosition();
	}
	public int equalsSignRelativePosition() {
		return equalsSignAt - startsAt;
	}
	public int length() {
		if(hasValue){
			return endQuotationMarkAt - startsAt + 1;
		}
		return nameEndsAt - startsAt + 1;
	}
	public boolean isValid(){
		if(startsAt > -1 && nameEndsAt > -1 && equalsSignAt > -1 && startQuotationMarkAt > -1 && endQuotationMarkAt > -1){
			if(nameEndsAt >= startsAt && equalsSignAt==nameEndsAt+1 && equalsSignAt==startQuotationMarkAt-1 && startQuotationMarkAt<endQuotationMarkAt){
				return true;	//coś jeszcze jest do uwzględnienia tylko nie pamiętam co
			}
		} 
		return false;
	}
}
