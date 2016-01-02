package com.xzymon.xpath_searcher.core.parser;

import java.util.LinkedList;
import java.util.List;

/**
 * Klasa reprezentująca obszar tagu XML lub HTML, zamkniętego wewnątrz < i >.
 * Przykłady takich prawidłowych tagów w wypadku HTMLu (każda linia to oddzielny prawidłowy tag):
 * <pre>
 * {@code
 * <a href="index.html" charset="UTF8">
 * </a>
 * <hr/>
 * }
 * </pre>
 * Poza tym klasa ta może reprezentować obszar "surowego" tekstu zawartego
 * pomiędzy takimi jak powyżej tagami. Poniższy kod zostanie przetworzony 
 * przez parser na trzy instancje tej klasy:
 * <pre>
 * {@code
 * <a href="index.html" charset="UTF8">index</a>
 * }
 * </pre>
 * <ol>
 * <li>&lt;a href="index.html" charset="UTF8"> - tag otwierający,</li>
 * <li>index - tag zawierający "surowy" tekst ({@link #isRaw()} == true),</li>
 * <li>&lt;/a> - tag zamykający ({@link #isClosing()} == true)</li>
 * </ol>
 * @author Szymon Ignaciuk
 */
public class HalfElementRepresentation {
	private int startsAt = -1;
	private int endsAt = -1;
	private int nameStartsAt = -1;
	private int nameEndsAt = -1;
	private int closingSlashAt = -1;
	private boolean other = false;
	private boolean raw = false;
	private List<AttributeRepresentation> attributes = null;
	private List<ErrorRepresentation> errors = null;
	private List<ElementInterior> interiors = null;
	
	public int getStartPosition() {
		return startsAt;
	}
	public void setStartPosition(int pos) {
		this.startsAt = pos;
	}
	public int getEndPosition() {
		return endsAt;
	}
	public void setEndPosition(int pos) {
		this.endsAt = pos;
	}
	public int getNameStartPosition() {
		return nameStartsAt;
	}
	public void setNameStartPosition(int pos) {
		this.nameStartsAt = pos;
	}
	public int getNameEndPosition() {
		return nameEndsAt;
	}
	public void setNameEndPosition(int pos) {
		this.nameEndsAt = pos;
	}
	public int getClosingSlashPosition(){
		return closingSlashAt;
	}
	public void setClosingSlashPosition(int pos){
		this.closingSlashAt = pos;
	}
	/**
	 * Testuje czy jest to tag zawierający zamknięcie (znak '/') na początku,
	 * zaraz po znaku otwierającym '<'.
	 * @return true jeśli jest to tag postaci: &lt;/a>, false w każdym innym wypadku.
	 */
	public boolean isClosing(){
		return (closingSlashAt==startsAt+1)?true:false;
	}
	/**
	 * Testuje czy tag sam się zamyka (tj. na przed ostatniej pozycji zawiera /, jak np. &lthr />.
	 * @return
	 */
	public boolean isSelfClosing(){
		return (closingSlashAt==endsAt-1)?true:false;
	}
	/**
	 * Testuje czy tag jest prawidłowo zakończony, tj. posiada ustawioną pozycję znaku >.
	 * @return
	 */
	public boolean isEnded(){
		return endsAt!=-1;
	}
	/**
	 * Testuje czy jest to tag inny niż normalne tagi HTMl, XML lub "surowe" 
	 * - np. te warunki spełniają tagi &lt;!DOCTYPE html>, &lt;?xml version="1.0"?> 
	 * oraz &lt;!-- -->
	 * @return
	 */
	public boolean isOther(){
		return other;
	}
	
