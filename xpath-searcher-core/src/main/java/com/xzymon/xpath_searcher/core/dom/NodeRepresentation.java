package com.xzymon.xpath_searcher.core.dom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzymon.xpath_searcher.core.exception.BuildingDOMException;
import com.xzymon.xpath_searcher.core.exception.IsNotOpeningSliceRepresentationException;
import com.xzymon.xpath_searcher.core.parser.AttributeRepresentation;
import com.xzymon.xpath_searcher.core.parser.HalfElementRepresentation;

public class NodeRepresentation {
	private static final Logger logger = LoggerFactory.getLogger(NodeRepresentation.class.getName());
	
	private String name;
	private String uniqueLocation;
	
	private HalfElementRepresentation openingSlice;
	private HalfElementRepresentation closingSlice;
	
	private NodeRepresentation parent;
	private List<NodeRepresentation> children;
	
	
	private NodeRepresentation(HalfElementRepresentation openingSlice, NodeRepresentation parent, String name) throws IsNotOpeningSliceRepresentationException{
		this.name = name;
		this.parent = parent;
		if(openingSlice!=null && openingSlice.isOpening()){
			this.openingSlice = openingSlice;
		} else {
			throw new IsNotOpeningSliceRepresentationException("for name: "+name);
		}
	}
	
	public static NodeRepresentation buildStructure(List<HalfElementRepresentation> slices, char[] chars) throws BuildingDOMException{
		NodeRepresentation result = null;
		if(slices!=null && slices.size()!=0){
			int sloop = -1;
			HalfElementRepresentation curSlice = null;
			NodeRepresentation curNode = null;
			NodeRepresentation parentNode = null;
			String extractedName = null;
			String previousParentName = null;
			Iterator<HalfElementRepresentation> slIt = slices.iterator();
			while(slIt.hasNext()){
				curSlice = slIt.next();
				sloop++;
				if(!curSlice.isOther() && !curSlice.isRaw()){
					extractedName = NodeRepresentation.extractName(chars, curSlice.getNameStartPosition(), curSlice.getNameEndPosition());
					if(curNode==null){
						//to musi być root
						logger.info(String.format("Attempt to create root node from SR at %1$d, with name: \"%2$s\"", sloop, extractedName));
						curNode = new NodeRepresentation(curSlice, null, extractedName);
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
								throw new BuildingDOMException("list position is " + sloop);
							}
							continue;
						}
						if(curSlice.isSelfClosing()){
							curNode = new NodeRepresentation(curSlice, parentNode, extractedName);
							parentNode.addChild(curNode);
							logger.info(String.format("Self-closing slice found at %1$d, with name: \"%2$s\"", sloop, extractedName));
							continue;
						}
						if(curSlice.isOpening()){
							curNode = new NodeRepresentation(curSlice, parentNode, extractedName);
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
	public HalfElementRepresentation getOpeningSlice() {
		return openingSlice;
	}
	public void setOpeningSlice(HalfElementRepresentation openingSlice) {
		this.openingSlice = openingSlice;
	}
	public HalfElementRepresentation getClosingSlice() {
		return closingSlice;
	}
	public void setClosingSlice(HalfElementRepresentation closingSlice) {
		this.closingSlice = closingSlice;
	}
	public NodeRepresentation getParent() {
		return parent;
	}
	public void setParent(NodeRepresentation parent) {
		this.parent = parent;
	}
	public List<NodeRepresentation> getChildren() {
		return children;
	}
	public void setChildren(List<NodeRepresentation> children) {
		this.children = children;
	}
	public void addChild(NodeRepresentation childNode){
		if(childNode!=null){
			if(this.children==null){
				this.children = new ArrayList<NodeRepresentation>();
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
	
	public class SlicedNodeIterator implements Iterator<NodeRepresentation>{
		private NodeRepresentation nextNode = null;
		private LinkedList<Iterator<NodeRepresentation>> itStack = new LinkedList<Iterator<NodeRepresentation>>();
		
		public SlicedNodeIterator(NodeRepresentation creator) {
			this.nextNode = creator;
		}
		
		public boolean hasNext() {
			return nextNode!=null;
		}

		public NodeRepresentation next() {
			NodeRepresentation result = nextNode;
			Iterator<NodeRepresentation> listIt = null;
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
