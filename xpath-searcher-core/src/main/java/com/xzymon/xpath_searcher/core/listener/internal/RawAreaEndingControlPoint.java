package com.xzymon.xpath_searcher.core.listener.internal;

/**
 * Punkt kontrolny obudowujący dowolny znak inny niż którykolwiek ze znaków
 * istotnych dla parsera. Służy do reprezentowania ostatniego znaku 
 * występującego w obszarze nie zawierającym się między znakami < i >.
 * @author Szymon Ignaciuk
 * @see ControlPoint
 */
public class RawAreaEndingControlPoint extends AbstractControlPoint {

	public RawAreaEndingControlPoint(int position) {
		super(position);
	}

}
