package hr.fer.zemris.java.hw05.db;

/**
 * Token types used for extracting tokens from query command. EOF is used to signalize end of line, OPERATOR for operators =, >, <, >=, <=, != and LIKE, LOGICAL_OPERATOR for "and" operator, ATTRIBUTE for attributes from student's record and LITERAL as value for some attribute. 
 * @author Andi Škrgat
 * @version 1.0
 */
public enum TokenType {
	
	EOF, OPERATOR, ATTRIBUTE, LOGICAL_OPERATOR, LITERAL;

}
