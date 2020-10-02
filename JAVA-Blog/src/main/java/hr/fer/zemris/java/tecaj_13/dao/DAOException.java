package hr.fer.zemris.java.tecaj_13.dao;

/**
 * Exception used in blog web app
 * @author Andi Škrgat
 * @version 1.0
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public DAOException(String message) {
		super(message);
	}
}