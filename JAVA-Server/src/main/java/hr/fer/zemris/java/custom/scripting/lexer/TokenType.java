package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Enum used in lexer for giving {@linkplain SmartScriptParser} information about what are we extracting
 * @author Andi Škrgat
 * @version 1.0
 */
public enum TokenType {
	
	ECHO, FOR, TAGNAME, FUNCTION, VARIABLE, STRING, NUMBER, ECHOEND, FOREND, OPERATOR, END, EOF, TEXT;
}