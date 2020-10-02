package hr.fer.zemris.java.custom.collections;



/**
 * Interface represents iterator with 2 methods: hasNextElement and getNextElement. It returns element from collection when user asks for.   
 * @author Andi Škrgat
 * @version 1.0
 */
public interface ElementsGetter{

	/**
	 * @returns true if there is at least one element in collection
	 */
	boolean hasNextElement();
	
	/**
	 * @returns next item, order here is not specified, if there is no element throws NoSuchElementException
	 */
	Object getNextElement();
	
	/**
	 * For the rest of the items calls processor. 
	 * @param p instance of processor
	 */
	void processRemaining(Processor p);
}
