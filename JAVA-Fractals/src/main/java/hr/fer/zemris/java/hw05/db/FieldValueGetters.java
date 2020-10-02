package hr.fer.zemris.java.hw05.db;

/**
 * Class offers concrete implementation of field getters
 * @author Andi Škrgat
 * @version 1.0
 */
public class FieldValueGetters {

	/**
	 * Getter for student's first name
	 */
	public static final IFieldValueGetter FIRST_NAME = (r) -> r.getFirstName();
	
	/**
	 * Getter for student's last name
	 */
	public static final IFieldValueGetter LAST_NAME = (r) -> r.getLastName();
	
	/**
	 * Getter for student's jmbag
	 */
	public static final IFieldValueGetter JMBAG = (r) -> r.getJmbag();
	
}
