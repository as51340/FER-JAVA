package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Inherits element class and stores string constant from document that will be parsed
 * @author Andi Škrgat
 * @version 1.0
 */
public class ElementString extends Element{

	private String value;
	
	/**
	 * @returns string value
	 */
	@Override
	public String asText() {
		int length = value.length();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < length; i++) {
			if(value.charAt(i) == '\\') {
				sb.append('\\');
			} else if(value.charAt(i) == '\"') {
				if(i != 0 && i != length -1) {
					sb.append('\\');
				}
			}
			sb.append(value.charAt(i));
		}
		sb = sb.insert(0, '\"');
		sb = sb.insert(sb.length(), '\"');
		return sb.toString();
	}
	
	/**
	 * @returns string value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Constructor initalizes value.
	 * @param value
	 */
	public ElementString(String value) {
		super();
		this.value = value;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		ElementString es;
		if(obj instanceof ElementString) {
			es = (ElementString) obj;
		} else {
			return false;
		}
		if(es.asText().equals(this.asText()) ==true) {
			return true;
		} else {
			return false;
		}
	}
}
