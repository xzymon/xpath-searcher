package com.xzymon.xpath_searcher.core.listener.internal;

/**
 * Punkt kontrolny obudowujący znak !
 * @author Szymon Ignaciuk
 * @see ControlPoint
 */
public class ExclamationMarkControlPoint extends AbstractControlPoint {

	public ExclamationMarkControlPoint(int position) {
		super(position);
		setChar('!');
	}

}
