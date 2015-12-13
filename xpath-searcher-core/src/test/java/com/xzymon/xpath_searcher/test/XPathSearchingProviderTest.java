package com.xzymon.xpath_searcher.test;

import static org.junit.Assert.*;

import java.io.InputStream;

import javax.xml.xpath.XPathExpressionException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.xzymon.xpath_searcher.core.XPathSearchingProvider;
import com.xzymon.xpath_searcher.core.XmlPreprocessingMode;

public class XPathSearchingProviderTest {

	private String xmlFile = "/test.xml";
	private String htmlFile = "/test.html";
	
	private XPathSearchingProvider xmlXPSProvider;
	private XPathSearchingProvider htmlXPSProvider;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		InputStream xmlIs = Object.class.getResourceAsStream(xmlFile);
		InputStream htmlIs = Object.class.getResourceAsStream(htmlFile);
		
		xmlXPSProvider = new XPathSearchingProvider(xmlIs, XmlPreprocessingMode.ASSUME_XML, null);
		htmlXPSProvider = new XPathSearchingProvider(htmlIs, XmlPreprocessingMode.ASSUME_HTML, null);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHasExpression() {
		String expression = "//*";
		
		try {
			xmlXPSProvider.newSearch(expression);
			assertTrue(xmlXPSProvider.hasExpression());
		} catch (XPathExpressionException e) {
			fail("XPathExpressionException occured on xml");
			e.printStackTrace();
		} catch (NullPointerException ne){
			ne.printStackTrace();
			fail("NullPointer on xml");
		}
		
		try {
			htmlXPSProvider.newSearch(expression);
			assertTrue(htmlXPSProvider.hasExpression());
		} catch (XPathExpressionException e) {
			fail("XPathExpressionException occured on html");
			e.printStackTrace();
		} catch (NullPointerException ne){
			ne.printStackTrace();
			fail("NullPointer on html");
		}
	}

	@Test
	public void testGetExpression() {
		String expression = "//*";
		
		try {
			xmlXPSProvider.newSearch(expression);
			assertEquals(expression, xmlXPSProvider.getExpression());
		} catch (XPathExpressionException e) {
			fail("XPathExpressionException occured on xml");
			e.printStackTrace();
		} catch (NullPointerException ne){
			ne.printStackTrace();
			fail("NullPointer on xml");
		}
		
		try {
			htmlXPSProvider.newSearch(expression);
			assertEquals(expression, htmlXPSProvider.getExpression());
		} catch (XPathExpressionException e) {
			fail("XPathExpressionException occured on html");
			e.printStackTrace();
		} catch (NullPointerException ne){
			ne.printStackTrace();
			fail("NullPointer on html");
		}
	}

	@Test
	public void testNewSearch() {
		try {
			assertEquals(11, xmlXPSProvider.newSearch("//property"));
		} catch (XPathExpressionException e) {
			fail("XPathExpressionException occured on xml");
			e.printStackTrace();
		} catch (NullPointerException ne){
			ne.printStackTrace();
			fail("NullPointer on xml");
		}
		
		try {
			assertEquals(1, htmlXPSProvider.newSearch("//body"));
		} catch (XPathExpressionException e) {
			fail("XPathExpressionException occured on html");
			e.printStackTrace();
		} catch (NullPointerException ne){
			ne.printStackTrace();
			fail("NullPointer on html");
		}
	}

	@Test
	public void testNextNode() {
		
	}

	@Test
	public void testReset() {
		//fail("Not yet implemented"); // TODO
	}

	@Test
	public void testClear() {
		//fail("Not yet implemented"); // TODO
	}

	@Test
	public void testIsEmpty() {
		//fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetData() {
		//fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetFoundNodeList() {
		//fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetSearchingListeners() {
		//fail("Not yet implemented"); // TODO
	}

	@Test
	public void testAddSearchingListener() {
		//fail("Not yet implemented"); // TODO
	}

	@Test
	public void testRemoveSearchingListener() {
		//fail("Not yet implemented"); // TODO
	}

}
