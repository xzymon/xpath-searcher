package com.xzymon.xpath_searcher.gui;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slicer {
	private static final Logger logger = LoggerFactory.getLogger(Slicer.class.getName());
	
	private byte[] bytes = null;
	private InputStream bs = null;
	private StreamCutter cutter = null;
	private LinkedList<Slice> slist = null;
	private LinkedList<SlicerMode> modeList = null;
	
	public List<Slice> slice(InputStream is){
		slist = new LinkedList<Slice>();
		modeList = new LinkedList<SlicerMode>();
		Slice currentSlice = null;
		SliceAttribute currentAttribute = null;
		try{
			int avail = is.available();
			if(avail>0){
				bytes = new byte[avail];
				is.read(bytes);
				bs = new ByteArrayInputStream(bytes);
				cutter = new StreamCutter(bs, false);
				modeList.addLast(SlicerMode.NONE);
				
				LinkedList<ControlPoint> cplist = cutter.getControlPointsList();
				int cplength = cplist.size();
				ControlPoint cp = null;
				ControlPoint pre_cp = null;
				// for bo trzeba się odwoływać do poprzednich/następnych elementów listy
				for(int cploop=0; cploop<cplength; cploop++){
					for(SlicerMode mode: modeList){
						logger.info(mode.toString());
					}
					pre_cp = cp;
					cp = cplist.get(cploop);
					switch(cp.getChar()){
					case '<':
						switch(modeList.getLast()){
						case NONE:
							modeList.addLast(SlicerMode.INSIDE_SLICE);
							currentSlice = new Slice();
							slist.add(currentSlice);
							currentSlice.setFirstByteInStreamPosition(cp.getPosition());
							break;
						case INSIDE_SLICE:
							modeList.addLast(SlicerMode.ERROR);
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
						}
						break;
					case '>':
						if(modeList.getLast().equals(SlicerMode.INSIDE_ATTRIBUTE)){
							modeList.removeLast();
							currentAttribute.setLength(cp.getPosition()-currentSlice.getFirstByteInStreamPosition()-currentAttribute.getFirstCharOffset());
							currentSlice.addAttribute(currentAttribute);
							currentAttribute = null;
						}
						if(modeList.getLast().equals(SlicerMode.INSIDE_SLICE)){
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
						}
						break;
					case '\"':
						switch(modeList.getLast()){
						case NONE:
							modeList.addLast(SlicerMode.INSIDE_DOUBLE_QUOTES);
							break;
						case INSIDE_SLICE:
							modeList.addLast(SlicerMode.INSIDE_DOUBLE_QUOTES);
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
								modeList.addLast(SlicerMode.INSIDE_DOUBLE_QUOTES);
							}
							break;
						case ERROR:
							// nic
							break;
						}
						break;
					case '\'':
						switch(modeList.getLast()){
						case NONE:
							modeList.addLast(SlicerMode.INSIDE_SINGLE_QUOTES);
							break;
						case INSIDE_SLICE:
							modeList.addLast(SlicerMode.INSIDE_SINGLE_QUOTES);
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
								modeList.addLast(SlicerMode.INSIDE_DOUBLE_QUOTES);
							}
							break;
						case ERROR:
							// nic
							break;
						}
						break;
					case '/':
						if(modeList.getLast().equals(SlicerMode.INSIDE_ATTRIBUTE)){
							modeList.removeLast();
							currentAttribute.setLength(cp.getPosition()-currentSlice.getFirstByteInStreamPosition()-currentAttribute.getFirstCharOffset());
							currentSlice.addAttribute(currentAttribute);
							currentAttribute = null;
						}
						// sprawdzenie czy to ma jakiekolwiek znaczenie - jedynie w INSIDE_SLICE
						// sprawdzanie tylko wstecz - więc do sprawdzenia tylko jedna opcja
						if(modeList.getLast().equals(SlicerMode.INSIDE_SLICE)){
							if(pre_cp!=null){
								if(pre_cp.getPosition()==cp.getPosition()-1 && pre_cp.getChar()=='<'){
									currentSlice.setType(SliceType.WITH_CONTENT_CLOSING);
								}
							}
						}
						break;
					case '=':
						// powinno mieć znaczenie tylko przy INSIDE_ATTRIBUTE
						// jeśli poprzedni znak na liście jest whitespace i nie jest bezpośrednio poprzedni to  włącz tryb INSIDE_ATTRIBUTE
						if(modeList.getLast().equals(SlicerMode.INSIDE_SLICE)){
							if(pre_cp!=null && pre_cp instanceof WhitespaceControlPoint && pre_cp.getPosition()<cp.getPosition()-1){
								modeList.addLast(SlicerMode.INSIDE_ATTRIBUTE);
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
						if(modeList.getLast().equals(SlicerMode.INSIDE_SLICE)){
							if(pre_cp.getPosition()<cp.getPosition()-1 && (pre_cp.getChar()=='<' || pre_cp.getChar()=='/')){
								currentSlice.setTagNameStartOffset(pre_cp.getPosition()+1);
								currentSlice.setTagNameLength(cp.getPosition()-pre_cp.getPosition()-1);
							}
						}
						// jeśli tryb INSIDE_ATTRIBUTE to powinno go wyłączać i konstruować/aktualizować ostatni SliceAttribute
						if(modeList.getLast().equals(SlicerMode.INSIDE_ATTRIBUTE)){
							modeList.removeLast();
							currentAttribute.setLength(cp.getPosition()-currentSlice.getFirstByteInStreamPosition()-currentAttribute.getFirstCharOffset());
							currentSlice.addAttribute(currentAttribute);
							currentAttribute = null;
						}
						break;
					case '?':
						// ma znaczenie dla deklaracji XML lub kodu php - gdy poprzedza go <
						if(modeList.getLast().equals(SlicerMode.INSIDE_SLICE)){
							if(pre_cp.getPosition()==cp.getPosition()-1 && pre_cp.getChar()=='<'){
								currentSlice.setType(SliceType.OTHER);
							}
						}
						// jeśli tryb INSIDE_ATTRIBUTE to powinno go wyłączać i konstruować/aktualizować ostatni SliceAttribute
						if(modeList.getLast().equals(SlicerMode.INSIDE_ATTRIBUTE)){
							modeList.removeLast();
							currentAttribute.setLength(cp.getPosition()-currentSlice.getFirstByteInStreamPosition()-currentAttribute.getFirstCharOffset());
							currentSlice.addAttribute(currentAttribute);
							currentAttribute = null;
						}
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
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(bs!=null){
				try{
					bs.close();
				} catch(IOException ex){
					ex.printStackTrace();
				}
			}
		}
		return slist;
	}
}
