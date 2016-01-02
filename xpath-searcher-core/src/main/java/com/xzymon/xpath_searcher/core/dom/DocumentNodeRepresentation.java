package com.xzymon.xpath_searcher.core.dom;

import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzymon.xpath_searcher.core.exception.IsNamedTagException;
import com.xzymon.xpath_searcher.core.exception.IsNotClosingTagException;
import com.xzymon.xpath_searcher.core.exception.IsNotOpeningTagException;
import com.xzymon.xpath_searcher.core.parser.AttributeRepresentation;
import com.xzymon.xpath_searcher.core.parser.HalfElementRepresentation;

public class DocumentNodeRepresentation implements Comparable<DocumentNodeRepresentation>{
	private static final Logger logger = LoggerFactory.getLogger(NodeRepresentation.class.getName());

	private SourceOfCreation creationSource;
	private ClosingFixType fixType;
	
	private Integer firstTagListId;
	private Integer lastTagListId;
	private String name;
	private String uniqueLocation;

	private HalfElementRepresentation openingTag;
	private HalfElementRepresentation closingTag;
	private HalfElementRepresentation rawTag;

	private DocumentNodeRepresentation parent;
	private NavigableSet<DocumentNodeRepresentation> children;

	private DocumentNodeRepresentation(HalfElementRepresentation opening, HalfElementRepresentation closing,
			HalfElementRepresentation raw, DocumentNodeRepresentation parent, String name, Integer firstId, Integer lastId)
					throws IsNotOpeningTagException, IsNotClosingTagException, IsNamedTagException {
		this.fixType = ClosingFixType.NONE;
		this.firstTagListId = firstId;
		this.lastTagListId = lastId;
		this.name = name;
		this.parent = parent;
		if (opening != null) {
			if (opening.isOpening()) {
				this.creationSource = SourceOfCreation.OPENING_TAG;
				this.openingTag = opening;
				if(this.name==null){
					throw new NullPointerException("DocumentNodeRepresentation with OPENING tag as source of creation has to provide name!");
				}
			} else {
				throw new IsNotOpeningTagException("for firstId=" + firstId);
			}
		}
		if(closing !=null){
			if(closing.isClosing()){
				this.creationSource = SourceOfCreation.CLOSING_TAG;
				this.closingTag = closing;
				if(this.name==null){
					throw new NullPointerException("DocumentNodeRepresentation with CLOSING tag as source of creation has to provide name!");
				}
			} else {
				throw new IsNotClosingTagException("for lastId=" + lastId);
			}
		}
		if(raw !=null){
			if((raw.isRaw() || raw.isOther()) && this.name==null){
				this.creationSource = SourceOfCreation.NAMELESS_TAG;
				this.rawTag = raw;
			} else {
				throw new IsNamedTagException("for firstId=" + firstId);
			}
		}
	}
	
	public static DocumentNodeRepresentation createOpeningNode(HalfElementRepresentation openingNode, 
			DocumentNodeRepresentation parent, String name, int firstId){
		DocumentNodeRepresentation newNode = null;
		try {
			newNode = new DocumentNodeRepresentation(openingNode, null, null, parent, name, firstId, null);
		} catch (IsNotOpeningTagException e) {
			e.printStackTrace();
		} catch (IsNotClosingTagException e) {
			e.printStackTrace();
		} catch (IsNamedTagException e) {
			e.printStackTrace();
		}
		return newNode;
	}
	
	public static DocumentNodeRepresentation createClosingNode(HalfElementRepresentation closingTag, 
			DocumentNodeRepresentation parent, String name, int lastId){
		DocumentNodeRepresentation newNode = null;
		try {
			newNode = new DocumentNodeRepresentation(null, closingTag, null, parent, name, null, lastId);
		} catch (IsNotOpeningTagException e) {
			e.printStackTrace();
		} catch (IsNotClosingTagException e) {
			e.printStackTrace();
		} catch (IsNamedTagException e) {
			e.printStackTrace();
		}
		return newNode;
	}
	
	public static DocumentNodeRepresentation createNamelessNode(HalfElementRepresentation namelessNode, 
			DocumentNodeRepresentation parent, String name, int firstId){
		DocumentNodeRepresentation newNode = null;
		try {
			newNode = new DocumentNodeRepresentation(null, null, namelessNode, parent, name, firstId, null);
		} catch (IsNotOpeningTagException e) {
			e.printStackTrace();
		} catch (IsNotClosingTagException e) {
			e.printStackTrace();
		} catch (IsNamedTagException e) {
			e.printStackTrace();
		}
		return newNode;
	}
	
