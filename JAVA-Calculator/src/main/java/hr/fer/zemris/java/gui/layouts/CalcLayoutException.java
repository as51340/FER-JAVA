package hr.fer.zemris.java.gui.layouts;

/**
 * More detailed exception that gives us information what happened while calculator has been doing his job
 * @author Andi Škrgat
 * @version 1.0
 */
public class CalcLayoutException extends RuntimeException{ 
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Calls super constructor with no parameters sent.
	 */
	public CalcLayoutException() {
		super();
	}
	
	/**
	 * @param cause instance of {@linkplain Throwable}
	 */
	public CalcLayoutException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * @param message explanation why error occurred
	 */
	public CalcLayoutException(String message) {
		super(message);
	}
	
	/**
	 * @param message explanation why error occurred
	 * @param cause instance of {@linkplain Throwable}
	 */
	public CalcLayoutException(String message, Throwable cause) {
		super(message, cause);
	}

}
