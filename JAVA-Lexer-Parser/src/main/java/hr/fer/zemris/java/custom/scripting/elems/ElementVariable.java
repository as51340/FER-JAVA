package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Inherits element class
 * @author Andi Škrgat
 * @version 1.0
 */
public class ElementVariable extends Element {
	
	private String name;
	
	/**
	 * @returns string name of element variable
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @returns property name
	 */
	@Override
	public String asText() {
		return this.name;
	}

	/**
	 * Constructor initalizes name.
	 * @param name
	 */
	public ElementVariable(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		ElementVariable es;
		if(obj instanceof ElementVariable) {
			es = (ElementVariable) obj;
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
