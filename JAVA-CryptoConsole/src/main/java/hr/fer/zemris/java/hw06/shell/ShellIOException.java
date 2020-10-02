package hr.fer.zemris.java.hw06.shell;

/**
 * Exception that is thrown if some error occurs while communicating with user. Extends RuntimeException which means
 * that console can recover from some unwanted situation.
 * @author Andi Škrgat
 * @version 1.0
 */
public class ShellIOException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default empty constructor
	 */
	public ShellIOException() {
		super();
	}
	
	/**
	 * @param message description of what happened
	 */
	public ShellIOException(String message) {
		super(message);
	}
	
	/**
	 * @param message description of what happened
	 * @param cause instance of throwable
	 */
	public ShellIOException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * @param cause instance of throwable
	 */
	public ShellIOException(Throwable cause) {
		super(cause);
	}
 
}
