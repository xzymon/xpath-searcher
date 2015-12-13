package com.xzymon.xpath_searcher.core.listener.internal;

/**
 * Punkt kontrolny obudowujący znak ?
 * @author Szymon Ignaciuk
 * @see ControlPoint
 */
public class QuestionMarkControlPoint extends AbstractControlPoint {

	public QuestionMarkControlPoint(int position) {
		super(position);
		setChar('?');
	}

}
