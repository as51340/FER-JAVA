package hr.fer.zemris.java.custom.scripting.lexer;


/**
 * Enum used in lexer so we could now what tokens are we extracting 
 * @author Andi Škrgat
 * @version 1.0
 */
public enum TokenType {
	
	ECHO, FOR, TAGNAME, FUNCTION, VARIABLE, STRING, NUMBER, ECHOEND, FOREND, OPERATOR, END, EOF, TEXT;
}