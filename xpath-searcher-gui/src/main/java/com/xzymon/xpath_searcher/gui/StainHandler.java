package com.xzymon.xpath_searcher.gui;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class StainHandler extends DefaultHandler {
	private Logger logger = LoggerFactory.getLogger(StainHandler.class.getName());

	@Override
	public InputSource resolveEntity(String publicId, String systemId)
			throws IOException, SAXException {
		logger.info(String.format("Method: %1$s, publicId=%2$s, systemId=%3$s", "resolveEntity", publicId, systemId));
		return super.resolveEntity(publicId, systemId);
	}

	@Override
	public void notationDecl(String name, String publicId, String systemId)
			throws SAXException {
		logger.info(String.format("Method: %1$s, name=%2$s, publicId=%3$s, systemId=%4$s", "notationDecl", name, publicId, systemId));
		super.notationDecl(name, publicId, systemId);
	}

	@Override
	public void unparsedEntityDecl(String name, String publicId,
			String systemId, String notationName) throws SAXException {
		logger.info(String.format("Method: %1$s, name=%2$s, publicId=%3$s, systemId=%4$s, notationName=%5$s", "unparsedEntityDecl", name, publicId, systemId, notationName));
		super.unparsedEntityDecl(name, publicId, systemId, notationName);
	}

	@Override
	public void setDocumentLocator(Locator locator) {
		logger.info(String.format("Method: %1$s, locator=%2$s", locator));
		super.setDocumentLocator(locator);
	}

	@Override
	public void startDocument() throws SAXException {
		logger.info(String.format("Method: %1$s, ", "startDocument"));
		super.startDocument();
	}

	@Override
	public void endDocument() throws SAXException {
		logger.info(String.format("Method: %1$s, ", "endDocument"));
		super.endDocument();
	}

	@Override
	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		logger.info(String.format("Method: %1$s, prefix=%2$s, uri=%3$s", "startPrefixMapping", prefix, uri));
		super.startPrefixMapping(prefix, uri);
	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		logger.info(String.format("Method: %1$s, prefix=%2$s", "endPrefixMapping", prefix));
		super.endPrefixMapping(prefix);
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		logger.info(String.format("Method: %1$s, "));
		super.startElement(uri, localName, qName, attributes);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		logger.info(String.format("Method: %1$s, "));
		super.endElement(uri, localName, qName);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		logger.info(String.format("Method: %1$s, "));
		super.characters(ch, start, length);
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		logger.info(String.format("Method: %1$s, "));
		super.ignorableWhitespace(ch, start, length);
	}

	@Override
	public void processingInstruction(String target, String data)
			throws SAXException {
		logger.info(String.format("Method: %1$s, "));
		super.processingInstruction(target, data);
	}

	@Override
	public void skippedEntity(String name) throws SAXException {
		logger.info(String.format("Method: %1$s, "));
		super.skippedEntity(name);
	}

	@Override
	public void warning(SAXParseException e) throws SAXException {
		logger.info(String.format("Method: %1$s, "));
		super.warning(e);
	}

	@Override
	public void error(SAXParseException e) throws SAXException {
		logger.info(String.format("Method: %1$s, "));
		super.error(e);
	}

	@Override
	public void fatalError(SAXParseException e) throws SAXException {
		logger.info(String.format("Method: %1$s, "));
		super.fatalError(e);
	}
	
}
