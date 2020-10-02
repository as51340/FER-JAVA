package hr.fer.zemris.java.hw05.db;

/**
 * Class representing token for tokenization provided from lexer. It has private variables TokenType and content. Each combination of these 2 properties define specific token.
 * @author Andi Škrgat
 * @version 1.0
 */
public class Token {
	
	/**
	 * Value of each token
	 */
	private String content;

	/**
	 * Type of token defined in enum TokenType
	 */
	private TokenType type;
	
	/**
	 * Constructor that defines properties of token
	 * @param content token "value"
	 * @param type of token
	 */
	public Token(String content, TokenType type) {
		this.content = content;
		this.type = type;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @return the type
	 */
	public TokenType getType() {
		return type;
	}
	
	
	
	
}
