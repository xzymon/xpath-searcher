package com.xzymon.xpath_searcher.core.listener.internal;

/**
 * Punkt kontrolny obudowujący znak <
 * @author Szymon Ignaciuk
 * @see ControlPoint
 */
public class LessThanControlPoint extends AbstractControlPoint {

	public LessThanControlPoint(int position) {
		super(position);
		this.setChar('<');
	}

}
