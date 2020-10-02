package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Exception derived from {@linkplain RuntimeException} used in implementation of {@linkplain SmartScriptParser}
 * @author Andi Škrgat
 * @version 1.0
 */
public class SmartScriptParserException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Calls super constructor with no parameters sent.
	 */
	public SmartScriptParserException() {
		super();
	}
	
	/**
	 * @param message description of what happened
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}
	
	/**
	 * @param message description of what happened
	 * @param cause instance of throwable
	 */
	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param cause instance of throwable
	 */
	public SmartScriptParserException(Throwable cause) {
		super(cause);
	}
 }
