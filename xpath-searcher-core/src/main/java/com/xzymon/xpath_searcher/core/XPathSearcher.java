/**
 * 
 */
package com.xzymon.xpath_searcher.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.xml.xpath.XPathExpressionException;

import com.xzymon.xpath_searcher.core.DefaultStateHolderWrapper;
import com.xzymon.xpath_searcher.core.listener.SystemOutStreamXPathSearchingListener;
import com.xzymon.xpath_searcher.core.listener.XPathSearchingListener;

/**
 * @author root
 *
 */
public final class XPathSearcher {
	private static final String PROPS_PATH = "/xpath-searcher.properties";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		XPathSearcher searcher = new XPathSearcher();
		searcher.loadProperties();
		boolean valid = searcher.processInvocation(args);
		if(!valid){
			searcher.usage();
		}
	}
	
	private void usage(){
		System.out.println(usageString());
	}

	private String usageString(){
		return "usage:\n"
				+ "java XPathSearcher [-x|-h] xpath_list [further_options] file \n"
				+ "where:\n"
				+ "-x\t--xml\t\tDefault way of processing. Expects XML file.\n"
				+ "-h\t--html\t\tProcessing given file as HTML file. Expects HTML file. \n\t\t\tRemoves some of HTML tags before processing XPath phrases\n"
				+ "xpath_list\t\tThe list of XPath expressions to search in file.\n"
				+ "\t\t\tFormat: \"xpath_expr1;xpath_expr2;xpath_expr3\""
				+ "[further options]\n"
				+ "when processing html:\n"
				+ "-r\t--remove-css\tUse to provide list of css elements to remove before\n\t\t\tsearching while processing HTML.\n"
				+ "\t\t\tFor example: to remove all div elements with class=\"header\"\n"
				+ "\t\t\tor with class=\"footer\" use\n" 
				+ "\t\t\t\t-r \"div.header;div.footer\"\n";
	}
	
	private boolean processInvocation(String[] args){
		boolean areParamsValid = false;
		List<String> xpathsList = null;
		List<String> cssList = new LinkedList<String>();
		int iloop=0;
		System.out.format("There are %1$d parameters given.\n", args.length);
		int shift=0;
		int argNo=0;
		int nodesFound = 0;
		boolean isHtml = false;
		boolean rOption = false;
		while(iloop<args.length-1){
			
			System.out.format("[%1$d]: %2$s\n", iloop, args[iloop]);
			argNo=iloop+shift;
			switch(argNo){
			case 0:
				if(args[argNo].equals("-x") || args[argNo].equals("--xml")){
					
				} else {
					if(args[argNo].equals("-h") || args[argNo].equals("--html")){
						isHtml = true;
					} else {
						shift = 1;
						iloop--;
					}
				}
				break;
			case 1:
				xpathsList = Arrays.asList(args[iloop].split(";"));
				for(String xpath: xpathsList){
					System.out.format("\txpath: %1$s\n", xpath);
				}
				break;
			case 2:
				if(isHtml && args[argNo].equals("-r") || args[argNo].equals("--remove-css")){
					rOption = true;
				}
				break;
			default:
				if(rOption){
					cssList = Arrays.asList(args[iloop].split(";"));
					for(String css: cssList){
						System.out.format("\tcss: %1$s\n", css);
					}
					rOption=false;
				}
			}
			iloop++;
		}
		String filepath = args[args.length-1];
		System.out.format("file: %1$s\n",filepath);
		if(xpathsList!=null && xpathsList.size()>0){
			try {
				List<XPathSearchingListener> xpathListeners = new LinkedList<XPathSearchingListener>();
				xpathListeners.add(new SystemOutStreamXPathSearchingListener());
				if(isHtml){
					htmlFileSearch(filepath, xpathsList, cssList, xpathListeners);
				} else {
					xmlFileSearch(filepath, xpathsList, xpathListeners);
				}
				areParamsValid = true;
			} catch (FileNotFoundException e) {
				System.out.format("Incorrect filepath: %1$s", filepath);
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.format("No xpath to search. Searching aborted.\n");
		}
		
		return areParamsValid;
	}
	
	public static void htmlFileSearch(String filepath, List<String> xpathsList, List<String> removeCssElementsList, 
			List<XPathSearchingListener> xpathListeners) throws FileNotFoundException, XPathExpressionException{
		int nodesFound=0;
		if(xpathsList!=null && xpathsList.size()>0){
			FileInputStream fis = new FileInputStream(filepath);
			DefaultStateHolderWrapper holder = new DefaultStateHolderWrapper();
			if(holder.loadHTMLStream(fis, removeCssElementsList)){
				for(XPathSearchingListener xpathListener : xpathListeners){
					holder.addSearchingListener(xpathListener);
				}
				for(String xpath: xpathsList){
					nodesFound = holder.newSearch(xpath);
					for(int nloop=0; nloop<nodesFound; nloop++){
						holder.nextNode();
					}
				}
			}
		}
	}
	
	public static void xmlFileSearch(String filepath, List<String> xpathsList, 
			List<XPathSearchingListener> xpathListeners) throws FileNotFoundException, XPathExpressionException{
		int nodesFound=0;
		if(xpathsList!=null && xpathsList.size()>0){
			FileInputStream fis = new FileInputStream(filepath);
			DefaultStateHolderWrapper holder = new DefaultStateHolderWrapper();
			if(holder.loadXMLStream(fis)){
				for(XPathSearchingListener xpathListener : xpathListeners){
					holder.addSearchingListener(xpathListener);
				}
				for(String xpath: xpathsList){
					nodesFound = holder.newSearch(xpath);
					for(int nloop=0; nloop<nodesFound; nloop++){
						holder.nextNode();
					}
				}
			}
		}
	}
	
	public static void htmlSearch(InputStream is, List<String> xpathsList, List<String> removeCssElementsList, 
			List<XPathSearchingListener> xpathListeners) throws XPathExpressionException{
		int nodesFound=0;
		if(xpathsList!=null && xpathsList.size()>0){
			DefaultStateHolderWrapper holder = new DefaultStateHolderWrapper();
			if(holder.loadHTMLStream(is, removeCssElementsList)){
				for(XPathSearchingListener xpathListener : xpathListeners){
					holder.addSearchingListener(xpathListener);
				}
				for(String xpath: xpathsList){
					nodesFound = holder.newSearch(xpath);
					for(int nloop=0; nloop<nodesFound; nloop++){
						holder.nextNode();
					}
				}
			}
		}
	}
	
	public static void xmlSearch(InputStream is, List<String> xpathsList, 
			List<XPathSearchingListener> xpathListeners) throws XPathExpressionException{
		int nodesFound=0;
		if(xpathsList!=null && xpathsList.size()>0){
			DefaultStateHolderWrapper holder = new DefaultStateHolderWrapper();
			if(holder.loadXMLStream(is)){
				for(XPathSearchingListener xpathListener : xpathListeners){
					holder.addSearchingListener(xpathListener);
				}
				for(String xpath: xpathsList){
					nodesFound = holder.newSearch(xpath);
					for(int nloop=0; nloop<nodesFound; nloop++){
						holder.nextNode();
					}
				}
			}
		}
	}
	
	private void loadProperties(){
		Properties props = new Properties();
		
		InputStream is = this.getClass().getResourceAsStream(PROPS_PATH);
		if(is!=null){
			try{
				props.load(is);
			} catch (IllegalArgumentException ex){
				ex.printStackTrace();
			} catch (IOException ex){
				ex.printStackTrace();
			}
		}
	}
	 
}
