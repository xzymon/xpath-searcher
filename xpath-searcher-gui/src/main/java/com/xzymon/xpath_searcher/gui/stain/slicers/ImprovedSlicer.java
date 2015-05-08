package com.xzymon.xpath_searcher.gui.stain.slicers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzymon.xpath_searcher.gui.stain.exceptions.InvalidCharacterException;
import com.xzymon.xpath_searcher.gui.stain.exceptions.SlicingException;
import com.xzymon.xpath_searcher.gui.stain.handlers.AttributeRepresentation;
import com.xzymon.xpath_searcher.gui.stain.handlers.ErrorRepresentation;
import com.xzymon.xpath_searcher.gui.stain.handlers.ProcessingHandler;
import com.xzymon.xpath_searcher.gui.stain.handlers.Slice;
import com.xzymon.xpath_searcher.gui.stain.handlers.SliceAttribute;
import com.xzymon.xpath_searcher.gui.stain.handlers.SliceInterior;
import com.xzymon.xpath_searcher.gui.stain.handlers.SliceRepresentation;
import com.xzymon.xpath_searcher.gui.stain.handlers.SlicerMode;
import com.xzymon.xpath_searcher.gui.stain.handlers.control.ControlPoint;
import com.xzymon.xpath_searcher.gui.stain.handlers.control.DoubleQuoteControlPoint;
import com.xzymon.xpath_searcher.gui.stain.handlers.control.EqualsSignControlPoint;
import com.xzymon.xpath_searcher.gui.stain.handlers.control.ExclamationMarkControlPoint;
import com.xzymon.xpath_searcher.gui.stain.handlers.control.GreaterThanControlPoint;
import com.xzymon.xpath_searcher.gui.stain.handlers.control.LessThanControlPoint;
import com.xzymon.xpath_searcher.gui.stain.handlers.control.QuestionMarkControlPoint;
import com.xzymon.xpath_searcher.gui.stain.handlers.control.SingleQuoteControlPoint;
import com.xzymon.xpath_searcher.gui.stain.handlers.control.SlashSignControlPoint;
import com.xzymon.xpath_searcher.gui.stain.handlers.control.WhitespaceControlPoint;

public class ImprovedSlicer {
private static final Logger logger = LoggerFactory.getLogger(ImprovedSlicer.class.getName());
	
	private byte[] savedStream = null;
	private LinkedList<SlicerMode> modeList = null;
	private LinkedList<ControlPoint> controlPoints;
	
	LinkedList<SliceRepresentation> slicesR = null;
	
	private ProcessingHandler handler = null;
	
