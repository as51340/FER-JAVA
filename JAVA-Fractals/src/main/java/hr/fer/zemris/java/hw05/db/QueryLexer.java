package hr.fer.zemris.java.hw05.db;

/**
 * Lexer is used as a system for doing lexical analysis of some input that can be e.g source code of some program and output is sequence of tokens.
 * It defines only one public method for getting next token from input and several methods for extracting specific token
 * @author Andi Škrgat
 * @version 1.0
 */
public class QueryLexer {

	/**
	 * String input written in array of characters
	 */
	private char[] data;
	
	/**
	 * Current position in data 
	 */
	private int i = 0;
	
	/**
	 * Last extracted token
	 */
	private Token currentToken;

	/**
	 * Constructor that initializes data input
	 */
	public QueryLexer(String input) {
		
		data = input.toCharArray();
	}
	
	/**
	 * @returns next token from input or throws ParserException if there error happened during tokenization
	 */
	public Token getToken() {
		if(currentToken != null && currentToken.getType() == TokenType.EOF) {
			throw new ParserException("No more tokens to extract");
		}
		skipBlanks();
		if(i >= data.length) {
			currentToken  = new Token("", TokenType.EOF);
			return currentToken;
		}
		if(data[i] == 'j' || data[i] == 'f' || data[i] == 'l') {
			String content = extractAttribute();
			currentToken = new Token(content, TokenType.ATTRIBUTE); 
			return currentToken;
		} else if(Character.toLowerCase(data[i]) == 'a') {
			String content = extractLogicalOperator();
			currentToken = new Token(content, TokenType.LOGICAL_OPERATOR);
			return currentToken;
		}
		else if(data[i] == '=' || data[i] == '>' || data[i] == '<' || data[i] == '!' || data[i] == 'L') {
			String content = extractOperator();
			currentToken = new Token(content, TokenType.OPERATOR);
			return currentToken; 
		} else if(data[i] == '\"') {
			String content = extractLiteral();
			currentToken = new Token(content, TokenType.LITERAL);
			return currentToken;
		} else {
			throw new ParserException("Query not regular");
		}
		
	}
	
	/**
	 * @return the currentToken
	 */
	public Token getCurrentToken() {
		return currentToken;
	}

	/**
	 * Private method used for skipping blanks from input line
	 */
	@SuppressWarnings("deprecation")
	private void skipBlanks() {
		while(i < data.length && Character.isSpace(data[i]) == true) {
			i++;
		}
	}
	
	/**
	 * Private method used for extracting student's attribute from input line
	 * @returns string-student's attribute
	 */
	@SuppressWarnings("deprecation")
	private String extractAttribute() {
		StringBuilder sb = new StringBuilder();
		//System.out.println(data[i]);
		while(i < data.length && (Character.isSpace(data[i]) == false) && data[i] != '=' && data[i] != '>' && data[i] != '<' && data[i] != '!') { // attributes end when space occurs or operator
			sb.append(data[i++]);
		}
		return sb.toString();
	}
	
	/**
	 * Private method used for extracting operator from input line. Allowed operators are =, >, >=, <=, !=, LIKE
	 * @returns string operator
	 */
	@SuppressWarnings("deprecation")
	private String extractOperator() {
		StringBuilder sb = new StringBuilder();
		while(i < data.length && (Character.isSpace(data[i]) == false) && data[i] != '\"' ) { //operators are separated with \" or with space
			sb.append(data[i++]);
		}
		return sb.toString();
	}
	
	/**
	 * Method used for extracting logical operator "and"
	 * @returns string containing operator
	 */
	@SuppressWarnings("deprecation")
	private String extractLogicalOperator() {
		StringBuilder sb = new StringBuilder();
		while(i < data.length &&  (Character.isSpace(data[i]) == false)) { //after and there must be space
			sb.append(data[i++]);
		}
		return sb.toString();
	}
	
	/**
	 * Private method used for extracting literals from input. Literals begin and end with \"
	 * @returns string containing literal
	 */
	private String extractLiteral() {
		StringBuilder sb = new StringBuilder();
		i++;
		while(true) {
			if(i >= data.length) {
				throw new ParserException("No literal ending");
			}
			if(data[i] == '\"') {
				i++;
				break;
			}
			sb.append(data[i++]);
		}
		return sb.toString();
	}
	
	
	
}
