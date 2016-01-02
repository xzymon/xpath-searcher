package com.xzymon.xpath_searcher.core.dom;

import com.xzymon.xpath_searcher.core.parser.HalfElementRepresentation;

/**
 * Klasa pomocnicza używana przy budowie drzewa {@link DocumentTreeRepresentation}.
 * Umożliwia dodatkowe przechowywanie stanu powiązanego z tagami {@link HalfElementRepresentation}.
 * @author Szymon Ignaciuk
 *
 */
public class TagState {
	private TagTreeBuildingStatus tagTreeBuildingStatus;
	private ParsingParticipationState parsingParticipationState;
	private DocumentNodeRepresentation nodeBindingThisTag;
	
	public TagState(){
		this.tagTreeBuildingStatus = TagTreeBuildingStatus.UNDEFINED;
		this.parsingParticipationState = ParsingParticipationState.OUTER;
		this.nodeBindingThisTag = null;
	}

	public TagTreeBuildingStatus getTagTreeBuildingStatus() {
		return tagTreeBuildingStatus;
	}

	public void setTagTreeBuildingStatus(TagTreeBuildingStatus tagTreeBuildingStatus) {
		this.tagTreeBuildingStatus = tagTreeBuildingStatus;
	}

	public ParsingParticipationState getParsingParticipationState() {
		return parsingParticipationState;
	}

	public void setParsingParticipationState(ParsingParticipationState parsingParticipationState) {
		this.parsingParticipationState = parsingParticipationState;
	}

	public DocumentNodeRepresentation getNodeBindingThisTag() {
		return nodeBindingThisTag;
	}

	public void setNodeBindingThisTag(DocumentNodeRepresentation nodeBindingThisTag) {
		this.nodeBindingThisTag = nodeBindingThisTag;
	}
	
	public boolean isStatusUndefined(){
		return this.tagTreeBuildingStatus.equals(TagTreeBuildingStatus.UNDEFINED);
	}
	
	public boolean isStatusBuiltIn(){
		return this.tagTreeBuildingStatus.equals(TagTreeBuildingStatus.BUILT_IN);
	}
	
	public boolean isStatusSkipped(){
		return this.tagTreeBuildingStatus.equals(TagTreeBuildingStatus.SKIPPED);
	}
	
	public boolean isStatusFixed(){
		return this.tagTreeBuildingStatus.equals(TagTreeBuildingStatus.FIXED);
	}
	
	public boolean isStatusRemoved(){
		return this.tagTreeBuildingStatus.equals(TagTreeBuildingStatus.REMOVED);
	}
	
	public boolean isStateOuter(){
		return this.parsingParticipationState.equals(ParsingParticipationState.OUTER);
	}
	
	public boolean isStateParent(){
		return this.parsingParticipationState.equals(ParsingParticipationState.PARENT);
	}
	
	public boolean isStateIncluded(){
		return this.parsingParticipationState.equals(ParsingParticipationState.INCLUDED);
	}
	
	public boolean isStateExcluded(){
		return this.parsingParticipationState.equals(ParsingParticipationState.EXCLUDED);
	}
}
