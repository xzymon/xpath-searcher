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
import com.xzymon.xpath_searcher.gui.stain.handlers.ProcessingHandler;
import com.xzymon.xpath_searcher.gui.stain.handlers.Slice;
import com.xzymon.xpath_searcher.gui.stain.handlers.SliceAttribute;
import com.xzymon.xpath_searcher.gui.stain.handlers.SliceType;
import com.xzymon.xpath_searcher.gui.stain.handlers.SlicerMode;
import com.xzymon.xpath_searcher.gui.stain.handlers.control.ControlPoint;
import com.xzymon.xpath_searcher.gui.stain.handlers.control.DoubleQuoteControlPoint;
import com.xzymon.xpath_searcher.gui.stain.handlers.control.EqualsSignControlPoint;
import com.xzymon.xpath_searcher.gui.stain.handlers.control.ExclamationMarkControlPoint;
import com.xzymon.xpath_searcher.gui.stain.handlers.control.GreaterThanControlPoint;
import com.xzymon.xpath_searcher.gui.stain.handlers.control.LessThanControlPoint;
import com.xzymon.xpath_searcher.gui.stain.handlers.control.NoneControlPoint;
import com.xzymon.xpath_searcher.gui.stain.handlers.control.QuestionMarkControlPoint;
import com.xzymon.xpath_searcher.gui.stain.handlers.control.SingleQuoteControlPoint;
import com.xzymon.xpath_searcher.gui.stain.handlers.control.SlashSignControlPoint;
import com.xzymon.xpath_searcher.gui.stain.handlers.control.WhitespaceControlPoint;

public class Slicer {
	private static final Logger logger = LoggerFactory.getLogger(Slicer.class.getName());
	
	private byte[] savedStream = null;
	private LinkedList<Slice> slist = null;
	private LinkedList<SlicerMode> modeList = null;
	
	private LinkedList<SlicerMode> modeHistory = null;
	private LinkedList<ControlPoint> controlPointsHistory = null;
	
	private LinkedList<ControlPoint> controlPoints;
	
	private boolean slicingError = false;
	private ControlPoint errorPoint = null;
	private ControlPoint regainPoint = null;
	
	private ProcessingHandler handler = null;
	
	public Slicer(InputStream is){
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
		logEventsReport();
		// przeanalizować history i stworzyć bloki względem trybów pracy
		for(Slice slice: slist){
			logger.info(slice.toString());
		}
	}
	
