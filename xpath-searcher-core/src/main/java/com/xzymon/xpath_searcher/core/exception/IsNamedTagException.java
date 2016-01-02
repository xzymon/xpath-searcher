package com.xzymon.xpath_searcher.core.exception;

import com.xzymon.xpath_searcher.core.parser.HalfElementRepresentation;

/**
 * Exception przeznaczony do rzucania w sytuacjach gdy oczekiwano przekazania
 * tagu {@link HalfElementRepresentation} dla którego metoda
 * {@link HalfElementRepresentation#isRaw()} == true lub
 * {@link HalfElementRepresentation#isOther()} == true, natomiast przekazano tag
 * który nie spełnia żadnego z tych warunków. 
 * @author Szymon Ignaciuk
 *
 */
public class IsNamedTagException extends BuildingDOMException {
	private static final long serialVersionUID = 1339738092451263839L;

	public IsNamedTagException(String message) {
		super(message);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
