package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Inherits element class and stores operator in parsed document.
 * @author Andi Škrgat
 * @version 1.0
 */
public class ElementOperator extends Element{
	
	private String symbol;
	
	/**
	 * @returns string name of element variable
	 */
	public String getSymbol() {
		return this.symbol;
	}
	
	/**
	 * @returns property symbol
	 */
	@Override
	public String asText() {
		return this.symbol;
	}

	/**
	 * Constructor initalizes symbol
	 * @param symbol
	 */
	public ElementOperator(String symbol) {
		super();
		this.symbol = symbol;
	}

	@Override
	public boolean equals(Object obj) {
		ElementOperator o;
		if(obj instanceof ElementOperator) {
			o = (ElementOperator) obj;
		} else {
			return false;
		}
		
		return this.symbol.equals(o.symbol) == true ? true : false;
	}
}
