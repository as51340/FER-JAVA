package hr.fer.zemris.java.custom.collections;


/**
 * Collection that represents some general collection of objects
 * @author Andi Škrgat
 * @version 2.0
 */
 public interface Collection {
			
	/**
	 * @return true if collection doesn't contain any object, false otherwise. 
	 */
	 default boolean isEmpty() {
		if(size() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Method works without arguments.
	 * @returns size of this collection.
	 */
	 int size();
	
	/**
	 * Adds the given object into this collection.
	 * @param value 
	 */
	 void add(Object value);
	
	/**
	 * @param value given object
	 * @return true if collection contains object, else false
	 */
	 boolean contains(Object value);
	
	/**
	 * Removes element if it is in the list.
	 * @param value given object
	 * @return true if collection contains value, false otherwise
	 */
	 boolean remove(Object value);
	
	/**
	 * @return object array filled with collection's content
	 */
	 Object[] toArray();
	
	/**
	 * Method calls processor.process() for each element of this collection.
	 * @param processor
	 */
	 default void forEach(Processor processor) {
		 ElementsGetter getter = this.createElementsGetter();
		 while(getter.hasNextElement() == true) {
			 processor.process(getter.getNextElement());
		 }
	 }
	
	/**
	 * Method adds into the current collection all elements from the given collection.
	 * @param other given collection.
	 */
	 default void addAll(Collection other) {
		for(int i = 0; i < other.size(); i++) {
			add(other.toArray()[i]);
		}
	}
	
	/**
	 * Removes all elements from this collection.
	 */
	 void clear();
	
	/**
	 * @returns interface that is used as iterator over collection.
	 */
	 ElementsGetter createElementsGetter();
	
	/**
	 * Adds all items from col that satisfies condition written in class tester
	 * @param col collection from where we check items
	 * @param tester class where is given some condition to meet
	 */
	 void addAllSatisfying(Collection col, Tester tester);
	 

	
	
	
	
 }
