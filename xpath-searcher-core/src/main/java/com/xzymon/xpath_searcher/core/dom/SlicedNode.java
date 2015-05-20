package com.xzymon.xpath_searcher.core.dom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzymon.xpath_searcher.core.exception.BuildingNodeStructureException;
import com.xzymon.xpath_searcher.core.exception.IsNotOpeningSliceRepresentationException;
import com.xzymon.xpath_searcher.core.parsing.AttributeRepresentation;
import com.xzymon.xpath_searcher.core.parsing.SliceRepresentation;

public class SlicedNode {
	private static final Logger logger = LoggerFactory.getLogger(SlicedNode.class.getName());
	
	private String name;
	private String uniqueLocation;
	
	private SliceRepresentation openingSlice;
	private SliceRepresentation closingSlice;
	
	private SlicedNode parent;
	private List<SlicedNode> children;
	
	
	private SlicedNode(SliceRepresentation openingSlice, SlicedNode parent, String name) throws IsNotOpeningSliceRepresentationException{
		this.name = name;
		this.parent = parent;
		if(openingSlice!=null && openingSlice.isOpening()){
			this.openingSlice = openingSlice;
		} else {
			throw new IsNotOpeningSliceRepresentationException("for name: "+name);
		}
	}
	
	public static SlicedNode buildStructure(List<SliceRepresentation> slices, char[] chars) throws BuildingNodeStructureException{
		SlicedNode result = null;
		if(slices!=null && slices.size()!=0){
			int sloop = -1;
			SliceRepresentation curSlice = null;
			SlicedNode curNode = null;
			SlicedNode parentNode = null;
			String extractedName = null;
			String previousParentName = null;
			Iterator<SliceRepresentation> slIt = slices.iterator();
			while(slIt.hasNext()){
				curSlice = slIt.next();
				sloop++;
				if(!curSlice.isOther() && !curSlice.isRaw()){
					extractedName = SlicedNode.extractName(chars, curSlice.getNameStartPosition(), curSlice.getNameEndPosition());
					if(curNode==null){
						//to musi być root
						logger.info(String.format("Attempt to create root node from SR at %1$d, with name: \"%2$s\"", sloop, extractedName));
						curNode = new SlicedNode(curSlice, null, extractedName);
						result = curNode;
						parentNode = result;
						logger.info(String.format("Root node created from SR at %1$d, with name: \"%2$s\"", sloop, extractedName));
					} else {
						//musi być zagnieżdżony lub zamykający
						if(curSlice.isClosing()){
							if(extractedName.equals(parentNode.getName())){
								parentNode.setClosingSlice(curSlice);
								previousParentName = parentNode.getName();
								parentNode = parentNode.getParent();
								logger.info(String.format("Closing slice found at %1$d, with name: \"%2$s\"", sloop, extractedName));
								logger.info(String.format("Moving up, to parent node with name: \"%1$s\"", previousParentName));
							} else {
								throw new BuildingNodeStructureException("list position is " + sloop);
							}
							continue;
						}
						if(curSlice.isSelfClosing()){
							curNode = new SlicedNode(curSlice, parentNode, extractedName);
							parentNode.addChild(curNode);
							logger.info(String.format("Self-closing slice found at %1$d, with name: \"%2$s\"", sloop, extractedName));
							continue;
						}
						if(curSlice.isOpening()){
							curNode = new SlicedNode(curSlice, parentNode, extractedName);
							parentNode.addChild(curNode);
							parentNode = curNode;
							logger.info(String.format("New opening slice found at %1$d, with name: \"%2$s\"", sloop, extractedName));
							continue;
						}
					}
				}
			}
		}
		
		return result;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private static String extractName(char[] chars, int startPos, int endPos){
		int length = endPos-startPos+1;
		return new String(chars, startPos, length);
	}
	public String getUniqueLocation() {
		return uniqueLocation;
	}
	public void setUniqueLocation(String uniqueLocation) {
		this.uniqueLocation = uniqueLocation;
	}
	public SliceRepresentation getOpeningSlice() {
		return openingSlice;
	}
	public void setOpeningSlice(SliceRepresentation openingSlice) {
		this.openingSlice = openingSlice;
	}
	public SliceRepresentation getClosingSlice() {
		return closingSlice;
	}
	public void setClosingSlice(SliceRepresentation closingSlice) {
		this.closingSlice = closingSlice;
	}
	public SlicedNode getParent() {
		return parent;
	}
	public void setParent(SlicedNode parent) {
		this.parent = parent;
	}
	public List<SlicedNode> getChildren() {
		return children;
	}
	public void setChildren(List<SlicedNode> children) {
		this.children = children;
	}
	public void addChild(SlicedNode childNode){
		if(childNode!=null){
			if(this.children==null){
				this.children = new ArrayList<SlicedNode>();
			}
			this.children.add(childNode);
		}
	}
	public boolean isRoot(){
		return parent==null;
	}
	public boolean isSelfClosing(){
		boolean result=false;
		if(openingSlice!=null){
			result = openingSlice.isSelfClosing();
		}
		return result;
	}
	public boolean hasChildren(){
		return children!=null?children.size()>0:false;
	}
	
	public SlicedNodeIterator iterator(){
		return new SlicedNodeIterator(this);
	}
	
	public class SlicedNodeIterator implements Iterator<SlicedNode>{
		private SlicedNode nextNode = null;
		private LinkedList<Iterator<SlicedNode>> itStack = new LinkedList<Iterator<SlicedNode>>();
		
		public SlicedNodeIterator(SlicedNode creator) {
			this.nextNode = creator;
		}
		
		public boolean hasNext() {
			return nextNode!=null;
		}

		public SlicedNode next() {
			SlicedNode result = nextNode;
			Iterator<SlicedNode> listIt = null;
			//ustalanie następcy węzła do zwrócenia
			//poszukiwanie wśród węzłów potomnych
			if(nextNode.hasChildren()){
				itStack.add(nextNode.getChildren().iterator());
				listIt = itStack.getLast();
				if(listIt.hasNext()){
					nextNode = listIt.next();
				} else {
					//nieosiągalne
				}
			} else {
				listIt = itStack.getLast();
				if(listIt.hasNext()){
					nextNode = listIt.next();
				} else {
					//wyjdź poziom wyżej
					do {
						itStack.removeLast();
						if(itStack.size()>0){
							listIt = itStack.getLast();
						} else {
							nextNode = null;
							break;
						}
					} while(!listIt.hasNext());
					if(listIt.hasNext()){
						nextNode = listIt.next();
					} else {
						nextNode = null;
					}
				}
			}
			return result;
		}
		
	}
	
	public List<AttributeRepresentation> getAttributes(){
		List<AttributeRepresentation> attrs = null;
		if(openingSlice!=null){
			attrs = openingSlice.getAttributes();
		}
		return attrs;
	}
	
	public boolean hasAttributes(){
		List<AttributeRepresentation> attrs = getAttributes();
		if(attrs!=null && attrs.size()>0){
			return true;
		}
		return false;
	}
}
