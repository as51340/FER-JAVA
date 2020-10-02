package hr.fer.zemris.java.custom.collections;


/**
 * Collection that represents some general collection of objects
 * @author Andi Škrgat
 * @version 1.0
 */
public class Collection {
		
	/**
	 * Default constructor for this class.
	 */
	protected Collection() {
		
	}
	
	/**
	 * @return true if collection doesn't contain any object, false otherwise. 
	 */
	public boolean isEmpty() {
		if(size() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Method works without arguments.
	 * @returns size of this collection.
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Adds the given object into this collection.
	 * @param value 
	 */
	public void add(Object value) {
		
	}
	
	/**
	 * @param value given object
	 * @return true if collection contains object, else false
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Removes element if it is in the list.
	 * @param value given object
	 * @return true if collection contains value, false otherwise
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * @return object array filled with collection's content
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException("Not possible at the moment. ");
	}
	
	/**
	 * Method calls processor.process() for each element of this collection.
	 * @param processor
	 */
	public void forEach(Processor processor) {
		
	}
	
	/**
	 * Method adds into the current collection all elements from the given collection.
	 * @param other given collection.
	 */
	public void addAll(Collection other) {
		class P extends Processor {
			public void process(Object o) {    
				add(o);
			}
		};
		other.forEach(new P());
	}
	
	/**
	 * Removes all elements from this collection.
	 */
	public void clear() {
		
	}
	

	
	
	
 }
