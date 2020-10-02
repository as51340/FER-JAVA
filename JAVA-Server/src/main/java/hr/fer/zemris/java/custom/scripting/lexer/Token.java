package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Class representing token for tokenization provided from lexer. It has private variables {@linkplain TokenType} and value {@linkplain Element}
 * @author Andi Škrgat
 * @version 1.0
 */
public class Token {
	
	/**
	 * Type of token
	 */
	private TokenType type;
	
	/**
	 * Value for token
	 */
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
