package com.xzymon.xpath_searcher.core.dom;

import static org.junit.Assert.*;

import org.junit.Test;
import com.xzymon.xpath_searcher.core.dom.CountingSet;

public class CountingSetTest {

	@Test
	public void test() {
		CountingSet<String> cs = new CountingSet<String>();
		String a = "a";
		String b = "b";
		String c = "c";
		assertFalse(cs.hasValue(a));
		assertFalse(cs.take(a));
		cs.put(a);
		assertTrue(cs.hasValue(a));
		assertTrue(cs.take(a));
		assertFalse(cs.hasValue(a));
		assertFalse(cs.take(a));
		cs.put(a);
		cs.put(b);
		cs.put(c);
		assertTrue(cs.hasValue(a));
		assertTrue(cs.hasValue(b));
		assertTrue(cs.hasValue(c));
		cs.put(b);
		cs.put(c);
		cs.put(c);
		assertTrue(cs.hasValue(a));
		assertTrue(cs.hasValue(b));
		assertTrue(cs.hasValue(c));
		assertTrue(cs.take(b));
		assertTrue(cs.hasValue(b));
		assertTrue(cs.take(b));
		assertFalse(cs.hasValue(b));
		assertTrue(cs.take(c));
		assertTrue(cs.hasValue(c));
		assertTrue(cs.take(c));
		assertTrue(cs.hasValue(c));
		assertTrue(cs.take(c));
		assertFalse(cs.hasValue(c));
	}

}
