package com.xzymon.xpath_searcher.core.parser;

/**
 * Klasa reprezentująca obszar atrybutu znajdującego się wewnątrz tagu
 * XML lub HTML. Przykładowo dla tagu HTML:
 * <pre>
 * {@code
 * <a href="index.html" charset="UTF8">
 * }
 * </pre>
 * parser stworzy dwie instancje tej klasy do reprezentowania
 * dwóch obszarów:
 * <ol>
 * <li>href="index.html"</li>
 * <li>charset="UTF8"</li>
 * </ol> 
 * @author Szymon Ignaciuk
 */
public class AttributeRepresentation implements ElementInterior {
	private HalfElementRepresentation owner;
	private String name = null;
	private int startsAt = -1;
	private int nameEndsAt = -1;
	private int equalsSignPos = -1;
	private int startQuotationMarkAt = -1;
	private int endQuotationMarkAt = -1;
	private boolean hasValue = false;
	private boolean singleQuoted = false;
	private boolean doubleQuoted = false;
	
	public int getStartPosition() {
		return startsAt;
	}

	public int getEndPosition() {
		return endQuotationMarkAt;
	}
	
	/**
	 * Służy do testowania czy instancja ma przypisanego właściciela 
	 * - tzn. obiekt typu {@link HalfElementRepresentation}. 
	 * @return true jeśli przypisano właściciela, w przeciwnym wypadku false
	 */
	public boolean hasOwner(){
		return owner!=null;
	}
	
	/**
	 * Zwraca pozycję (w przetwarzanym przez parser tekście) na której
	 * znajduje się znak < rozpoczynający {@link HalfElementRepresentation} 
	 * będącego właścicielem tej instancji 
	 * @return wartość 0+ jeżeli instancja posiada właściciela, w przeciwnym wypadku -1.
	 */
	public int getOwnerPosition() {
		if(hasOwner()){
			return owner.getStartPosition();
		}
		return -1;
	}
	
	/**
	 * Setter właściciela tej instacji.
	 * @param slice
	 */
	public void setOwner(HalfElementRepresentation slice) {
		this.owner = slice;
	}
	public int getStartsAt() {
		return startsAt;
	}
	public void setStartsAt(int startsAt) {
		this.startsAt = startsAt;
	}
	
