package hr.fer.zemris.java.hw14.dao;

/**
 * Exception used in implementation of our own {@linkplain DAO} for accessing to votingDB database
 * @author Andi Škrgat
 * @version 1.0
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public DAOException() {
	}
	
	/**
	 * Constructor 
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor
	 * @param message
	 * @param cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 * @param message
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * @param cause
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}