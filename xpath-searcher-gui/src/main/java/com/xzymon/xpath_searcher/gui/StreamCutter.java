package com.xzymon.xpath_searcher.gui;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamCutter {
	private static final Logger logger = LoggerFactory.getLogger(StreamCutter.class.getName());
	
	private LinkedList<ControlPoint> list;
	private byte[] savedStream = null;
	
	public StreamCutter(InputStream is, boolean saveStream){
		list = new LinkedList<ControlPoint>();
		int read;
		char cread;
		int pos=0;
		if(!saveStream){
			try {
				while((read = is.read())!=-1){
					cread = (char)read;
					switch(cread){
					case '<':
						list.add(new LessThanControlPoint(pos));
						break;
					case '>':
						list.add(new GreaterThanControlPoint(pos));
						break;
					case '\"':
						list.add(new DoubleQuoteControlPoint(pos));
						break;
					case '\'':
						list.add(new SingleQuoteControlPoint(pos));
						break;
					case '/':
						list.add(new SlashSignControlPoint(pos));
						break;
					case '=':
						list.add(new EqualsSignControlPoint(pos));
						break;
					case ' ':
					case '\t':
					case '\n':
					case '\r':
					case '\f':
						try{
							list.add(new WhitespaceControlPoint(pos, cread));
						} catch (InvalidCharacterException ex){
							logger.error(String.format("[pos: %3$d]%1$s: %2$s", ex.getClass().getName(), ex.getMessage(), pos));
						}
						break;
					case '!':
						list.add(new ExclamationMarkControlPoint(pos));
						break;
					}
					pos++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				savedStream = new byte[is.available()];
				is.read(savedStream);
				while(pos<savedStream.length){
					cread = (char)savedStream[pos];
					switch(cread){
					case '<':
						list.add(new LessThanControlPoint(pos));
						break;
					case '>':
						list.add(new GreaterThanControlPoint(pos));
						break;
					case '\"':
						list.add(new DoubleQuoteControlPoint(pos));
						break;
					case '\'':
						list.add(new SingleQuoteControlPoint(pos));
						break;
					case '/':
						list.add(new SlashSignControlPoint(pos));
						break;
					case '=':
						list.add(new EqualsSignControlPoint(pos));
						break;
					case ' ':
					case '\t':
					case '\n':
					case '\r':
					case '\f':
						try{
							list.add(new WhitespaceControlPoint(pos, cread));
						} catch (InvalidCharacterException ex){
							logger.error(String.format("%1$s: %2$s at %3$d", ex.getClass().getName(), ex.getMessage(), pos));
						}
						break;
					}
					pos++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public LinkedList<ControlPoint> getControlPointsList() {
		return list;
	}
	
	public void logReport(){
		for (ControlPoint controlPoint : list) {
			logger.info(String.format("%1$d = %2$s", controlPoint.getPosition(), controlPoint.getChar()));
		}
	}
}
