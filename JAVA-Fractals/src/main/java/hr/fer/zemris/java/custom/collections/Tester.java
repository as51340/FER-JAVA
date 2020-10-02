package hr.fer.zemris.java.custom.collections;


/**
 * @author Andi Škrgat
 * Interface for testing if some object is acceptable or not.
 */
public interface Tester<E> {
	
	/**
	 * @param obj that has been tested
	 * @returns true if it can be accepted false otherwise
	 */
	boolean test(E obj);
}
