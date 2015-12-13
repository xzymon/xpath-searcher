package com.xzymon.xpath_searcher.core.listener.internal;

/**
 * Punkt kontrolny obudowujący znak '
 * @author Szymon Ignaciuk
 * @see ControlPoint
 */
public class SingleQuoteControlPoint extends AbstractControlPoint {

	public SingleQuoteControlPoint(int position) {
		super(position);
		this.setChar('\'');
	}

}
