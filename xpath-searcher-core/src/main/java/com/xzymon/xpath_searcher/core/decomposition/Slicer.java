package com.xzymon.xpath_searcher.core.decomposition;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xzymon.xpath_searcher.core.dom.SlicedNode;
import com.xzymon.xpath_searcher.core.exception.BuildingNodeStructureException;
import com.xzymon.xpath_searcher.core.exception.InvalidCharacterException;
import com.xzymon.xpath_searcher.core.exception.SlicingException;
import com.xzymon.xpath_searcher.core.listener.SlicingListener;
import com.xzymon.xpath_searcher.core.listener.internal.ControlPoint;
import com.xzymon.xpath_searcher.core.listener.internal.DoubleQuoteControlPoint;
import com.xzymon.xpath_searcher.core.listener.internal.EqualsSignControlPoint;
import com.xzymon.xpath_searcher.core.listener.internal.ExclamationMarkControlPoint;
import com.xzymon.xpath_searcher.core.listener.internal.GreaterThanControlPoint;
import com.xzymon.xpath_searcher.core.listener.internal.LessThanControlPoint;
import com.xzymon.xpath_searcher.core.listener.internal.NoneControlPoint;
import com.xzymon.xpath_searcher.core.listener.internal.QuestionMarkControlPoint;
import com.xzymon.xpath_searcher.core.listener.internal.SingleQuoteControlPoint;
import com.xzymon.xpath_searcher.core.listener.internal.SlashSignControlPoint;
import com.xzymon.xpath_searcher.core.listener.internal.SlicerMode;
import com.xzymon.xpath_searcher.core.listener.internal.WhitespaceControlPoint;
import com.xzymon.xpath_searcher.core.parsing.AttributeRepresentation;
import com.xzymon.xpath_searcher.core.parsing.ErrorRepresentation;
import com.xzymon.xpath_searcher.core.parsing.SliceInterior;
import com.xzymon.xpath_searcher.core.parsing.SliceRepresentation;

public class Slicer {
	private static final Logger logger = LoggerFactory.getLogger(Slicer.class.getName());
	
	private char[] savedChars = null;
	private LinkedList<SlicerMode> modeList = null;
	private LinkedList<ControlPoint> controlPoints = null;
	private LinkedList<SliceRepresentation> slicesR = null;
	
	private List<SlicingListener> slicingListeners;
	