	public List<Slice> slice() throws SlicingException{
		slist = new LinkedList<Slice>();
		modeList = new LinkedList<SlicerMode>();
		// history
		modeHistory = new LinkedList<SlicerMode>();
		controlPointsHistory = new LinkedList<ControlPoint>();
		
		Slice currentSlice = null;
		SliceAttribute currentAttribute = null;
		boolean reinvoke = false;
		
		controlPointsHistory.addLast(new NoneControlPoint(-1));
		addMode(SlicerMode.NONE);
				
		findControlPoints();
		int cplength = controlPoints.size();
		ControlPoint cp = null;
		ControlPoint pre_cp = null;
		// for bo trzeba się odwoływać do poprzednich/następnych elementów listy
		for(int cploop=0; cploop<cplength; cploop++){
			if(modeList.getLast()==SlicerMode.ERROR){
				slicingError = true;
				errorPoint = cp;
				logger.info(String.format("error detected at %1$d on char=%2$d ['%3$s']", cp.getPosition(), (int)cp.getChar(), cp.getChar()));
			}
			pre_cp = cp;
			cp = controlPoints.get(cploop);
			
			modeHistory.addLast(modeList.getLast());
			controlPointsHistory.addLast(cp);
			
			switch(cp.getChar()){
			case '<':
				switch(modeList.getLast()){
				case NONE:
					addMode(SlicerMode.INSIDE_SLICE);
					currentSlice = new Slice();
					slist.add(currentSlice);
					currentSlice.setFirstByteInStreamPosition(cp.getPosition());
					break;
				case INSIDE_SLICE:
					addMode(SlicerMode.ERROR);
					break;
				case INSIDE_DOUBLE_QUOTES:
					// nic
					break;
				case INSIDE_SINGLE_QUOTES:
					// nic
					break;
				case INSIDE_ATTRIBUTE:
					// nic
					break;
				case ERROR:
					// nic
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
						modeList.removeLast();
						currentAttribute.setLength(cp.getPosition()-currentSlice.getFirstByteInStreamPosition()-currentAttribute.getFirstCharOffset());
						currentSlice.addAttribute(currentAttribute);
						currentAttribute = null;
						reinvoke = true;
						break;
					case INSIDE_SLICE:
						if(pre_cp.getChar()=='/' && pre_cp.getPosition()==cp.getPosition()-1){
							currentSlice.setType(SliceType.WITHOUT_CONTENT);
						}
						if(pre_cp.getPosition()<cp.getPosition()-1 && (pre_cp.getChar()=='<' || pre_cp.getChar()=='/')){
							currentSlice.setTagNameStartOffset(pre_cp.getPosition()+1);
							currentSlice.setTagNameLength(cp.getPosition()-pre_cp.getPosition()-1);
						}
						currentSlice.setClosingByteOffset(cp.getPosition()-currentSlice.getFirstByteInStreamPosition());
						currentSlice = null;
						modeList.removeLast();
						break;
					case ERROR:
						modeList.removeLast();
						registerRegain(cp);
						pre_cp = getPreviousControlPointFrom(pre_cp);
						reinvoke = true;
					default:
						break;
					}
				} while (reinvoke);
				break;
			case '\"':
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
					break;
				case INSIDE_SINGLE_QUOTES:
					// nic
					break;
				case INSIDE_ATTRIBUTE:
					if(pre_cp!=null && pre_cp.getChar()=='=' && pre_cp.getPosition()==cp.getPosition()-1){
						addMode(SlicerMode.INSIDE_DOUBLE_QUOTES);
					}
					break;
				case ERROR:
					// nic
					break;
				default:
					break;
				}
				break;
			case '\'':
				switch(modeList.getLast()){
				case NONE:
					addMode(SlicerMode.INSIDE_SINGLE_QUOTES);
					break;
				case INSIDE_SLICE:
					addMode(SlicerMode.INSIDE_SINGLE_QUOTES);
					break;
				case INSIDE_DOUBLE_QUOTES:
					// nic
					break;
				case INSIDE_SINGLE_QUOTES:
					// wycofaj tryb
					modeList.removeLast();
					break;
				case INSIDE_ATTRIBUTE:
					if(pre_cp!=null && pre_cp.getChar()=='=' && pre_cp.getPosition()==cp.getPosition()-1){
						addMode(SlicerMode.INSIDE_DOUBLE_QUOTES);
					}
					break;
				case ERROR:
					// nic
					break;
				default:
					break;
				}
				break;
			case '/':
				do {
					reinvoke = false;
					switch(modeList.getLast()){
					case INSIDE_ATTRIBUTE:
						modeList.removeLast();
						currentAttribute.setLength(cp.getPosition()-currentSlice.getFirstByteInStreamPosition()-currentAttribute.getFirstCharOffset());
						currentSlice.addAttribute(currentAttribute);
						currentAttribute = null;
						reinvoke = true;
						break;
					// sprawdzenie czy to ma jakiekolwiek znaczenie - jedynie w INSIDE_SLICE
					// sprawdzanie tylko wstecz - więc do sprawdzenia tylko jedna opcja
					case INSIDE_SLICE:
						if(pre_cp.getPosition()==cp.getPosition()-1 && pre_cp.getChar()=='<'){
							currentSlice.setType(SliceType.WITH_CONTENT_CLOSING);
						}
						break;
					case ERROR:
						modeList.removeLast();
						registerRegain(cp);
						pre_cp = getPreviousControlPointFrom(pre_cp);
						reinvoke = true;
					default:
						break;
					}
				} while (reinvoke);
				break;
			case '=':
				// powinno mieć znaczenie tylko przy INSIDE_ATTRIBUTE
				// jeśli poprzedni znak na liście jest whitespace i nie jest bezpośrednio poprzedni to  włącz tryb INSIDE_ATTRIBUTE
				if(modeList.getLast().equals(SlicerMode.INSIDE_SLICE)){
					if(pre_cp!=null && pre_cp instanceof WhitespaceControlPoint && pre_cp.getPosition()<cp.getPosition()-1){
						addMode(SlicerMode.INSIDE_ATTRIBUTE);
						currentAttribute = new SliceAttribute();
						currentAttribute.setFirstCharOffset(pre_cp.getPosition()-currentSlice.getFirstByteInStreamPosition());
						currentAttribute.setEqualsSignOffset(cp.getPosition()-pre_cp.getPosition());
					}
				}
				break;
			case ' ':
			case '\t':
			case '\n':
			case '\r':
			case '\f':
				do {
					reinvoke = false;
					switch(modeList.getLast()){
					// jeśli tryb INSIDE_ATTRIBUTE to powinno go wyłączać i konstruować/aktualizować ostatni SliceAttribute
					case INSIDE_ATTRIBUTE:
						modeList.removeLast();
						currentAttribute.setLength(cp.getPosition()-currentSlice.getFirstByteInStreamPosition()-currentAttribute.getFirstCharOffset());
						currentSlice.addAttribute(currentAttribute);
						currentAttribute = null;
						reinvoke = true;
						break;
					case INSIDE_SLICE:
						if(pre_cp.getPosition()<cp.getPosition()-1 && (pre_cp.getChar()=='<' || pre_cp.getChar()=='/')){
							currentSlice.setTagNameStartOffset(pre_cp.getPosition()+1);
							currentSlice.setTagNameLength(cp.getPosition()-pre_cp.getPosition()-1);
						}
						break;
					case ERROR:
						modeList.removeLast();
						registerRegain(cp);
						pre_cp = getPreviousControlPointFrom(pre_cp);
						reinvoke = true;
					default:
						break;
					}
				} while (reinvoke);
				break;
			case '?':
				do {
					reinvoke = false;
					switch(modeList.getLast()){
					// jeśli tryb INSIDE_ATTRIBUTE to powinno go wyłączać i konstruować/aktualizować ostatni SliceAttribute
					case INSIDE_ATTRIBUTE:
						modeList.removeLast();
						currentAttribute.setLength(cp.getPosition()-currentSlice.getFirstByteInStreamPosition()-currentAttribute.getFirstCharOffset());
						currentSlice.addAttribute(currentAttribute);
						currentAttribute = null;
						reinvoke = true;
						break;
					// ma znaczenie dla deklaracji XML lub kodu php - gdy poprzedza go <
					case INSIDE_SLICE:
						if(pre_cp.getPosition()==cp.getPosition()-1 && pre_cp.getChar()=='<'){
							currentSlice.setType(SliceType.OTHER);
						}
						break;
					case ERROR:
						modeList.removeLast();
						registerRegain(cp);
						pre_cp = getPreviousControlPointFrom(pre_cp);
						reinvoke = true;
					default:
						break;
					}
				} while (reinvoke);
				break;
			case '!':
				// ma znaczenie tylko dla deklaracji DTD - gdy poprzedza go <
				if(modeList.getLast().equals(SlicerMode.INSIDE_SLICE)){
					if(pre_cp.getPosition()==cp.getPosition()-1 && pre_cp.getChar()=='<'){
						currentSlice.setType(SliceType.OTHER);
					}
				}
				break;
			default:
				// powinno przełączać w tryb INSIDE_ATTRIBUTE jeśli poprzedni znak to spacja
			}
		}
		modeHistory.addLast(modeList.getLast());
		return slist;
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

	public ControlPoint registerRegain(ControlPoint cp){
		logger.info(String.format("regaining error at %1$d on char=%2$d ['%3$s']", cp.getPosition(), (int)cp.getChar(), cp.getChar()));
		modeHistory.addLast(SlicerMode.REGAIN_ERROR);
		controlPointsHistory.addLast(cp);
		return cp;
	}
	
	public void addMode(SlicerMode mode){
		modeList.addLast(mode);
		//modeHistory.addLast(mode);
	}
	
	public LinkedList<ControlPoint> getControlPointsList() {
		return controlPoints;
	}
	
	public void logReport(){
		for (ControlPoint controlPoint : controlPoints) {
			logger.info(String.format("%1$d = %2$s", controlPoint.getPosition(), controlPoint.getChar()));
		}
	}
	
	public void logEventsReport(){
		int pos = -1;
		int modeLength = modeHistory.size();
		int cpLength = controlPointsHistory.size();
		if(modeLength==cpLength){
			Iterator<SlicerMode> modeIt = modeHistory.iterator();
			Iterator<ControlPoint> cpIt = controlPointsHistory.iterator();
			SlicerMode mode = null;
			ControlPoint controlPoint = null;
			while(modeIt.hasNext() && cpIt.hasNext()){
				pos++;
				mode = modeIt.next();
				controlPoint = cpIt.next();
				logger.info(String.format("pos %1$d: mode: %2$s, controlPoint: pos=%3$d, ch=%4$d[%5$s]", pos, mode.toString(), controlPoint.getPosition(), (int)controlPoint.getChar(), controlPoint.getChar()));
			}
		} else {
			logger.info(String.format("lengths don't match : mode=%1$d, cp=%2$d", modeLength, cpLength));
		}
	}
}