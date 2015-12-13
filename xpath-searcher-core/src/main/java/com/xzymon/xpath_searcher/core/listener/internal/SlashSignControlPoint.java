package com.xzymon.xpath_searcher.core.listener.internal;

/**
 * Punkt kontrolny obudowujący znak /
 * @author Szymon Ignaciuk
 * @see ControlPoint
 */
public class SlashSignControlPoint extends AbstractControlPoint {

	public SlashSignControlPoint(int position) {
		super(position);
		this.setChar('/');
	}

}
