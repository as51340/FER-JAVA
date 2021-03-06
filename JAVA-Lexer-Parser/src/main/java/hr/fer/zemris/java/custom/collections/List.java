package hr.fer.zemris.java.custom.collections;

/**
 * @author Andi Škrgat
 * @version 1.0
 * Interface that has some more specified methods comparing to Collection, from where it was inherited.
 */
public interface List extends Collection{
	
	/**
	 * @param index
	 * @returns object on position index
	 */
	Object get(int index);
	
	/**
	 * Insert object at the position in collection
	 * @param value object
	 * @param position
	 */
	void insert(Object value, int position);
	
	/**
	 * @param value object
	 * @returns position of value in collection
	 */
	int indexOf(Object value);
	
	/**
	 * Removes object that is stored at the position index.
	 * @param index
	 */
	void remove(int index);
}
