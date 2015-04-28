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
		logger.info(String.format("Method: resolveEntity, publicId=%1$s, systemId=%2$s", publicId, systemId));
		return super.resolveEntity(publicId, systemId);
	}

	@Override
	public void notationDecl(String name, String publicId, String systemId)
			throws SAXException {
		logger.info(String.format("Method: notationDecl, name=%1$s, publicId=%2$s, systemId=%3$s", name, publicId, systemId));
		super.notationDecl(name, publicId, systemId);
	}

	@Override
	public void unparsedEntityDecl(String name, String publicId,
			String systemId, String notationName) throws SAXException {
		logger.info(String.format("Method: unparsedEntityDecl, name=%1$s, publicId=%2$s, systemId=%3$s, notationName=%4$s", name, publicId, systemId, notationName));
		super.unparsedEntityDecl(name, publicId, systemId, notationName);
	}

	@Override
	public void setDocumentLocator(Locator locator) {
		logger.info(String.format("Method: setDocumentLocator, locator=%1$s", locator));
		super.setDocumentLocator(locator);
	}

	@Override
	public void startDocument() throws SAXException {
		logger.info(String.format("Method: startDocument"));
		super.startDocument();
	}

	@Override
	public void endDocument() throws SAXException {
		logger.info(String.format("Method: endDocument"));
		super.endDocument();
	}

	@Override
	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		logger.info(String.format("Method: startPrefixMapping, prefix=%1$s, uri=%2$s", prefix, uri));
		super.startPrefixMapping(prefix, uri);
	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		logger.info(String.format("Method: endPrefixMapping, prefix=%1$s", prefix));
		super.endPrefixMapping(prefix);
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		StringBuffer sb = new StringBuffer();
		sb.append("attributes={");
		for(int aloop=0; aloop<attributes.getLength(); aloop++){
			sb.append(" ").append(attributes.getQName(aloop)).append("=").append(attributes.getValue(aloop));
		}
		sb.append("}");
		logger.info(String.format("Method: startElement, uri=%1$s, localName=%2$s, qName=%3$s %4$s", uri, localName, qName, sb.toString()));
		super.startElement(uri, localName, qName, attributes);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		logger.info(String.format("Method: endElement, uri=%1$s, localName=%2$s, qName=%3$s", uri, localName, qName));
		super.endElement(uri, localName, qName);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		StringBuffer sb = new StringBuffer();
		for(int cloop=0; cloop<ch.length; cloop++){
			sb.append("[").append(ch[cloop]).append("]");
		}
		logger.info(String.format("Method: characters, ch=%1$s, start=%2$d, length=%3$d", sb.toString(), start, length));
		super.characters(ch, start, length);
	}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		StringBuffer sb = new StringBuffer();
		for(int cloop=0; cloop<ch.length; cloop++){
			sb.append("[").append(ch[cloop]).append("]");
		}
		logger.info(String.format("Method: ignorableWhitespace, ch=%1$s, start=%2$d, length=%3$d", sb.toString(), start, length));
		super.ignorableWhitespace(ch, start, length);
	}

	@Override
	public void processingInstruction(String target, String data)
			throws SAXException {
		logger.info(String.format("Method: processingInstruction, target=%1$s, data=%2$s", target, data));
		super.processingInstruction(target, data);
	}

	@Override
	public void skippedEntity(String name) throws SAXException {
		logger.info(String.format("Method: skippedEntity, name=%1$s", name));
		super.skippedEntity(name);
	}

	@Override
	public void warning(SAXParseException e) throws SAXException {
		logger.info(String.format("Method: warning"));
		super.warning(e);
	}

	@Override
	public void error(SAXParseException e) throws SAXException {
		logger.info(String.format("Method: error"));
		super.error(e);
	}

	@Override
	public void fatalError(SAXParseException e) throws SAXException {
		logger.info(String.format("Method: fatalError"));
		super.fatalError(e);
	}
	
}
