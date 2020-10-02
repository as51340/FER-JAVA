package hr.fer.zemris.java.hw05.db;

/**
 * Interface which we use for filtering records from collection by calling method accepts
 * @author Andi Škrgat
 * @version 1.0
 */
public interface IFilter {

	/**
	 * Decides if given record satisfies some condition
	 * @param record that we check if satisfies
	 * @returns true if satisfies else false
	 */
	public boolean accepts(StudentRecord record);
}
