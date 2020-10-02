package hr.fer.zemris.java.hw05.db;

/**
 * Strategy responsible for obtaining a requested field from given student's record
 * @author Andi Škrgat
 * @version 1.0
 */
public interface IFieldValueGetter {
	
	/**
	 * @param record whose some field will be obtained
	 * @returns requested field
	 */
	public String get(StudentRecord record);

}
