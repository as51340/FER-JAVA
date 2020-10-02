package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Inherits element class
 * @author Andi Škrgat
 * @version 1.0
 */
public class ElementConstantInteger extends Element{

	private int value;
	
	/**
	 * @param value, initalize value
	 */
	public ElementConstantInteger(int value) {
		super();
		this.value = value;
	}

	/**
	 * @returns string representation of value
	 */
	@Override
	public String asText() {
		return Integer.toString(this.value);
	}
	
	/**
	 * @returns int value of element variable
	 */
	public int getValue() {
		return this.value;
	}
	
	@Override
	public boolean equals(Object obj) {
		ElementConstantInteger es;
		if(obj instanceof ElementConstantInteger) {
			es = (ElementConstantInteger) obj;
		} else {
			return false;
		}
		return (es.value == this.value) ==true ? true : false; 
	}
}
