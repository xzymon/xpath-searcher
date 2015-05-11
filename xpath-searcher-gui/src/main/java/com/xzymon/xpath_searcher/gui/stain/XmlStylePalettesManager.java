package com.xzymon.xpath_searcher.gui.stain;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlStylePalettesManager {
	private static final Logger logger = LoggerFactory.getLogger(XmlStylePalettesManager.class.getName());
	
	public static final String DEFAULT_PALETTE_NAME = "default";
	
	private Map<String, XmlStylePalette> styles = new HashMap<String, XmlStylePalette>();
	
	private XmlStylePalette currentPalette;
	
	public XmlStylePalettesManager(){
		XmlStylePalette defaultPalette = new XmlStylePalette(DEFAULT_PALETTE_NAME);
		styles.put(DEFAULT_PALETTE_NAME, defaultPalette);
		currentPalette = styles.get(DEFAULT_PALETTE_NAME);
	}
	
	public XmlStylePalette getByName(String name){
		return styles.get(name);
	}
	
	public XmlStylePalette getCurrentPalette() {
		return currentPalette;
	}

	public void setCurrentPalette(XmlStylePalette currentPalette) {
		this.currentPalette = currentPalette;
	}

	public boolean overrideStyleByParameter(String paramName, String paramValue){
		boolean overriden = false;
		String[] parameterParts = paramName.split("\\.");
		int partsDemand = 3;
		int styleNamePos = 0;
		String prefix = XmlStylePalette.getParameterAllStylesPrefix();
		if(prefix!=null){
			partsDemand++;
			styleNamePos++;
		}
		logger.info(String.format("parameters: length=%1$d, demand=%2$d", parameterParts.length, partsDemand));
		if(parameterParts.length==partsDemand){
			logger.info(String.format("parameters: prefix=%1$s", prefix));
			if((parameterParts.length==4 && parameterParts[0].equals(prefix)) || parameterParts.length==3){
				if(styles.containsKey(parameterParts[styleNamePos])){
					//logger.info("contains");
					overriden = styles.get(parameterParts[styleNamePos]).overrideStyleByParameterShort(parameterParts, paramValue, styleNamePos);
				} else {
					//logger.info("adding");
					XmlStylePalette style = new XmlStylePalette(parameterParts[styleNamePos]); 
					styles.put(parameterParts[styleNamePos], style);
					overriden = style.overrideStyleByParameterShort(parameterParts, paramValue, styleNamePos);
				}
			}
		} 
		return overriden;
	}
}
