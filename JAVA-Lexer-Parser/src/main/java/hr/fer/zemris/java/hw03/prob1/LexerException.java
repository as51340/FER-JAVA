package hr.fer.zemris.java.hw03.prob1;

/**
 * @author Andi Škrgat
 * @version 1.0
 * RuntimeException used for extracting tokens from inpu
 */
public class LexerException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor
	 */
	public LexerException() {
		super();
	}
	
	/**
	 * @param message that will be displayed when exception occurs
	 */
	public LexerException(String message) {
		super(message);
	}
	
	/**
	 * Constructor calls parent's constructor to deal with exception
	 * @param cause instance of Throwable
	 */
	public LexerException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Constructor passes arguments to parent's constructor
	 * @param message string described what happened
	 * @param cause instance of Throwable
	 */
	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}

}
