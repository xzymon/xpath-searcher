package com.xzymon.xpath_searcher.core.converter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.xzymon.xpath_searcher.core.exception.ParserException;
import com.xzymon.xpath_searcher.core.listener.XMLNamespacesRemoveParserListener;
import com.xzymon.xpath_searcher.core.parser.HalfElementsParser;

public class XMLStreamConverter implements XPathSearchableStream  {
	private HalfElementsParser parser;
	private byte[] resultStream;
	
	public XMLStreamConverter(InputStream is) throws IOException, ParserException{
		int avail = is.available();
		if(avail>0){
			parser = new HalfElementsParser(is);
			XMLNamespacesRemoveParserListener removeListener = new XMLNamespacesRemoveParserListener();
			removeListener.setData(parser.getSavedChars());
			parser.addParserListener(removeListener);
			parser.invokeListeners();
			String filtered = removeListener.getCollectedString();
			resultStream = filtered.getBytes();
		}
	}
	
	public InputStream getConvertedStream(){
		return new ByteArrayInputStream(resultStream);
	}
}
