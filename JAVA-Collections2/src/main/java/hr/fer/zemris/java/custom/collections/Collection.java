package hr.fer.zemris.java.custom.collections;



/**
 * Collection that represents some general collection of objects
 * @author Andi Škrgat
 * @version 2.0
 */
 public interface Collection<E> {
	 
			
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
	 void add(E value);
	
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
	 default void forEach(Processor<E> processor) {
		 ElementsGetter<E> getter = this.createElementsGetter();
		 while(getter.hasNextElement() == true) {
			 processor.process(getter.getNextElement());
		 }
	 }
	
	/**
	 * Method adds into the current collection all elements from the given collection.
	 * @param other given collection.
	 */
	 @SuppressWarnings("unchecked")
	 default void addAll(Collection<? extends E> other) {
		Object[] arr = other.toArray();
		for(int i = 0; i < arr.length; i++) {
			add((E) arr[i]);
		}
	}
	
	/**
	 * Removes all elements from this collection.
	 */
	 void clear();
	
	/**
	 * @returns interface that is used as iterator over collection.
	 */
	 ElementsGetter<E> createElementsGetter();
	
	/**
	 * Adds all items from col that satisfies condition written in class tester
	 * @param col collection from where we check items
	 * @param tester class where is given some condition to meet
	 */
	 void addAllSatisfying(Collection<? extends E> col, Tester<E> tester);
	 

	
	
	
	
 }
