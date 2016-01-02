package com.xzymon.xpath_searcher.core.dom;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class TagLocations {
	private String tagName;
	private TreeSet<Integer> opening, selfClosing, closing, problematic;
	
	public TagLocations(String tagName){
		this.tagName = tagName;
		this.opening = new TreeSet<Integer>();
		this.selfClosing = new TreeSet<Integer>();
		this.closing = new TreeSet<Integer>();
		this.problematic = new TreeSet<Integer>();
	}
	
	public String getTagName() {
		return tagName;
	}

	public void addOpening(Integer locationInList){
		this.opening.add(locationInList);
	}
	
	public void addSelfClosing(Integer locationInList){
		this.selfClosing.add(locationInList);
	}
	
	public void addClosing(Integer locationInList){
		this.closing.add(locationInList);
	}
	
	public void addProblematic(Integer locationInList){
		this.problematic.add(locationInList);
	}
	
	public void removeOpening(Integer locationInList){
		this.opening.remove(locationInList);
	}
	
	public void removeSelfClosing(Integer locationInList){
		this.selfClosing.remove(locationInList);
	}
	
	public void removeClosing(Integer locationInList){
		this.closing.remove(locationInList);
	}
	
	public void removeProblematic(Integer locationInList){
		this.problematic.remove(locationInList);
	}
	
	public TreeSet<Integer> getOpening() {
		return opening;
	}

	public TreeSet<Integer> getSelfClosing() {
		return selfClosing;
	}

	public TreeSet<Integer> getClosing() {
		return closing;
	}

	public TreeSet<Integer> getProblematic() {
		return problematic;
	}

	public List<Integer> getAllAsSortedList(){
		List<Integer> result = new ArrayList<Integer>(this.opening);
		result.addAll(selfClosing);
		result.addAll(closing);
		result.sort(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});
		return result;
	}
}