	public Integer getOpeningTagListId() {
		if(this.openingTag!=null){
			return firstTagListId;
		}
		return null;
	}

	public Integer getClosingTagListId() {
		return lastTagListId;
	}

	public Integer getNamelessTagListId(){
		if(this.rawTag!=null){
			return firstTagListId;
		}
		return null;
	}
	
	public Integer getCreationTagListId(){
		if(creationSource.equals(SourceOfCreation.CLOSING_TAG)){
			return lastTagListId;
		}
		return firstTagListId;
	}
	
	public String getName() {
		return name;
	}

	public String getUniqueLocation() {
		return uniqueLocation;
	}

	public void setUniqueLocation(String uniqueLocation) {
		this.uniqueLocation = uniqueLocation;
	}

	public HalfElementRepresentation getOpeningTag() {
		return openingTag;
	}

	public boolean canSetOpeningTag(){
		if(rawTag==null && openingTag==null && fixType.equals(ClosingFixType.NONE)){
			return true;
		}
		return false;
	}
	
	public boolean setOpeningTag(HalfElementRepresentation openingTag, int listId) {
		if(canSetOpeningTag()){
			this.openingTag = openingTag;
			this.firstTagListId = listId;
			return true;
		}
		return false;
	}

	public HalfElementRepresentation getClosingTag() {
		return closingTag;
	}

	public boolean canSetClosingTag(){
		if(rawTag==null && closingTag==null && fixType.equals(ClosingFixType.NONE)){
			return true;
		}
		return false;
	}
	
	public boolean setClosingTag(HalfElementRepresentation closingTag, int listId) {
		if(canSetClosingTag()){
			this.closingTag = closingTag;
			this.lastTagListId = listId;
			return true;
		}
		return false;
	}
	
	public boolean isFixedByOpeningTag(){
		return fixType.equals(ClosingFixType.OPENING_TAG);
	}
	
	public boolean isFixedByClosingTag(){
		return fixType.equals(ClosingFixType.CLOSING_TAG);
	}
	
	public boolean isFixedBySelfClosing(){
		return fixType.equals(ClosingFixType.SELF);
	}
	
	public boolean isFixed(){
		return !fixType.equals(ClosingFixType.NONE);
	}
	
	public void setFixType(ClosingFixType fix){
		this.fixType = fix;
	}
	
	public void fixByOpeningAtClosingTagId(){
		this.fixType = ClosingFixType.OPENING_TAG;
		this.firstTagListId = this.lastTagListId;
	}
	
	public void fixByOpeningAtTagId(int tagId){
		this.fixType = ClosingFixType.OPENING_TAG;
		this.firstTagListId = tagId;
	}
	
	public void fixByClosingAtTagId(int tagId){
		this.fixType = ClosingFixType.CLOSING_TAG;
		this.lastTagListId = tagId;
	}
	
	public void fixBySelfClosing(){
		this.fixType = ClosingFixType.SELF;
		//this.firstTagListId = this.firstTagListId;
	}

	public HalfElementRepresentation getRawTag() {
		return rawTag;
	}

	public DocumentNodeRepresentation getParent() {
		return parent;
	}

	public void setParent(DocumentNodeRepresentation parent) {
		this.parent = parent;
	}

	public boolean hasParent() {
		return parent == null;
	}

	public boolean isSelfClosing() {
		if (openingTag != null) {
			return openingTag.isSelfClosing() || fixType.equals(ClosingFixType.SELF);
		}
		return false;
	}
	
	/**
	 * Umożliwia testowanie czy jest to prawidłowy węzeł składający się z tagów.
	 * Prawidłowy węzeł składający się z tagów to węzeł albo posiadający tag
	 * otwierający i zamykający, albo posiadający tag otwierający samozamykający się.
	 * <p>
	 * Ponadto węzeł jest prawidłowy jeżeli co prawda nie spełnia powyższych
	 * kryteriów, ale ustawiono w nim odpowiedni sposób naprawy nieprawidłowości
	 * przez ustawienie metodą {@link #setFixType(ClosingFixType)} odpowiedniej
	 * wartości {@link ClosingFixType}:
	 * <ul>
	 * <li>jeżeli węzeł składa się tylko z tagu otwierającego który nie jest
	 * tagiem samozamykającym się, to sytuację można naprawić przez ustawienie
	 * {@link ClosingFixType#SELF} lub {@link ClosingFixType#CLOSING_TAG},
	 * </li>
	 * <li>jeżeli węzeł składa się tylko z tagu zamykającego to sytuację można
	 * naprawić przez ustawienie {@link ClosingFixType#OPENING_TAG}.</li>
	 * </ul>
	 * </p>
	 * @return true, jeśli: 
	 * <ul>
	 * <li>{@link #getOpeningTag()}!=null && {@link #getClosingTag()}!=null</li>
	 * <li>{@link #isSelfClosing()}==true.</li>
	 * </ul>
	 * W pozostałych przypadkach false.
	 */
	public boolean isClosed(){
		if(openingTag!=null && (closingTag!=null || fixType.equals(ClosingFixType.CLOSING_TAG))){
			return true;
		}
		if(closingTag!=null && fixType.equals(ClosingFixType.OPENING_TAG)){
			return true;
		}
		return isSelfClosing();
	}
	
