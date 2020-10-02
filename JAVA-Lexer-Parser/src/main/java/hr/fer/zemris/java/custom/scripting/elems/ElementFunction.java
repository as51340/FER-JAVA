package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Inherits element class
 * @author Andi Škrgat
 * @version 1.0
 */
public class ElementFunction extends Element{
	
	private String name;
	
	/**
	 * @returns string name of element variable
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Constructor initalizes name
	 * @param name
	 */
	public ElementFunction(String name) {
		super();
		this.name = name;
	}

	/**
	 * @returns property name
	 */
	@Override
	public String asText() {
		StringBuilder sb = new StringBuilder();
		sb.append('@');
		sb.append(name);
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		ElementFunction es;
		if(obj instanceof ElementFunction) {
			es = (ElementFunction) obj;
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
