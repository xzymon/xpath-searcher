package com.xzymon.xpath_searcher.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NodeList;

import com.xzymon.xpath_searcher.core.converter.HTMLStreamConverter;
import com.xzymon.xpath_searcher.core.converter.XPathSearchableStream;
import com.xzymon.xpath_searcher.core.exception.ParserException;
import com.xzymon.xpath_searcher.core.listener.XPathSearchingListener;

/**
 * Klasa przeznaczona do realizowania wyszukiwania w przekazanym strumieniu, z
 * możliwością rejestrowania listenerów {@link XPathSearchingListener} do
 * nasłuchiwania zdarzeń z przebiegu wyszukiwania.
 * 
 * @author Szymon Ignaciuk
 * @since 0.1.1.Final
 */
public class XPathSearchingProvider {
	private static final Logger logger = LoggerFactory.getLogger(XMLStateHolder.class.getName());

	private char[] savedChars;
	private XPathProcessor engine = null;
	private boolean empty;

	private List<String> standardCssElementsToRemove = Arrays
			.asList(new String[] { "head", "img", "input", "br", "hr", "area", "button", "script" });

	public XPathSearchingProvider(InputStream is, XmlPreprocessingMode premode, List<String> cssElementsToRemove)
			throws ParserException {
		empty = true;
		byte[] savedStream = null;
		if (is != null) {
			try {
				int avail = is.available();
				if (avail > 0) {
					if (premode == XmlPreprocessingMode.ASSUME_HTML) {
						savedStream = loadHTMLStream(is, cssElementsToRemove);
					} else {
						savedStream = new byte[avail];
						is.read(savedStream);
					}
					ByteArrayInputStream bais = new ByteArrayInputStream(savedStream);
					InputStreamReader reader = new InputStreamReader(bais);
					char[] charBuf = new char[savedStream.length];
					int read = reader.read(charBuf);
					savedChars = new char[read];
					System.arraycopy(charBuf, 0, savedChars, 0, read);
					engine = new XPathProcessorImpl(new ByteArrayInputStream(savedStream), null);
					empty = false;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean hasExpression() {
		return engine.hasExpression();
	}

	public String getExpression() {
		return engine.getExpression();
	}

	public int newSearch(String expression) throws XPathExpressionException {
		return engine.findNodes(expression);
	}

	public void nextNode() {
		engine.nextNode();
	}

	public void reset() {
		engine.reset();
	}

	public void clear() {
		engine.clear();
	}

	public boolean isEmpty() {
		return empty;
	}

	public char[] getData() {
		return this.savedChars;
	}

	public NodeList getFoundNodeList() {
		return engine.getAllFoundNodes();
	}

	public List<XPathSearchingListener> getSearchingListeners() {
		return engine.getSearchingListeners();
	}

	public void addSearchingListener(XPathSearchingListener listener) {
		engine.addSearchingListener(listener);
	}

	public void removeSearchingListener(XPathSearchingListener listener) {
		engine.removeSearchingListener(listener);
	}

	public List<String> getCurrentCssElementsToRemoveFromHtml() {
		return standardCssElementsToRemove;
	}

	private byte[] loadHTMLStream(InputStream is, List<String> cssElementsToRemove) {
		boolean result = false;
		InputStream filteredIs = null;
		byte[] preparedBytes = null;

		if (cssElementsToRemove != null && cssElementsToRemove.size() > 0) {
			standardCssElementsToRemove.addAll(cssElementsToRemove);
		}
		/*
		 * cssElementsToRemove.add("div.footer1");
		 * cssElementsToRemove.add("div.footer2");
		 * cssElementsToRemove.add("div.footer3");
		 * cssElementsToRemove.add("div.footer4");
		 * //cssElementsToRemove.add("div.center");
		 * cssElementsToRemove.add("div.askCookies");
		 * cssElementsToRemove.add("div.hidden");
		 */

		try {
			XPathSearchableStream htmlConverter = new HTMLStreamConverter(is);
			filteredIs = htmlConverter.getConvertedStream();
			preparedBytes = prepareHtmlWithJsoup(filteredIs, standardCssElementsToRemove);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			if (filteredIs != null) {
				try {
					filteredIs.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return preparedBytes;
	}

	private byte[] prepareHtmlWithJsoup(InputStream srcStream, List<String> elementTypesToRemove) throws IOException {
		String corrected = null;
		int avail = srcStream.available();
		if (avail > 0) {
			byte[] bytes = new byte[avail];
			srcStream.read(bytes);
			String strSource = new String(bytes);
			logger.info(String.format("parsing by Jsoup..."));
			org.jsoup.nodes.Document parsedDoc = Jsoup.parse(strSource);
			for (String typeName : elementTypesToRemove) {
				Elements els = parsedDoc.select(typeName);
				for (Element el : els) {
					el.remove();
				}
			}
			String fromHtml = parsedDoc.html();
			corrected = fromHtml.replaceAll("&nbsp;", "&#160;");
			logger.info(String.format("html retrieved from Jsoup parsed document"));
		}
		return corrected.getBytes();
	}

}
