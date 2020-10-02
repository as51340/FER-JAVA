package hr.fer.zemris.java.custom.collections;


/**
 * Exception that is thrown if stack is empty. It inherits from RuntimeExcpetion. 
 * @author Andi Škrgat
 * @version 1.0
 */
public class EmptyStackException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * Empty constructor.
	 */
	public EmptyStackException() {
		super();
	}
	
	/**
	 * Constructor with string message.
	 * @param message string that describes what happened
	 */
	public EmptyStackException(String message) {
		super(message);
	}
	
	/**
	 * It calls parent's constructor that will deal with Throwable type.
	 * @param cause Throwable
	 */
	public EmptyStackException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * Calls super and forwards arguments.
	 * @param message string that describes what happened
	 * @param cause instance of Throwable
	 */
	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}
