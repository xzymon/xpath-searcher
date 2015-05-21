package com.xzymon.xpath_searcher.core.converter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.xzymon.xpath_searcher.core.decomposition.Slicer;
import com.xzymon.xpath_searcher.core.exception.SlicingException;
import com.xzymon.xpath_searcher.core.listener.OtherTagRemoveSlicingListener;

public class HTMLStreamConverter {
	private Slicer slicer;
	private byte[] resultStream;
	
	public HTMLStreamConverter(InputStream is) throws IOException, SlicingException{
		int avail = is.available();
		if(avail>0){
			byte[] helpArray = new byte[avail];
			slicer = new Slicer(is);
			OtherTagRemoveSlicingListener removeListener = new OtherTagRemoveSlicingListener();
			removeListener.setData(slicer.getSavedChars());
			slicer.addSlicingListener(removeListener);
			slicer.invokeListeners();
			String filtered = removeListener.getCollectedString();
			resultStream = filtered.getBytes();
		}
	}
	
	public InputStream getConvertedStream(){
		return new ByteArrayInputStream(resultStream);
	}
}
