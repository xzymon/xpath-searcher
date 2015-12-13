package com.xzymon.xpath_searcher.core.listener.internal;

/**
 * Punkt kontrolny obudowujący znak =
 * @author Szymon Ignaciuk
 * @see ControlPoint
 */
public class EqualsSignControlPoint extends AbstractControlPoint {

	public EqualsSignControlPoint(int position) {
		super(position);
		this.setChar('=');
	}

}
