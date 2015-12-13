package com.xzymon.xpath_searcher.core.listener.internal;

/**
 * Punkt kontrolny obudowujący znak "
 * @author Szymon Ignaciuk
 * @see ControlPoint
 */
public class DoubleQuoteControlPoint extends AbstractControlPoint {

	public DoubleQuoteControlPoint(int position) {
		super(position);
		this.setChar('\"');
	}

}
