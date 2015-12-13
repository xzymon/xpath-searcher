package com.xzymon.xpath_searcher.core.listener.internal;

/**
 * Punkt kontrolny obudowujący znak >
 * @author Szymon Ignaciuk
 * @see ControlPoint
 */
public class GreaterThanControlPoint extends AbstractControlPoint {

	public GreaterThanControlPoint(int position) {
		super(position);
		this.setChar('>');
	}

}