	public void setOther(boolean other){
		this.other = other;
	}
	/**
	 * Testuje czy jest to tag opakowujący surowy tekst - tzn. nie zawierający < i > oraz nazwy tagu
	 * @return
	 */
	public boolean isRaw() {
		return raw;
	}
	public void setRaw(boolean raw) {
		this.raw = raw;
	}
	/**
	 * Testuje czy jest to tag posiadający nazwę, ale nie zawierający znaku 
	 * / <u>przed</u> nazwą tagu.
	 * 
	 * Metoda nie jest odpowiedzialna za potwierdzenie czy istnieje
	 * tag dopełniający - tzn. zamykający.
	 * @return true gdy tag jest:
	 * <ul>
	 * <li>albo pierwszym z obszarów dwuczłonowego tagu, tzn. 
	 * dla dwuczłonowego tagu &lt;a>&lt;/a> warunek ten spełnia &lt;a>,</li>
	 * <li>albo tagiem zawierającym / ale na końcu, np &lt;a/>.</li>
	 * </ul>
	 * w każdym innym przypadku false;
	 */
	public boolean isOpening(){
		return !raw && !other && !isClosing();
	}
	/**
	 * Zwraca listę obiektów reprezentujacych atrybuty zawarte w tym tagu.
	 * @return
	 */
	public List<AttributeRepresentation> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<AttributeRepresentation> attributes) {
		this.attributes = attributes;
	}
	public void addAttribute(AttributeRepresentation attribute){
		if(attributes==null){
			attributes = new LinkedList<AttributeRepresentation>();
		}
		attributes.add(attribute);
	}
	/**
	 * Zwraca listę obiektów reprezentujących błędy zawarte w tym tagu
	 * @return
	 */
	public List<ErrorRepresentation> getErrors() {
		return errors;
	}
	public void setErrors(List<ErrorRepresentation> errors) {
		this.errors = errors;
	}
	public void addError(ErrorRepresentation error){
		if(errors==null){
			errors = new LinkedList<ErrorRepresentation>();
		}
		errors.add(error);
	}
	/**
	 * Zwraca listę elementów wewnętrznych - którymi mogą być obiekty
	 * reprezentujące błędy lub atrybuty
	 * @return
	 */
	public List<ElementInterior> getInterior() {
		return interiors;
	}
	public void setInterior(List<ElementInterior> interiors) {
		this.interiors = interiors;
	}
	public void addInterior(ElementInterior interior){
		if(interiors==null){
			interiors = new LinkedList<ElementInterior>();
		}
		interiors.add(interior);
	}
	
	public String fromSavedChars(char[] chars, int startPos, int endPos){
		int length = endPos-startPos+1;
		return new String(chars, startPos, length);
	}
	
	/**
	 * Pobiera wycinek z chars i zwraca go jako {@link StringBuffer}.
	 * @param chars
	 * @param startPos
	 * @param endPos
	 * @return
	 */
	public StringBuffer fromSavedCharsAsStringBuffer(char[] chars, int startPos, int endPos){
		int length = endPos-startPos+1;
		StringBuffer sb = new StringBuffer();
		return sb.append(chars, startPos, length);
	}
	
	public String getNullSaveTagName(char[] chars) throws UnsupportedOperationException{
		if(nameStartsAt > -1 && nameEndsAt >= nameStartsAt && chars != null && chars.length>nameEndsAt){
			return fromSavedChars(chars, nameStartsAt, nameEndsAt);
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
	public String getTagName(char[] chars) {
		if(nameStartsAt > -1 && nameEndsAt >= nameStartsAt && chars != null && chars.length>nameEndsAt){
			return fromSavedChars(chars, nameStartsAt, nameEndsAt);
		} else {
			return null;
		}
	}
	
	public StringBuffer getTagNameAsStringBuffer(char[] chars) {
		if(nameStartsAt > -1 && nameEndsAt >= nameStartsAt && chars != null && chars.length>nameEndsAt){
			return fromSavedCharsAsStringBuffer(chars, nameStartsAt, nameEndsAt);
		} else {
			return null;
		}
	}
	
	@Override
	public String toString() {
		StringBuffer attrSb = new StringBuffer();
		if(attributes!=null && !attributes.isEmpty()){
			for(AttributeRepresentation attr: getAttributes()){
				if(attrSb.length()!=0){
					attrSb.append(", ");
				}
				attrSb.append(attr);
			}
		} else {
			attrSb.append("null");
		}
		StringBuffer errSb = new StringBuffer();
		if(errors!=null && !errors.isEmpty()){
			for(ErrorRepresentation error: errors){
				if(errSb.length()!=0){
					errSb.append(", ");
				}
				errSb.append(error);
			}
		} else {
			errSb.append("null");
		}
		StringBuffer intSb = new StringBuffer();
		if(interiors!=null && !interiors.isEmpty()){
			for(ElementInterior interior: interiors){
				if(intSb.length()!=0){
					intSb.append(", ");
				}
				if(interior instanceof AttributeRepresentation){
					intSb.append("A");
				}
				if(interior instanceof ErrorRepresentation){
					intSb.append("E");
				}
			}
		} else {
			intSb.append("null");
		}
		return "SliceRepresentation [startsAt=" + startsAt + ", endsAt="
				+ endsAt + ", nameStartsAt=" + nameStartsAt + ", nameEndsAt="
				+ nameEndsAt + ", closingSlashAt=" + closingSlashAt
				+ ", closing=" + isClosing() + ", selfClosing=" + isSelfClosing()
				+ ", raw=" + raw + ", attributes={" + attributes 
				+ "}, errors={" + errSb.toString() + "}, interiors={"
				+ intSb.toString() + "}]";
	}
	
	public StringBuffer stringifyUsingMarkupLanguage(char[] chars){
		StringBuffer sb = null;
		
		if(isRaw() || isOther()){
			sb = fromSavedCharsAsStringBuffer(chars, startsAt, endsAt);
		} else {
			// to jest normalny, nazwany tag
			sb = new StringBuffer();
			
			sb.append('<');
			if(isClosing()){
				sb.append('/');
			}
			StringBuffer nameSb = getTagNameAsStringBuffer(chars);
			if(nameSb!=null){
				sb.append(nameSb);
			}
			
			StringBuffer attrSb = new StringBuffer();
			if(attributes!=null && !attributes.isEmpty()){
				for(AttributeRepresentation attr: getAttributes()){
					attrSb.append(" ");
					attrSb.append(attr.stringifyAsStringBuffer(chars));
				}
			}
			sb.append(attrSb);
			
			if(isSelfClosing()){
				sb.append('/');
			}
			sb.append('>');
		}
		
		return sb;
	}
}
