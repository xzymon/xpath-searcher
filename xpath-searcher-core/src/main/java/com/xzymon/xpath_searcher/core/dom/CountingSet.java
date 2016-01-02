package com.xzymon.xpath_searcher.core.dom;

import java.util.HashMap;
import java.util.Map;

public class CountingSet<T> {
	private Map<T, Integer> map;
	
	public CountingSet(){
		map = new HashMap<T, Integer>();
	}
	
	public void put(T key){
		if(map.containsKey(key)){
			Integer value = map.get(key);
			map.put(key, ++value);
			System.out.println("CounterSet: after put[" + key +"] value=" + value);
		} else {
			map.put(key, new Integer(1));
			System.out.println("CounterSet: after put[" + key +"] value=1");
		}
	}
	
	public boolean take(T key){
		Integer value = map.get(key);
		if(value!=null){
			if(value>0){
				map.put(key, --value);
				System.out.println("CounterSet: before put[" + key +"] value=" + value);
				return true;
			}
		}
		System.out.println("no value");
		return false;
	}
	
	public boolean hasValue(T key){
		Integer value = map.get(key);
		if(value!=null){
			System.out.println("CounterSet: test if hasValue [" + key +"] value=" + value);
			if(value>0){
				return true;
			}
		}
		return false;
	}
	
	public void clear(){
		map = new HashMap<T, Integer>();
	}
}
