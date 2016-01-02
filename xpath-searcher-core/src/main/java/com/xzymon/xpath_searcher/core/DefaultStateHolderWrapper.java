package com.xzymon.xpath_searcher.core;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzymon.xpath_searcher.core.converter.HTMLStreamConverter;
import com.xzymon.xpath_searcher.core.converter.XMLStreamConverter;
import com.xzymon.xpath_searcher.core.converter.XPathSearchableStream;
import com.xzymon.xpath_searcher.core.exception.ParserException;

public class DefaultStateHolderWrapper extends AbstractStateHolderWrapper {
	private static final Logger logger = LoggerFactory.getLogger(DefaultStateHolderWrapper.class.getName());
	
	private List<String> standardCssElementsToRemove;
	
	public DefaultStateHolderWrapper(){
		super();
		
		standardCssElementsToRemove = new ArrayList<String>();
		standardCssElementsToRemove.add("head");
		standardCssElementsToRemove.add("img");
		standardCssElementsToRemove.add("input");
		standardCssElementsToRemove.add("br");
		standardCssElementsToRemove.add("area");
		standardCssElementsToRemove.add("button");
		standardCssElementsToRemove.add("script");
	}
	
	public boolean loadXMLStream(InputStream is) {
		boolean result = false;
		InputStream filteredIs = null;
		try{
			XMLStreamConverter xmlConverter = new XMLStreamConverter(is);
			filteredIs = xmlConverter.getConvertedStream();
			loadStream(filteredIs);
			result = true;
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParserException e1) {
			e1.printStackTrace();
		} finally {
			if(filteredIs!=null){
				try{
					filteredIs.close();
				} catch(IOException ex){
					ex.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public boolean loadHTMLStream(InputStream is, List<String> cssElementsToRemove) {
		boolean result = false;
		InputStream filteredIs = null;
		InputStream preparedIs = null;
		
		if(cssElementsToRemove!=null && cssElementsToRemove.size()>0){
			standardCssElementsToRemove.addAll(cssElementsToRemove);
		}
		/*
		cssElementsToRemove.add("div.footer1");
		cssElementsToRemove.add("div.footer2");
		cssElementsToRemove.add("div.footer3");
		cssElementsToRemove.add("div.footer4");
		//cssElementsToRemove.add("div.center");
		cssElementsToRemove.add("div.askCookies");
		cssElementsToRemove.add("div.hidden");
		*/
		
		try{
			XPathSearchableStream htmlConverter = new HTMLStreamConverter(is);
			filteredIs = htmlConverter.getConvertedStream();
			preparedIs = prepareHtmlWithJsoup(filteredIs, standardCssElementsToRemove);
			result = loadStream(preparedIs);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			if(filteredIs!=null){
				try{
					filteredIs.close();
				} catch(IOException ex){
					ex.printStackTrace();
				}
			}
			if(preparedIs!=null){
				try{
					preparedIs.close();
				} catch(IOException ex){
					ex.printStackTrace();
				}
			}
		}
		return result;
	}
	
	private InputStream prepareHtmlWithJsoup(InputStream srcStream, List<String> elementTypesToRemove) throws IOException{
		InputStream resultStream = null;
		int avail = srcStream.available();
		if(avail>0){
			byte[] bytes = new byte[avail];
			srcStream.read(bytes);
			String strSource = new String(bytes);
			logger.info(String.format("parsing by Jsoup..."));
			org.jsoup.nodes.Document parsedDoc = Jsoup.parse(strSource);
			for(String typeName : elementTypesToRemove){
				Elements els = parsedDoc.select(typeName);
				for(Element el: els){
					el.remove();
				}
			}
			String fromHtml = parsedDoc.html();
			String corrected = fromHtml.replaceAll("&nbsp;", "&#160;");
			logger.info(String.format("html retrieved from Jsoup parsed document"));
			resultStream = new ByteArrayInputStream(corrected.getBytes());
		}
		return resultStream;
	}
}
