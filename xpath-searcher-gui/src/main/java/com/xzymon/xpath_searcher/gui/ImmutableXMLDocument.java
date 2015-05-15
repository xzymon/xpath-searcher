package com.xzymon.xpath_searcher.gui;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.DocumentFilter;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImmutableXMLDocument extends DefaultStyledDocument implements StyledDocument{
	private static final long serialVersionUID = -5238944309822808938L;
	private static final Logger logger = LoggerFactory.getLogger(ImmutableXMLDocument.class.getName());
	
	private boolean allowMutability;
	private ImmutableDocumentFilter filter = new ImmutableDocumentFilter();
	
	public ImmutableXMLDocument() {
		super();
		init();
	}

	public ImmutableXMLDocument(Content c, StyleContext styles) {
		super(c, styles);
		init();
	}

	public ImmutableXMLDocument(StyleContext styles) {
		super(styles);
		init();
	}
	
	private void init(){
		this.setDocumentFilter(filter);
	}

	public void immutable(){
		this.allowMutability = false;
	}
	
	public void mutable(){
		this.allowMutability = true;
	}

	class ImmutableDocumentFilter extends DocumentFilter{
		public ImmutableDocumentFilter(){
		}

		@Override
		public void remove(FilterBypass fb, int offset, int length)
				throws BadLocationException {
			if(allowMutability){
				logger.debug(String.format("remove(fb, %1$d, %2$d) operation allowed", offset, length) );
				super.remove(fb, offset, length);
			} else {
				logger.debug(String.format("remove(fb, %1$d, %2$d) operation rejected", offset, length) );
			}
		}

		@Override
		public void insertString(FilterBypass fb, int offset, String string,
				AttributeSet attr) throws BadLocationException {
			if(allowMutability){
				logger.debug(String.format("insertString(fb, %1$d, \"%2$s\", attr) operation allowed", offset, string) );
				super.insertString(fb, offset, string, attr);
			} else {
				logger.debug(String.format("insertString(fb, %1$d, \"%2$s\", attr) operation rejected", offset, string) );
			}
		}

		@Override
		public void replace(FilterBypass fb, int offset, int length,
				String text, AttributeSet attrs) throws BadLocationException {
			if(allowMutability){
				logger.debug(String.format("replace(fb, %1$d, %2$d, \"%3$s\", attrs) operation allowed", offset, length, text) );
				super.replace(fb, offset, length, text, attrs);
			} else {
				logger.debug(String.format("replace(fb, %1$d, %2$d, \"%3$s\", attrs) operation rejected", offset, length, text) );
			}
		}
		
	}

	
}
