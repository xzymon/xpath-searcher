package com.xzymon.xpath_searcher.core.dom;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzymon.xpath_searcher.core.exception.ParserException;
import com.xzymon.xpath_searcher.core.listener.LoggingParserListener;
import com.xzymon.xpath_searcher.core.parser.HalfElementRepresentation;
import com.xzymon.xpath_searcher.core.parser.HalfElementsParser;

public class HalfElementRepresentationStringifyTest {
	private static Logger logger = LoggerFactory.getLogger(HalfElementRepresentationStringifyTest.class);
	
	private HalfElementsParser heParser;
	private List<HalfElementRepresentation> halfElements;
	private HalfElementRepresentation tag;
	
	private LoggingParserListener loggingListener = new LoggingParserListener();
	
	private String input;
	private String expected;
	private String actual;
	private byte[] savedStream;
	private char[] savedChars;
	
	@Test
	public void rawTag(){
		input = "";
		expected = "";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			heParser.invokeListeners();
			halfElements = heParser.getHalfElementsList();
			assertEquals(0, halfElements.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
		input = " ";
		expected = " ";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			heParser.invokeListeners();
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
		input = "a";
		expected = "a";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			heParser.invokeListeners();
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "     ";
		expected = "     ";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			heParser.invokeListeners();
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "aaaaa";
		expected = "aaaaa";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			heParser.invokeListeners();
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void otherTag(){
		input = "<!>";
		expected = "<!>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<!DOCTYPE>";
		expected = "<!DOCTYPE>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<!DOCTYPE html>";
		expected = "<!DOCTYPE html>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<!---->";
		expected = "<!---->";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<!-- sth -->";
		expected = "<!-- sth -->";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<?xml ?>";
		expected = "<?xml ?>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
	}
	
	@Test
	public void namedOpeningNoAttributesTag(){
		input = "<a>";
		expected = "<a>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<a >";
		expected = "<a>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa>";
		expected = "<aaa>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa >";
		expected = "<aaa>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void namedOpeningOneAttributeTag(){
		input = "<aaa b=\"\">";
		expected = "<aaa b=\"\">";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa bbbb=\"\">";
		expected = "<aaa bbbb=\"\">";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa b=\"c\">";
		expected = "<aaa b=\"c\">";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa bbbb=\"c\">";
		expected = "<aaa bbbb=\"c\">";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa b=\"ccc\">";
		expected = "<aaa b=\"ccc\">";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa bbbb=\"ccc\">";
		expected = "<aaa bbbb=\"ccc\">";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void namedOpeningManyAttributesTag(){
		input = "<aaa b=\"\" d=\"\">";
		expected = "<aaa b=\"\" d=\"\">";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa b=\"c\" d=\"\">";
		expected = "<aaa b=\"c\" d=\"\">";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa b=\"\" d=\"e\">";
		expected = "<aaa b=\"\" d=\"e\">";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa b=\"c\" d=\"e\">";
		expected = "<aaa b=\"c\" d=\"e\">";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa b=\"ccc\" d=\"eee\">";
		expected = "<aaa b=\"ccc\" d=\"eee\">";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa b=\"ccc\" d=\"eee\" f=\"\">";
		expected = "<aaa b=\"ccc\" d=\"eee\" f=\"\">";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa b=\"ccc\" d=\"eee\" f=\"ggg\">";
		expected = "<aaa b=\"ccc\" d=\"eee\" f=\"ggg\">";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void namedSelfClosingNoAttributesTag(){
		input = "<a/>";
		expected = "<a/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			logger.info("tag is " + tag);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<a />";
		expected = "<a/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa/>";
		expected = "<aaa/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa />";
		expected = "<aaa/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void namedSelfClosingOneAttributeTag(){
		input = "<aaa b=\"\"/>";
		expected = "<aaa b=\"\"/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa b=\"\" />";
		expected = "<aaa b=\"\"/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa bbbb=\"\"/>";
		expected = "<aaa bbbb=\"\"/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa bbbb=\"\" />";
		expected = "<aaa bbbb=\"\"/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa b=\"c\"/>";
		expected = "<aaa b=\"c\"/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa b=\"c\" />";
		expected = "<aaa b=\"c\"/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa bbbb=\"c\"/>";
		expected = "<aaa bbbb=\"c\"/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa b=\"ccc\"/>";
		expected = "<aaa b=\"ccc\"/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa b=\"ccc\" />";
		expected = "<aaa b=\"ccc\"/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa bbbb=\"ccc\"/>";
		expected = "<aaa bbbb=\"ccc\"/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa bbbb=\"ccc\" />";
		expected = "<aaa bbbb=\"ccc\"/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void namedSelfClosingManyAttributesTag(){
		input = "<aaa b=\"\" d=\"\"/>";
		expected = "<aaa b=\"\" d=\"\"/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa b=\"\" d=\"\" />";
		expected = "<aaa b=\"\" d=\"\"/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa b=\"c\" d=\"\"/>";
		expected = "<aaa b=\"c\" d=\"\"/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa b=\"c\" d=\"\" />";
		expected = "<aaa b=\"c\" d=\"\"/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa b=\"\" d=\"e\"/>";
		expected = "<aaa b=\"\" d=\"e\"/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa b=\"\" d=\"e\" />";
		expected = "<aaa b=\"\" d=\"e\"/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa b=\"c\" d=\"e\"/>";
		expected = "<aaa b=\"c\" d=\"e\"/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa b=\"c\" d=\"e\" />";
		expected = "<aaa b=\"c\" d=\"e\"/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa b=\"ccc\" d=\"eee\"/>";
		expected = "<aaa b=\"ccc\" d=\"eee\"/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa b=\"ccc\" d=\"eee\" />";
		expected = "<aaa b=\"ccc\" d=\"eee\"/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa b=\"ccc\" d=\"eee\" f=\"\"/>";
		expected = "<aaa b=\"ccc\" d=\"eee\" f=\"\"/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa b=\"ccc\" d=\"eee\" f=\"\" />";
		expected = "<aaa b=\"ccc\" d=\"eee\" f=\"\"/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa b=\"ccc\" d=\"eee\" f=\"ggg\"/>";
		expected = "<aaa b=\"ccc\" d=\"eee\" f=\"ggg\"/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "<aaa b=\"ccc\" d=\"eee\" f=\"ggg\" />";
		expected = "<aaa b=\"ccc\" d=\"eee\" f=\"ggg\"/>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void namedClosingNoAttributesTag(){
		input = "</a>";
		expected = "</a>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "</a >";
		expected = "</a>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "</aaa>";
		expected = "</aaa>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
		
		input = "</aaa >";
		expected = "</aaa>";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	private void hibernatedCode(){
		input = "";
		expected = "";
		
		savedStream = input.getBytes();
		try {
			heParser = new HalfElementsParser(savedStream);
			savedChars = heParser.getSavedChars();
			heParser.addParserListener(loggingListener);
			halfElements = heParser.getHalfElementsList();
			assertEquals(1, halfElements.size());
			tag = halfElements.get(0);
			actual = tag.stringifyUsingMarkupLanguage(savedChars).toString();
			logger.info("expected is [" + expected + "]");
			logger.info("actual is [" + actual + "]");
			assertEquals(expected, actual);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
	}
}