	public Slicer(InputStream is) throws SlicingException{
		try{
			int avail = is.available();
			if(avail>0){
				byte[] savedStream = new byte[avail];
				is.read(savedStream);
				ByteArrayInputStream bais = new ByteArrayInputStream(savedStream);
				InputStreamReader reader = new InputStreamReader(bais);
				char[] charBuf = new char[savedStream.length];
				int read = reader.read(charBuf);
				savedChars = new char[read];
				System.arraycopy(charBuf, 0, savedChars, 0, read);
				slice();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	public Slicer(byte[] savedStream) throws IOException, SlicingException{
		ByteArrayInputStream bais = new ByteArrayInputStream(savedStream);
		InputStreamReader reader = new InputStreamReader(bais);
		char[] charBuf = new char[savedStream.length];
		int read = reader.read(charBuf);
		savedChars = new char[read];
		System.arraycopy(charBuf, 0, savedChars, 0, read);
		slice();
	}
	
	public Slicer(char[] chars) throws SlicingException{
		this.savedChars = chars;
		slice();
	}
	
	private void slice() throws SlicingException{
		findControlPoints();
		getSlices();
	}
	
	public void invokeListeners(){
		AttributeRepresentation attr = null;
		ErrorRepresentation error = null;
		
		int gapStart=-1, gapEnd=-1;
		int slashPos;
		
		for(SliceRepresentation sliceR: slicesR){
			logger.debug(sliceR.toString());
			
			//TODO: here
			if(sliceR.isRaw()){
				for(SlicingListener sl : slicingListeners){
					sl.rawText(sliceR.getStartPosition(), sliceR.getEndPosition());
				}
			} else if(sliceR.isOther()){
				for(SlicingListener sl : slicingListeners){
					sl.otherTag(sliceR.getStartPosition(), sliceR.getEndPosition());
				}
			} else {
				for(SlicingListener sl : slicingListeners){
					sl.lessThanStartChar(sliceR.getStartPosition());
				}
				if(sliceR.isClosing()){
					for(SlicingListener sl : slicingListeners){
						sl.closingSlash(sliceR.getClosingSlashPosition());
					}
				}
				for(SlicingListener sl : slicingListeners){
					sl.tagName(sliceR.getNameStartPosition(), sliceR.getNameEndPosition());
				}
				gapStart = sliceR.getNameEndPosition()+1;
				if(sliceR.getInterior()!=null){
					for(SliceInterior sInt : sliceR.getInterior()){
						gapEnd = sInt.getStartPosition()-1;
						if(gapEnd>=gapStart){
							for(SlicingListener sl : slicingListeners){
								sl.tagGap(gapStart, gapEnd);
							}
						}
						if(sInt instanceof AttributeRepresentation){
							attr = (AttributeRepresentation) sInt;
							for(SlicingListener sl : slicingListeners){
								sl.attributeName(attr.getStartsAt(), attr.getNameEndsAt());
								sl.attributeEqualsSign(attr.getEqualsSignAt());
								sl.attributeValue(attr.getStartQuotationMarkAt(), attr.getEndQuotationMarkAt());
							}
						}
						if(sInt instanceof ErrorRepresentation){
							error = (ErrorRepresentation) sInt;
							for(SlicingListener sl : slicingListeners){
								sl.error(error.getStartPosition(), error.getEndPosition());
							}
						}
						gapStart = sInt.getEndPosition()+1;
					}
				}
				//trzeba obsługiwać ewentualną przerwę przed znakiem/znakami kończącym 
				if(sliceR.isSelfClosing()){
					slashPos = sliceR.getClosingSlashPosition();
					if(slashPos>gapStart){
						for(SlicingListener sl : slicingListeners){
							sl.tagGap(gapStart, slashPos-1);
						}
						gapStart = slashPos+1;
					}
					for(SlicingListener sl : slicingListeners){
						sl.closingSlash(sliceR.getClosingSlashPosition());
					}
				}
				for(SlicingListener sl : slicingListeners){
					sl.greaterThanEndingChar(sliceR.getEndPosition());
				}
			}
		}
	}
	
	public SlicedNode buildStructure() throws BuildingNodeStructureException{
		return SlicedNode.buildStructure(slicesR, savedChars);
	}
	
	private List<SliceRepresentation> getSlices() throws SlicingException{
		modeList = new LinkedList<SlicerMode>();
		
		SliceRepresentation curSlice = null;
		AttributeRepresentation curAttr = null;
		ErrorRepresentation curError = null;
		slicesR = new LinkedList<SliceRepresentation>();
		
		boolean reinvoke = false;
		boolean dropAttr = false;
		
		modeList.addLast(SlicerMode.NONE);
				
		//findControlPoints();
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
			
			logger.debug(String.format("curSlice is %1$s, mode is %2$s, pos=%3$d, ch=[%4$s]",
					curSlice==null?"null":"not null", modeList.getLast().toString(), cp.getPosition(), cp.getChar()));
			
			switch(cp.getChar()){
			case '<':
				switch(modeList.getLast()){
				case NONE:
					insertRawSlice(cp);
					curSlice = new SliceRepresentation();
					curSlice.setStartPosition(cp.getPosition());
					slicesR.addLast(curSlice);
					modeList.addLast(SlicerMode.INSIDE_SLICE);
					break;
				case INSIDE_SLICE:
					curError = new ErrorRepresentation();
					curError.setStartPosition(cp.getPosition());
					modeList.addLast(SlicerMode.ERROR);
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
							curSlice.setNameEndPosition(cp.getPosition()-1);
							curSlice.setEndPosition(cp.getPosition());
						}
						curSlice.setEndPosition(cp.getPosition());
						curSlice = null;
						modeList.removeLast();
						break;
					case ERROR:
						modeList.removeLast();
						regainError(cp, curError, curSlice);
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
							//modeList.addLast(SlicerMode.INSIDE_DOUBLE_QUOTES);
							break;
						case INSIDE_SLICE:
							modeList.addLast(SlicerMode.INSIDE_DOUBLE_QUOTES);
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
									modeList.addLast(SlicerMode.INSIDE_DOUBLE_QUOTES);
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
							//modeList.addLast(SlicerMode.INSIDE_SINGLE_QUOTES);
							break;
						case INSIDE_SLICE:
							modeList.addLast(SlicerMode.INSIDE_SINGLE_QUOTES);
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
									modeList.addLast(SlicerMode.INSIDE_DOUBLE_QUOTES);
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
							regainError(cp, curError, curSlice);
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
							modeList.addLast(SlicerMode.INSIDE_ATTRIBUTE);
							curAttr = new AttributeRepresentation();
							curAttr.setStartsAt(pre_cp.getPosition()+1);
							curAttr.setNameEndsAt(cp.getPosition()-1);
							curAttr.setName(new String(savedChars, curAttr.getStartsAt(), curAttr.getNameEndsAt()-curAttr.getStartsAt()+1));
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
							logger.info("Attribute converted to ERROR");
							curSlice.addInterior(curError);
							reinvoke = true;
							break;
						case INSIDE_SLICE:
							if(pre_cp.getPosition()<cp.getPosition()-1 && ((pre_cp.getChar()=='<' /*&& curSlice.getStartPosition()!=pre_cp.getPosition()*/) || pre_cp.getChar()=='/')){
								curSlice.setNameStartPosition(pre_cp.getPosition()+1);
								curSlice.setNameEndPosition(cp.getPosition()-1);
							}
							if(pre_cp.getPosition()<cp.getPosition()-1 && 
									(pre_cp.getChar()==' ' || pre_cp.getChar()=='\t' || pre_cp.getChar()=='\n' 
									|| pre_cp.getChar()=='\r' || pre_cp.getChar()=='\f' )){
								curError = new ErrorRepresentation();
								curError.setStartPosition(pre_cp.getPosition()+1);
								curError.setEndPosition(cp.getPosition()-1);
								logger.info(String.format("ERROR: startPos=%1$d, endPos=%2$d", curError.getStartPosition(), curError.getEndPosition()));
								curSlice.addError(curError);
								curSlice.addInterior(curError);
							}
							if(pre_cp.getPosition()<cp.getPosition()-1 && (pre_cp.getChar()=='<' && curSlice.getStartPosition()!=pre_cp.getPosition())){
								curError = new ErrorRepresentation();
								curError.setStartPosition(pre_cp.getPosition()+1);
								curError.setEndPosition(cp.getPosition()-1);
								logger.info(String.format("ERROR: startPos=%1$d, endPos=%2$d", curError.getStartPosition(), curError.getEndPosition()));
								curSlice.addError(curError);
								curSlice.addInterior(curError);
							}
							break;
						case ERROR:
							modeList.removeLast();
							regainError(cp, curError, curSlice);
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
							modeList.addLast(SlicerMode.ERROR);
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
							regainError(cp, curError, curSlice);
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
		// + ewentualny zamykający rawSlice
		if(savedChars.length>0){
			cp = new NoneControlPoint(savedChars.length-1);
			insertRawSlice(cp);
		}
		return slicesR;
	}
	
	private void findControlPoints(){
		controlPoints = new LinkedList<ControlPoint>();
		char read_ch;
		int pos=0;

		if(savedChars != null){
			while(pos<savedChars.length){
				read_ch = savedChars[pos];
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

	private void insertRawSlice(ControlPoint cp){
		// justAfterEnding is after previous Slice
		int justAfterEnding = 0;
		// justBeforeBegining is before this Slice
		int justBeforeBegining = -1;
		SliceRepresentation rawSlice = null;
		
		justBeforeBegining = cp.getPosition()-1;
		if(justAfterEnding<=justBeforeBegining && justBeforeBegining>-1){
			if(slicesR.size()>0){
				justAfterEnding=slicesR.getLast().getEndPosition()+1;
			} 
			if(justBeforeBegining>=justAfterEnding){
				rawSlice = new SliceRepresentation();
				rawSlice.setRaw(true);
				rawSlice.setStartPosition(justAfterEnding);
				rawSlice.setEndPosition(justBeforeBegining);
				slicesR.addLast(rawSlice);
			}
		}
	}
	
	public char[] getSavedChars() {
		return savedChars;
	}

	public void setSavedChars(char[] chars) {
		try {
			this.savedChars = chars;
			slice();
		} catch (SlicingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private ControlPoint getPreviousControlPointFrom(ControlPoint toFind){
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

	public List<SlicingListener> getSlicingListeners(){
		return this.slicingListeners;
	}
	
	public void addSlicingListener(SlicingListener listener){
		if(slicingListeners==null){
			slicingListeners = new LinkedList<SlicingListener>();
		}
		if(!slicingListeners.contains(listener)){
			slicingListeners.add(listener);
		}
	}
	
	public void removeSlicingListener(SlicingListener listener){
		if(slicingListeners!=null){
			slicingListeners.remove(listener);
		}
	}
	
	private ControlPoint regainError(ControlPoint cp, ErrorRepresentation curError, SliceRepresentation curSlice){
		logger.info(String.format("regaining error at %1$d on char=%2$d ['%3$s']", cp.getPosition(), (int)cp.getChar(), cp.getChar()));
		curError.setEndPosition(cp.getPosition()-1);
		curSlice.addError(curError);
		curSlice.addInterior(curError);
		return cp;
	}
	
	public void logReport(){
		for (ControlPoint controlPoint : controlPoints) {
			logger.info(String.format("%1$d = %2$s", controlPoint.getPosition(), controlPoint.getChar()));
		}
	}
	
}