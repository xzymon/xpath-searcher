package com.xzymon.xpath_searcher.core.dom;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzymon.xpath_searcher.core.XmlPreprocessingMode;

public class DocumentNodeRepresentationUsingHTMLDefaultOrphanedPolicies {
	private static final Logger logger = LoggerFactory.getLogger(DocumentNodeRepresentationUsingHTMLDefaultOrphanedPolicies.class);
	
	DocumentTreeRepresentation tree;
	private OrphanedPolicies htmlDefaultPolicies;
	
	private byte[] savedStream;
	private String input, expected, actual;
	
	
	public DocumentNodeRepresentationUsingHTMLDefaultOrphanedPolicies(){
		htmlDefaultPolicies = new HTMLDefaultOrphanedPolicies();
	}

	@Test
	public void test1(){
		input = "<!DOCTYPE html>\n"
+"<html><head>\n"
+"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
+"<meta name=\"robots\" content=\"NOODP\">\n"
+"<meta name=\"verify-v1\" content=\"6VGCEM9Je2qxQQThvi0XNdBMkIQHuQu9rCWQhDhZCX4=\">\n"
+"<title>Some title</title>\n"
+"<meta name=\"description\" content=\"Lista zakończonych\">\n"
+"<link type=\"text/css\" rel=\"stylesheet\" href=\"standard.css\">\n"
+"</head><body>\n"
+"<ul>\n"
+"<!-- pierwszy na liście -->\n"
+"\t<li><p><a href=\"http://google.com\">Google</a>\n"
+"</body></html>";
		expected = "<html><head>\n"
+"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\n"
+"<meta name=\"robots\" content=\"NOODP\"/>\n"
+"<meta name=\"verify-v1\" content=\"6VGCEM9Je2qxQQThvi0XNdBMkIQHuQu9rCWQhDhZCX4=\"/>\n"
+"<title>Some title</title>\n"
+"<meta name=\"description\" content=\"Lista zakończonych\"/>\n"
+"<link type=\"text/css\" rel=\"stylesheet\" href=\"standard.css\"/>\n"
+"</head><body>\n"
+"<ul>\n"
+"<!-- pierwszy na liście -->\n"
+"\t<li><p><a href=\"http://google.com\">Google</a>\n"
+"</p></li></ul>"
+"</body></html>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				htmlDefaultPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
		
		input = "<!DOCTYPE html>\n"
				+"<html><head>\n"
				+"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
				+"<meta name=\"robots\" content=\"NOODP\">\n"
				+"<meta name=\"verify-v1\" content=\"6VGCEM9Je2qxQQThvi0XNdBMkIQHuQu9rCWQhDhZCX4=\">\n"
				+"<title>Some title</title>\n"
				+"<meta name=\"description\" content=\"Lista zakończonych\">\n"
				+"<link type=\"text/css\" rel=\"stylesheet\" href=\"standard.css\">\n"
				+"</head><body>\n"
				+"\t<ul>\n"
				+"\t\t<!-- pierwszy na liście -->\n"
				+"\t\t<li>\n"
				+"\t\t\t<p><a href=\"http://google.com\">Google</a>\n"
				+"\t</ul>\n"
				+"\t<!-- jakiś obrazek -->\n"
				+"\t<img src=\"pl_002.png\" usemap=\"#blog\" class=\"blogTab\" id=\"showBlog\">\n"
				+"\t<!-- na koniec skrypt -->\n"
				+"\t<script type=\"text/javascript\">\n"
				+"\t\t (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){\n"
				+"\t\t\t (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),\n"
				+"\t\t\t m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)\n"
				+"\t\t\t })(window,document,'script','//www.google-analytics.com/analytics.js','ga');\n"
				+"\t\t\t ga('create', 'UA-51903666-1', 'za10groszy.pl');\n"
				+"\t\t\t ga('send', 'pageview');\n"
				+"\t</script>\n"
				+"</body></html>";
		expected = "<html><head>\n"
				+"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\n"
				+"<meta name=\"robots\" content=\"NOODP\"/>\n"
				+"<meta name=\"verify-v1\" content=\"6VGCEM9Je2qxQQThvi0XNdBMkIQHuQu9rCWQhDhZCX4=\"/>\n"
				+"<title>Some title</title>\n"
				+"<meta name=\"description\" content=\"Lista zakończonych\"/>\n"
				+"<link type=\"text/css\" rel=\"stylesheet\" href=\"standard.css\"/>\n"
				+"</head><body>\n"
				+"\t<ul>\n"
				+"\t\t<!-- pierwszy na liście -->\n"
				+"\t\t<li>\n"
				+"\t\t\t<p><a href=\"http://google.com\">Google</a>\n"
				+"\t</p></li></ul>\n"
				+"\t<!-- jakiś obrazek -->\n"
				+"\t<img src=\"pl_002.png\" usemap=\"#blog\" class=\"blogTab\" id=\"showBlog\"/>\n"
				+"\t<!-- na koniec skrypt -->\n"
				+"\t<script type=\"text/javascript\">\n"
				+"\t\t (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){\n"
				+"\t\t\t (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),\n"
				+"\t\t\t m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)\n"
				+"\t\t\t })(window,document,'script','//www.google-analytics.com/analytics.js','ga');\n"
				+"\t\t\t ga('create', 'UA-51903666-1', 'za10groszy.pl');\n"
				+"\t\t\t ga('send', 'pageview');\n"
				+"\t</script>\n"
				+"</body></html>";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				htmlDefaultPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}

	public void checkTest() {
		input = "";
		expected = "";
		savedStream = input.getBytes();
		tree = new DocumentTreeRepresentation(new ByteArrayInputStream(savedStream), XmlPreprocessingMode.ASSUME_HTML,
				htmlDefaultPolicies);
		actual = tree.stringifyTree();
		logger.info("input is [" + input + "]");
		logger.info("expected is [" + expected + "]");
		logger.info("actual is [" + actual + "]");
		assertEquals(expected, actual);
	}
}
