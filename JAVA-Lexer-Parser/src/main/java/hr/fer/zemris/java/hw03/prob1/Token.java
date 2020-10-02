package hr.fer.zemris.java.hw03.prob1;

/**
 * @author Andi Škrgat
 * @version 1.0
 * Token represents lexical unit that groups one or more consecutive characters from input text. 
 */
public class Token {
	
	Object value;
	TokenType type;
	
	/**
	 * Constructor that sets value and type to the given values.
	 * @param type of generated token
	 * @param value of token
	 */
	public Token(TokenType type, Object value) {
		this.value = value;
		this.type = type;
	}

	/**
	 * @returns value of token
	 */
	public Object getValue() {
		return this.value;
	}
	
	/**
	 * @returns type of token
	 */
	public TokenType getType() {
		return this.type;
	}
 }