	public boolean isRaw(){
		return this.creationSource.equals(SourceOfCreation.NAMELESS_TAG);
	}
	
	public SourceOfCreation getSourceOfCreation(){
		return this.creationSource;
	}

	public NavigableSet<DocumentNodeRepresentation> getChildren() {
		return children;
	}

	public boolean hasChildren() {
		return children != null ? children.size() > 0 : false;
	}

	public void addChild(DocumentNodeRepresentation childNode) {
		if(!isRaw()){
			if (childNode != null) {
				if (this.children == null) {
					this.children = new TreeSet<DocumentNodeRepresentation>();
				}
				this.children.add(childNode);
			}
		}
	}
	
	public boolean removeChild(DocumentNodeRepresentation childNode){
		boolean result = false;
		if(childNode != null){
			if(this.children != null){
				result = this.children.remove(childNode);
			}
		}
		return result;
	}

	public List<AttributeRepresentation> getAttributes() {
		List<AttributeRepresentation> attrs = null;
		if (openingTag != null) {
			attrs = openingTag.getAttributes();
		}
		return attrs;
	}

	public boolean hasAttributes() {
		List<AttributeRepresentation> attrs = getAttributes();
		if (attrs != null && attrs.size() > 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public int compareTo(DocumentNodeRepresentation that) {
		return this.getCreationTagListId().compareTo(that.getCreationTagListId());
	}

	public DocumentNodeRepresentation findLastDescendant(){
		DocumentNodeRepresentation lastChild=null;
		DocumentNodeRepresentation curParent=this;
		while(curParent.hasChildren()){
			lastChild = curParent.getChildren().last();
			curParent=lastChild;
		}
		return lastChild;
	}
	
	public StringBuffer stringifyAsStringBuffer(char[] chars){
		StringBuffer sb = null;
		StringBuffer modSb = null;
		int insertPos;
		
		if(isRaw()){
			return rawTag.stringifyUsingMarkupLanguage(chars);
		}
		sb = new StringBuffer();
		if(isClosed()){
			if(this.openingTag!=null){
				if(this.openingTag.isSelfClosing()){
					logger.info("węzeł ma tag otwierający samozamykający się");
					return sb.append(openingTag.stringifyUsingMarkupLanguage(chars));
				}
				if(this.isFixedBySelfClosing()){
					logger.info("węzeł został naprawiony przez zastosowanie strategii SELF");
					modSb = openingTag.stringifyUsingMarkupLanguage(chars);
					insertPos = modSb.length()-1;
					modSb.insert(insertPos, '/');
					return modSb;
				}
				logger.info("węzeł ma zwykły tag otwierający");
				sb.append(openingTag.stringifyUsingMarkupLanguage(chars));
			} else {
				logger.info("węzeł został naprawiony przez zastosowanie strategii OPENING_TAG");
				sb.append('<').append(this.name).append('>');
			}
			if(this.children!=null){
				DocumentNodeRepresentation dnr;
				int processedChildren = 0;
				Iterator<DocumentNodeRepresentation> itDnr = this.children.iterator();
				while(itDnr.hasNext()){
					dnr = itDnr.next();
					sb.append(dnr.stringifyAsStringBuffer(chars));
					processedChildren++;
				}
				logger.info("w postaci tekstowej zawarto " + processedChildren + " dzieci");
			} else {
				logger.info("węzeł nie ma żadnych dzieci");
			}
			//niezależnie czy tag zamykający istnieje czy też węzeł został
			//naprawiony przez CLOSING_TAG
			sb.append('<').append('/').append(this.name).append('>');
		} else {
			if(!isFixed()){
				sb.append("{").append(this.name).append(" - węzeł wymaga naprawy}");
			} else {
				sb.append("{").append(this.name).append(" - problem z węzłem - isFixed()==true, a dalej węzeł nie jest zamknięty!}");
			}
		}
		
		return sb;
	}
}
