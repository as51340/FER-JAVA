package hr.fer.zemris.java.hw05.db;

/**
 * Interface for all concrete operators that will define its own method satisfied 
 * @author Andi Škrgat
 * @version 1.0
 */
public interface IComparisonOperator {

	/**
	 * Method that we use to check if 2 strings are in some specific relation. Each operator has its own specified relation that 2 strings need to satisfy.
	 * @param value1 first string that we compare to the other one
	 * @param value2 second string that we compare to the first one
	 * @returns true if parameters satisfy condition
	 */
	public boolean satisfied(String value1, String value2);
}
