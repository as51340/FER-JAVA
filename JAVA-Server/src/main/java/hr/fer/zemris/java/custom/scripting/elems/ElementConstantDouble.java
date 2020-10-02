package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Inherits element class. Stores double constant element.
 * @author Andi Škrgat
 * @version 1.0
 */
public class ElementConstantDouble extends Element{

	private double value;
	
	/**
	 * @returns string representation of value
	 */
	@Override
	public String asText() {
		return Double.toString(value);
	}
	
	/**
	 * Constructor for initialization parameter value.
	 * @param value
	 */
	public ElementConstantDouble(double value) {
		super();
		this.value = value;
	}

	/**
	 * @returns double value of element variable
	 */
	public double getValue() {
		return this.value;
	}
	
	@Override
	public boolean equals(Object obj) {
		ElementConstantDouble es;
		if(obj instanceof ElementConstantDouble) {
			es = (ElementConstantDouble) obj;
		} else {
			return false;
		}
		return (es.value == this.value) ==true ? true : false; 
	}
}
