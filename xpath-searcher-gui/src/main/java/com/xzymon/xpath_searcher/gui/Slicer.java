package com.xzymon.xpath_searcher.gui;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

public class Slicer {
	private byte[] bytes = null;
	private InputStream bs = null;
	private StreamCutter cutter = null;
	private LinkedList<Slice> slist = null;
	private LinkedList<SlicerMode> modeList = null;
	
	public void slice(InputStream is){
		slist = new LinkedList<Slice>();
		modeList = new LinkedList<SlicerMode>();
		Slice currentSlice = null;
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
					pre_cp = cp;
					cp = cplist.get(cploop);
					switch(cp.getChar()){
					case '<':
						switch(modeList.getLast()){
						case NONE:
							modeList.addLast(SlicerMode.INSIDE_SLICE);
							currentSlice = new Slice();
							slist.add(currentSlice);
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
							break;
						case INSIDE_ATTRIBUTE:
							// nic
							break;
						case ERROR:
							// nic
							break;
						}
						break;
					case '/':
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
						break;
					case ' ':
					case '\t':
					case '\n':
					case '\r':
					case '\f':
						// jeśli tryb INSIDE_ATTRIBUTE to powinno go wyłączać i konstruować/aktualizować ostatni SliceAttribute
						break;
					case '!':
						// ma znaczenie tylko dla deklaracji DTD - gdy poprzedza go <
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
	}
}
