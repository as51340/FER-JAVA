package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Class representing token for tokenization provided from lexer. It has private variables TokenType and value Element.
 * @author Andi Škrgat
 * @version 1.0
 */
public class Token {
	
	private TokenType type;
	private Element value;
	
	/**
	 * Constructor for initalization token.
	 * @param type of token
	 * @param el value
	 */ 
	public Token (TokenType type, Element el) {
		this.type = type;
		this.value = el;
	}
	
	/**
	 * @return the type of token
	 */
	public TokenType getType() {
		return type;
	}
	
	
	/**
	 * @return the token value
	 */
	public Element getValue() {
		return value;
	}
	
	
	
	

}
