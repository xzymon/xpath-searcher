package com.xzymon.xpath_searcher.gui.stain;

import java.awt.Color;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlStylePalette {
	private static final Logger logger = LoggerFactory.getLogger(XmlStylePalette.class.getName());
	
	private static String parameterAllStylesPrefix = "styles";
	
	private static final Map<Object, Class> styledAttributesTypes = new HashMap<Object, Class>();
	private static final SimpleAttributeSet defaultFlat = new SimpleAttributeSet();
	
	private final Map<String, SimpleAttributeSet> styledElements = new HashMap<String, SimpleAttributeSet>();
	private final Map<String, Object> styledAttributes = new HashMap<String, Object>();
	
	static {
		defaultFlat.addAttribute(StyleConstants.NameAttribute, "defaultFlat");
		defaultFlat.addAttribute(StyleConstants.Background, Color.WHITE);
		defaultFlat.addAttribute(StyleConstants.Foreground, Color.BLACK);
		defaultFlat.addAttribute(StyleConstants.Bold, false);
		defaultFlat.addAttribute(StyleConstants.Italic, false);
		defaultFlat.addAttribute(StyleConstants.Underline, false);
		defaultFlat.addAttribute(StyleConstants.StrikeThrough, false);
		defaultFlat.addAttribute(StyleConstants.FontSize, 10);
		
		styledAttributesTypes.put(StyleConstants.Background, java.awt.Color.class);
		styledAttributesTypes.put(StyleConstants.Foreground, java.awt.Color.class);
		styledAttributesTypes.put(StyleConstants.Bold, java.lang.Boolean.class);
		styledAttributesTypes.put(StyleConstants.Italic, java.lang.Boolean.class);
		styledAttributesTypes.put(StyleConstants.Underline, java.lang.Boolean.class);
		styledAttributesTypes.put(StyleConstants.StrikeThrough, java.lang.Boolean.class);
		styledAttributesTypes.put(StyleConstants.FontSize, java.lang.Integer.class);
	}
	
	private String name = null;
	
	private SimpleAttributeSet other = new SimpleAttributeSet(defaultFlat);
	private SimpleAttributeSet tagCasing = new SimpleAttributeSet(defaultFlat);
	private SimpleAttributeSet attributeName = new SimpleAttributeSet(defaultFlat);
	private SimpleAttributeSet attributeEqualsSign = new SimpleAttributeSet(defaultFlat);
	private SimpleAttributeSet attributeValue = new SimpleAttributeSet(defaultFlat);
	private SimpleAttributeSet error = new SimpleAttributeSet(defaultFlat);
	private SimpleAttributeSet rawText = new SimpleAttributeSet(defaultFlat);
	
	public XmlStylePalette(String name){
		this.name = name;
		
		styledElements.put("other", other);
		styledElements.put("tagCasing", tagCasing);
		styledElements.put("attributeName", attributeName);
		styledElements.put("attributeEqualsSign", attributeEqualsSign);
		styledElements.put("attributeValue", attributeValue);
		styledElements.put("error", error);
		styledElements.put("rawText", rawText);
		
		styledAttributes.put("background", StyleConstants.Background);
		styledAttributes.put("foreground", StyleConstants.Foreground);
		styledAttributes.put("bold", StyleConstants.Bold);
		styledAttributes.put("italic", StyleConstants.Italic);
		styledAttributes.put("underline", StyleConstants.Underline);
		styledAttributes.put("strikeThrough", StyleConstants.StrikeThrough);
		styledAttributes.put("fontSize", StyleConstants.FontSize);
	}
	
	public Map<String, SimpleAttributeSet> getKnowableStyledElements(){
		return Collections.unmodifiableMap(styledElements);
	}
	
	public Map<String, Object> getKnowableStyledAttributes(){
		return Collections.unmodifiableMap(styledAttributes);
	}

	public static String getParameterAllStylesPrefix() {
		return parameterAllStylesPrefix;
	}

	public static void setParameterAllStylesPrefix(String parameterAllStylesPrefix) {
		XmlStylePalette.parameterAllStylesPrefix = parameterAllStylesPrefix;
	}

	public String getName() {
		return name;
	}

	public SimpleAttributeSet getOther() {
		return other;
	}

	public void setOther(SimpleAttributeSet other) {
		this.other = other;
	}

	public SimpleAttributeSet getTagCasing() {
		return tagCasing;
	}

	public void setTagCasing(SimpleAttributeSet tagCasing) {
		this.tagCasing = tagCasing;
	}

	public SimpleAttributeSet getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(SimpleAttributeSet attributeName) {
		this.attributeName = attributeName;
	}

	public SimpleAttributeSet getAttributeEqualsSign() {
		return attributeEqualsSign;
	}

	public void setAttributeEqualsSign(SimpleAttributeSet attributeEqualsSign) {
		this.attributeEqualsSign = attributeEqualsSign;
	}

	public SimpleAttributeSet getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(SimpleAttributeSet attributeValue) {
		this.attributeValue = attributeValue;
	}

	public SimpleAttributeSet getError() {
		return error;
	}

	public void setError(SimpleAttributeSet error) {
		this.error = error;
	}

	public static SimpleAttributeSet getDefaultFlat() {
		return defaultFlat;
	}

	public SimpleAttributeSet getRawText() {
		return rawText;
	}

	public void setRawText(SimpleAttributeSet rawText) {
		this.rawText = rawText;
	}

	public boolean overrideStyleByParameter(String paramName, String paramValue){
		boolean overriden = false;
		String[] parameterParts = paramName.split("\\.");
		int partsDemand = 3;
		Integer intValue = null;
		Boolean boolValue = null;
		Color colorValue = null;
		Object value = null;
		int styleNamePos = 0;
		SimpleAttributeSet sas = null;
		if(parameterAllStylesPrefix!=null){
			partsDemand++;
			styleNamePos++;
		}
		if(parameterParts.length==partsDemand){
			if((parameterParts.length==4 && parameterParts[0].equals(parameterAllStylesPrefix)) || parameterParts.length==3){
				if(parameterParts[styleNamePos].equals(name)){
					if(styledElements.containsKey(parameterParts[styleNamePos+1]) && styledAttributes.containsKey(parameterParts[styleNamePos+2])){
						sas = styledElements.get(parameterParts[styleNamePos+1]);
						String type = styledAttributesTypes.get(styledAttributes.get(parameterParts[styleNamePos+2])).getCanonicalName();
						switch(type){
						case "java.awt.Color":
							if(paramValue!=null){
								try{
									intValue = Integer.parseInt(paramValue, 16);
									colorValue = new Color(intValue);
									value = colorValue;
								} catch (NumberFormatException ex){
									
								}
							}
							break;
						case "java.lang.Integer":
							if(paramValue!=null){
								try{
									intValue = Integer.parseInt(paramValue, 16);
									value = intValue;
								} catch (NumberFormatException ex){
									
								}
							}
							break;
						case "java.lang.Boolean":
							if(paramValue!=null){
								boolValue = Boolean.parseBoolean(paramValue);
								value = boolValue;
							}
							break;
						}
						if(value!=null){
							logger.info(String.format("for style %3$s: replacing value of %1$s to %2$s", paramName, value.toString(), name));
							sas.removeAttribute(parameterParts[styleNamePos+2]);
							sas.addAttribute(styledAttributes.get(parameterParts[styleNamePos+2]), value);
							overriden = true;
						}
					}
				}
			}
		}
		return overriden;
	}
	
	public boolean overrideStyleByParameterShort(String[] parameterParts, String paramValue, int styleNamePos){
		boolean overriden = false;
		Integer intValue = null;
		Boolean boolValue = null;
		Color colorValue = null;
		Object value = null;
		SimpleAttributeSet sas = null;
		//logger.info(String.format("Testing: styledElements <-> %1$s | styledAttributes <-> %2$s", parameterParts[styleNamePos+1], parameterParts[styleNamePos+2]));
		if(styledElements.containsKey(parameterParts[styleNamePos+1]) && styledAttributes.containsKey(parameterParts[styleNamePos+2])){
			sas = styledElements.get(parameterParts[styleNamePos+1]);
			String type = styledAttributesTypes.get(styledAttributes.get(parameterParts[styleNamePos+2])).getCanonicalName();
			//logger.info(String.format("style : %1$s", type));
			switch(type){
			case "java.awt.Color":
				if(paramValue!=null){
					try{
						intValue = Integer.parseInt(paramValue, 16);
						colorValue = new Color(intValue);
						value = colorValue;
						//logger.info("color");
					} catch (NumberFormatException ex){
					
					}
				}
				break;
			case "java.lang.Integer":
				if(paramValue!=null){
					try{
						intValue = Integer.parseInt(paramValue, 16);
						value = intValue;
						//logger.info("integer");
					} catch (NumberFormatException ex){
					
					}
				}
				break;
			case "java.lang.Boolean":
				if(paramValue!=null){
					boolValue = Boolean.parseBoolean(paramValue);
					value = boolValue;
					//logger.info("boolean");
				}
				break;
			}
			if(value!=null){
				logger.info(String.format("for style %3$s: replacing value of style %1$s to %2$s", parameterParts[styleNamePos], value.toString(), name));
				sas.removeAttribute(parameterParts[styleNamePos+2]);
				sas.addAttribute(styledAttributes.get(parameterParts[styleNamePos+2]), value);
				overriden = true;
			}
		}
		return overriden;
	}
	
}
