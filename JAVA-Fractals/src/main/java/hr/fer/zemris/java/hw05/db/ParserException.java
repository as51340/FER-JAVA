package hr.fer.zemris.java.hw05.db;

/**
 * Exception derived from RuntimeException used in implementation of parser.
 * @author Andi Škrgat
 * @version 1.0
 */
public class ParserException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Calls super constructor with no parameters sent.
	 */
	public ParserException() {
		super();
	}
	
	/**
	 * @param message description of what happened
	 */
	public ParserException(String message) {
		super(message);
	}
	
	/**
	 * @param message description of what happened
	 * @param cause instance of throwable
	 */
	public ParserException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param cause instance of throwable
	 */
	public ParserException(Throwable cause) {
		super(cause);
	}
 }