	public ImprovedSlicer(InputStream is){
		try{
			int avail = is.available();
			if(avail>0){
				savedStream = new byte[avail];
				is.read(savedStream);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	public void useHandler(){
		AttributeRepresentation attr = null;
		ErrorRepresentation error = null;
		int rtFirstPos=0, rtLastPos=0;	//rawTest positions
		
		for(SliceRepresentation sliceR: slicesR){
			logger.info(sliceR.toString());
			
			//TODO: here
			
			if(sliceR.isOther()){
				handler.otherTag(sliceR.getStartPosition(), sliceR.getEndPosition());
			} else {
				handler.lessThanStartChar(sliceR.getStartPosition());
				handler.tagName(sliceR.getNameStartPosition(), sliceR.getNameEndPosition());
				if(sliceR.getInterior()!=null){
					for(SliceInterior sInt : sliceR.getInterior()){
						if(sInt instanceof AttributeRepresentation){
							attr = (AttributeRepresentation) sInt;
							handler.attributeName(attr.getStartsAt(), attr.getNameEndsAt());
							handler.attributeEqualsSign(attr.getEqualsSignAt());
							handler.attributeValue(attr.getStartQuotationMarkAt(), attr.getEndQuotationMarkAt());
						}
						if(sInt instanceof ErrorRepresentation){
							error = (ErrorRepresentation) sInt;
							handler.error(error.getStartPosition(), error.getEndPosition());
						}
					}
				}
				handler.greaterThanEndingChar(sliceR.getEndPosition());
			}
		}
	}
	
	public List<SliceRepresentation> slice() throws SlicingException{
		modeList = new LinkedList<SlicerMode>();
		
		SliceRepresentation curSlice = null;
		AttributeRepresentation curAttr = null;
		ErrorRepresentation curError = null;
		slicesR = new LinkedList<SliceRepresentation>();
		LinkedList<AttributeRepresentation> attrsR = new LinkedList<AttributeRepresentation>();
		LinkedList<ErrorRepresentation> errorsR = new LinkedList<ErrorRepresentation>();
		
		Slice currentSlice = null;
		SliceAttribute currentAttribute = null;
		
		boolean reinvoke = false;
		boolean dropAttr = false;
		
		addMode(SlicerMode.NONE);
				
		findControlPoints();
		int cplength = controlPoints.size();
		ControlPoint cp = null;
		ControlPoint pre_cp = null;
		// for bo trzeba się odwoływać do poprzednich/następnych elementów listy
		for(int cploop=0; cploop<cplength; cploop++){
			if(modeList.getLast()==SlicerMode.ERROR){
				logger.info(String.format("error detected at %1$d on char=%2$d ['%3$s']", cp.getPosition(), (int)cp.getChar(), cp.getChar()));
			}
			pre_cp = cp;
			cp = controlPoints.get(cploop);
			
			logger.info(String.format("curSlice is %1$s, mode is %2$s, pos=%3$d, ch=[%4$s]",
					curSlice==null?"null":"not null", modeList.getLast().toString(), cp.getPosition(), cp.getChar()));
			
			switch(cp.getChar()){
			case '<':
				switch(modeList.getLast()){
				case NONE:
					curSlice = new SliceRepresentation();
					curSlice.setStartPosition(cp.getPosition());
					slicesR.addLast(curSlice);
					addMode(SlicerMode.INSIDE_SLICE);
					break;
				case INSIDE_SLICE:
					curError = new ErrorRepresentation();
					curError.setStartPosition(cp.getPosition());
					addMode(SlicerMode.ERROR);
					break;
				default:
					break;	
				}
				break;
			case '>':
				//invoke/reinvoke jest na potrzeby obsłużenia za jednym razem zejścia z INSIDE_ATTRIBUTE i INSIDE_SLICE
				//zmienione z if'ów na switch-case na potrzeby ewentualnej rozbudowy (przypuszczanie obsługa błędów)
				do {
					reinvoke = false;
					switch(modeList.getLast()){
					case INSIDE_ATTRIBUTE:
						if(curAttr!=null){
							if(curAttr.getNameEndsAt()==-1){
								curAttr.setNameEndsAt(cp.getPosition()-1);
							}
						}
						modeList.removeLast();
						reinvoke = true;
						break;
					case INSIDE_SLICE:
						if(pre_cp.getChar()=='/' && pre_cp.getPosition()==cp.getPosition()-1){
							if(curSlice.getClosingSlashPosition()==-1){
								curSlice.setClosingSlashPosition(pre_cp.getPosition());
							}
						}
						if(pre_cp.getPosition()<cp.getPosition()-1 && (pre_cp.getChar()=='<' || pre_cp.getChar()=='/')){
							curSlice.setNameStartPosition(pre_cp.getPosition()+1);
							curSlice.setNameEndPosition(pre_cp.getPosition()-1);
							curSlice.setEndPosition(cp.getPosition());
						}
						curSlice.setEndPosition(cp.getPosition());
						curSlice = null;
						modeList.removeLast();
						break;
					case ERROR:
						modeList.removeLast();
						regainError(cp, curError);
						pre_cp = getPreviousControlPointFrom(pre_cp);
						reinvoke = true;
					default:
						break;
					}
				} while (reinvoke);
				break;
			case '\"':
				if(curSlice!=null && !curSlice.isOther()){
					do{
						reinvoke=false;
						switch(modeList.getLast()){
						case NONE:
							addMode(SlicerMode.INSIDE_DOUBLE_QUOTES);
							break;
						case INSIDE_SLICE:
							addMode(SlicerMode.INSIDE_DOUBLE_QUOTES);
							break;
						case INSIDE_DOUBLE_QUOTES:
							// wycofaj tryb
							modeList.removeLast();
							if(curAttr!=null){
								curAttr.setEndQuotationMarkAt(cp.getPosition());
							}
							curSlice.addAttribute(curAttr);
							curSlice.addInterior(curAttr);
							curAttr = null;
							reinvoke = true;
							dropAttr = true;
							break;
						case INSIDE_ATTRIBUTE:
							if(dropAttr){
								modeList.removeLast();
								dropAttr = false;
							} else {
								if(pre_cp!=null && pre_cp.getChar()=='=' && pre_cp.getPosition()==cp.getPosition()-1){
									addMode(SlicerMode.INSIDE_DOUBLE_QUOTES);
									curAttr.setDoubleQuoted(true);
									curAttr.setStartQuotationMarkAt(cp.getPosition());
								}
							}
							break;
						default:
							break;
						}
					} while(reinvoke);
				}
				break;
			case '\'':
				if(curSlice!=null && !curSlice.isOther()){
					do{
						reinvoke=false;
						switch(modeList.getLast()){
						case NONE:
							addMode(SlicerMode.INSIDE_SINGLE_QUOTES);
							break;
						case INSIDE_SLICE:
							addMode(SlicerMode.INSIDE_SINGLE_QUOTES);
							break;
						case INSIDE_SINGLE_QUOTES:
							// wycofaj tryb
							modeList.removeLast();
							if(curAttr!=null){
								curAttr.setEndQuotationMarkAt(cp.getPosition());
							}
							curSlice.addAttribute(curAttr);
							curSlice.addInterior(curAttr);
							curAttr = null;
							reinvoke = true;
							dropAttr = true;
							break;
						case INSIDE_ATTRIBUTE:
							if(dropAttr){
								modeList.removeLast();
								dropAttr = false;
							} else {
								if(pre_cp!=null && pre_cp.getChar()=='=' && pre_cp.getPosition()==cp.getPosition()-1){
									addMode(SlicerMode.INSIDE_DOUBLE_QUOTES);
									curAttr.setSingleQuoted(true);
									curAttr.setStartQuotationMarkAt(cp.getPosition());
								}
							}
							break;
						default:
							break;
						}
					} while(reinvoke);
				}
				break;
			case '/':
				if(curSlice!=null && !curSlice.isOther()){
					do {
						reinvoke = false;
						switch(modeList.getLast()){
						case INSIDE_ATTRIBUTE:
							modeList.removeLast();
							curAttr = null;
							reinvoke = true;
							break;
						// sprawdzenie czy to ma jakiekolwiek znaczenie - jedynie w INSIDE_SLICE
						// sprawdzanie tylko wstecz - więc do sprawdzenia tylko jedna opcja
						case INSIDE_SLICE:
							if(pre_cp.getPosition()==cp.getPosition()-1 && pre_cp.getChar()=='<'){
								if(curSlice.getClosingSlashPosition()==-1){
									curSlice.setClosingSlashPosition(cp.getPosition());
								}
							}
							break;
						case ERROR:
							modeList.removeLast();
							regainError(cp, curError);
							pre_cp = getPreviousControlPointFrom(pre_cp);
							reinvoke = true;
						default:
							break;
						}
					} while (reinvoke);
				}
				break;
			case '=':
				if(curSlice!=null && !curSlice.isOther()){
					// powinno mieć znaczenie tylko przy INSIDE_ATTRIBUTE
					// jeśli poprzedni znak na liście jest whitespace i nie jest bezpośrednio poprzedni to  włącz tryb INSIDE_ATTRIBUTE
					if(modeList.getLast().equals(SlicerMode.INSIDE_SLICE)){
						if(pre_cp!=null && pre_cp instanceof WhitespaceControlPoint && pre_cp.getPosition()<cp.getPosition()-1){
							addMode(SlicerMode.INSIDE_ATTRIBUTE);
							curAttr = new AttributeRepresentation();
							curAttr.setStartsAt(pre_cp.getPosition()+1);
							curAttr.setNameEndsAt(cp.getPosition()-1);
							curAttr.setEqualsSignAt(cp.getPosition());
						}
					}
				}
				break;
			case ' ':
			case '\t':
			case '\n':
			case '\r':
			case '\f':
				if(curSlice!=null && !curSlice.isOther()){
					do {
						reinvoke = false;
						switch(modeList.getLast()){
						// jeśli tryb INSIDE_ATTRIBUTE to powinno go wyłączać i konstruować/aktualizować ostatni SliceAttribute
						case INSIDE_ATTRIBUTE:
							modeList.removeLast();
							curError = new ErrorRepresentation();
							curError.setStartPosition(curAttr.getStartsAt());
							curError.setEndPosition(cp.getPosition()-1);
							curAttr = null;
							curSlice.addError(curError);
							curSlice.addInterior(curError);
							reinvoke = true;
							break;
						case INSIDE_SLICE:
							if(pre_cp.getPosition()<cp.getPosition()-1 && (pre_cp.getChar()=='<' || pre_cp.getChar()=='/')){
								curSlice.setNameStartPosition(pre_cp.getPosition()+1);
								curSlice.setNameEndPosition(cp.getPosition()-1);
							}
							break;
						case ERROR:
							modeList.removeLast();
							regainError(cp, curError);
							pre_cp = getPreviousControlPointFrom(pre_cp);
							reinvoke = true;
						default:
							break;
						}
					} while (reinvoke);
				}
				break;
			case '?':
				if(curSlice!=null && !curSlice.isOther()){
					do {
						reinvoke = false;
						switch(modeList.getLast()){
						// jeśli tryb INSIDE_ATTRIBUTE to powinno go wyłączać i konstruować/aktualizować ostatni SliceAttribute
						case INSIDE_ATTRIBUTE:
							modeList.removeLast();
							curError = new ErrorRepresentation();
							curError.setStartPosition(curAttr.getStartsAt());
							curError.setEndPosition(cp.getPosition()-1);
							curAttr = null;
							curSlice.addError(curError);
							curSlice.addInterior(curError);
							addMode(SlicerMode.ERROR);
							reinvoke = true;
							break;
						// ma znaczenie dla deklaracji XML lub kodu php - gdy poprzedza go <
						case INSIDE_SLICE:
							if(pre_cp.getPosition()==cp.getPosition()-1 && pre_cp.getChar()=='<'){
								curSlice.setOther(true);
							}
							break;
						case ERROR:
							modeList.removeLast();
							regainError(cp, curError);
							pre_cp = getPreviousControlPointFrom(pre_cp);
							reinvoke = true;
						default:
							break;
						}
					} while (reinvoke);
				}
				break;
			case '!':
				if(curSlice!=null && !curSlice.isOther()){
					// ma znaczenie tylko dla deklaracji DTD - gdy poprzedza go <
					if(modeList.getLast().equals(SlicerMode.INSIDE_SLICE)){
						if(pre_cp.getPosition()==cp.getPosition()-1 && pre_cp.getChar()=='<'){
							logger.info("detecting slice of type \"other\"");
							curSlice.setOther(true);
						}
					}
				}
				break;
			default:
				// powinno przełączać w tryb INSIDE_ATTRIBUTE jeśli poprzedni znak to spacja
			}
		}
		return slicesR;
	}
	
	public void findControlPoints(){
		controlPoints = new LinkedList<ControlPoint>();
		char read_ch;
		int pos=0;

		if(savedStream != null){
			while(pos<savedStream.length){
				read_ch = (char)savedStream[pos];
				switch(read_ch){
				case '<':
					controlPoints.add(new LessThanControlPoint(pos));
					break;
				case '>':
					controlPoints.add(new GreaterThanControlPoint(pos));
					break;
				case '\"':
					controlPoints.add(new DoubleQuoteControlPoint(pos));
					break;
				case '\'':
					controlPoints.add(new SingleQuoteControlPoint(pos));
					break;
				case '/':
					controlPoints.add(new SlashSignControlPoint(pos));
					break;
				case '=':
					controlPoints.add(new EqualsSignControlPoint(pos));
					break;
				case ' ':
				case '\t':
				case '\n':
				case '\r':
				case '\f':
					try{
						controlPoints.add(new WhitespaceControlPoint(pos, read_ch));
					} catch (InvalidCharacterException ex){
						logger.error(String.format("%1$s: %2$s at %3$d", ex.getClass().getName(), ex.getMessage(), pos));
					}
					break;
				case '?':
					controlPoints.add(new QuestionMarkControlPoint(pos));
					break;
				case '!':
					controlPoints.add(new ExclamationMarkControlPoint(pos));
					break;
				}
				pos++;
			}
		}
	}

	public ControlPoint getPreviousControlPointFrom(ControlPoint toFind){
		ControlPoint result = null;
		if(toFind!=null){
			ControlPoint cp = null;
			ListIterator<ControlPoint> li = controlPoints.listIterator();
			while(cp!=toFind && li.hasNext()){
				cp = li.next();
			}
			if(cp==toFind && li.hasPrevious()){
				result = li.previous();
			}
		}
		return result;
	}
	
	public void setProcessingHandler(ProcessingHandler handler){
		this.handler = handler;
	}
	
	public ControlPoint registerRegain(int pos, char ch){
		ControlPoint cp = null;
		return cp;
	}

	public ControlPoint regainError(ControlPoint cp, ErrorRepresentation curError){
		logger.info(String.format("regaining error at %1$d on char=%2$d ['%3$s']", cp.getPosition(), (int)cp.getChar(), cp.getChar()));
		curError.setEndPosition(cp.getPosition()-1);
		return cp;
	}
	
	public void addMode(SlicerMode mode){
		modeList.addLast(mode);
	}
	
	public LinkedList<ControlPoint> getControlPointsList() {
		return controlPoints;
	}
	
	public void logReport(){
		for (ControlPoint controlPoint : controlPoints) {
			logger.info(String.format("%1$d = %2$s", controlPoint.getPosition(), controlPoint.getChar()));
		}
	}
	
}