	/**
	 * Zwraca pozycję na której znajduje się ostatni znak tworzący nazwę atrybutu
	 * @return wartość większa lub równa {@link #getStartPosition()}
	 */
	public int getNameEndsAt() {
		return nameEndsAt;
	}
	/**
	 * Setter wartości pozycji ostatniego znaku tworzącego nazwę atrybutu.
	 * @param nameEndsAt
	 */
	public void setNameEndsAt(int nameEndsAt) {
		this.nameEndsAt = nameEndsAt;
	}
	/**
	 * Zwraca pozycję znaku =. 
	 * @return pozycja znaku =, zawsze większa niż wartość zwrócona przez {@link #getNameEndsAt()}
	 */
	public int getEqualsSignPos() {
		return equalsSignPos;
	}
	/**
	 * Setter pozycji znaku =.
	 * @param equalsSignAt
	 */
	public void setEqualsSignPos(int equalsSignAt) {
		this.equalsSignPos = equalsSignAt;
	}
	/**
	 * Zwraca pozycję znaku " lub ', rozpoczynającego ciąg stanowiący wartość atrybutu.
	 * @return pozycja znaku " lub ', zawsze większa niż wartość zwracana przez {@link #getEqualsSignPos()}
	 */
	public int getStartQuotationMarkAt() {
		return startQuotationMarkAt;
	}
	/**
	 * Setter pozycji znaku " lub ', rozpoczynającego wartość atrybutu.
	 * @param startQuotationMarkAt
	 */
	public void setStartQuotationMarkAt(int startQuotationMarkAt) {
		this.startQuotationMarkAt = startQuotationMarkAt;
	}
	/**
	 * Zwraca pozycję znaku zamykającego wartość atrybutu, tj.
	 * pozycję ponownego wystąpienia znaku otwierającego wartość atrybutu.
	 * @return wartość większa niż zwrócona przez {@link #getStartQuotationMarkAt()}
	 */
	public int getEndQuotationMarkAt() {
		return endQuotationMarkAt;
	}
	/**
	 * Setter pozycji znaku zamykającego wartość atrybutu
	 * @param endQuotationMarkAt
	 */
	public void setEndQuotationMarkAt(int endQuotationMarkAt) {
		this.endQuotationMarkAt = endQuotationMarkAt;
	}
	/**
	 * Zwraca wynik testu polegającego na ocenie czy atrybut posiada ustawione 
	 * wartości pozycji obu apostrofów (będących znakami albo " albo '). 
	 * @return true w wypadku prawidłowego ustawienia, w przypadku przeciwnym false.
	 */
	public boolean hasValue() {
		return (startQuotationMarkAt > 0 && startQuotationMarkAt < endQuotationMarkAt);
	}
	/**
	 * Testuje czy wartość argumentu jest podana wewnątrz ' '.
	 * @return
	 */
	public boolean isSingleQuoted() {
		return singleQuoted;
	}
	public void setSingleQuoted(boolean singleQuoted) {
		this.singleQuoted = singleQuoted;
	}
	/**
	 * Testuje czy wartość argumentu jes podana wewnątrz " ".
	 * @return
	 */
	public boolean isDoubleQuoted() {
		return doubleQuoted;
	}
	public void setDoubleQuoted(boolean doubleQuoted) {
		this.doubleQuoted = doubleQuoted;
	}
	/**
	 * Zwraca różnicę pomiędzy położeniem znaku < (rozpoczynającego obszar
	 * właściciela atrybutu) a pierwszym znakiem nazwy atrybutu.
	 * @return
	 */
	public int parentRelativeOffset() {
		return startsAt - getOwnerPosition();
	}
	/**
	 * Zwraca różnicę pomiędzy pierwszym znakiem nazwy atrybutu a pozycją znaku =.
	 * @return
	 */
	public int equalsSignRelativePosition() {
		return equalsSignPos - startsAt;
	}
	/**
	 * Zwraca długość całego obszaru tworzącego atrybut
	 * @return
	 */
	public int length() {
		if(hasValue){
			return endQuotationMarkAt - startsAt + 1;
		}
		return nameEndsAt - startsAt + 1;
	}
	/**
	 * Testuje czy atrybut jest prawidłowo zbudowany, jt. czy względne pozycje
	 * odpowiednich elementów tworzących atrybut są prawidłowe.
	 * @return
	 */
	public boolean isValid(){
		if(startsAt > -1 && nameEndsAt > -1 && equalsSignPos > -1 && startQuotationMarkAt > -1 && endQuotationMarkAt > -1){
			if(nameEndsAt >= startsAt && equalsSignPos==nameEndsAt+1 && equalsSignPos==startQuotationMarkAt-1 && startQuotationMarkAt<endQuotationMarkAt){
				return true;	//coś jeszcze jest do uwzględnienia tylko nie pamiętam co
			}
		} 
		return false;
	}
	
	/**
	 * Zwraca nazwę atrybutu
	 * @return
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "\nAttributeRepresentation [owner=" + owner + ", name=" 
				+ name + ", startsAt=" + startsAt + ", nameEndsAt=" + nameEndsAt + ", equalsSignAt="
				+ equalsSignPos + ", startQuotationMarkAt=" + startQuotationMarkAt + ", endQuotationMarkAt="
				+ endQuotationMarkAt + ", hasValue=" + hasValue + ", singleQuoted=" 
				+ singleQuoted + ", doubleQuoted=" + doubleQuoted + "]";
	}
	
	public StringBuffer stringifyAsStringBuffer(char[] chars){
		if(isValid()){
			StringBuffer sb = new StringBuffer();
			sb.append(chars, startsAt, nameEndsAt-startsAt+1);
			sb.append('=');
			sb.append('\"');
			sb.append(chars, startQuotationMarkAt+1, endQuotationMarkAt-1-(startQuotationMarkAt+1)+1);
			sb.append('\"');
			return sb;
		}
		return null;
	}
}
