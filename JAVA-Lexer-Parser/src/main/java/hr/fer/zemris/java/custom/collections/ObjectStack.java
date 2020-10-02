package hr.fer.zemris.java.custom.collections;

/**
 * Class that represents stack with all expected methods.
 * @author Andi Škrgat
 * @version 1.0
 */
public class ObjectStack {
	
	private ArrayIndexedCollection col;
	
	/**
	 * Constructor that allocates memory for storing objects.
	 */
	public ObjectStack() {
		col = new ArrayIndexedCollection();
	}
	
	/**
	 * @return true if stack is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return col.isEmpty();
	}
	
	/**
	 * 
	 * @return number of elements on the stack
	 */
	public int size() {
		return col.size();
	}
	
	/**
	 * 
	 * @param value object that has been put on the stack
	 */
	public void push(Object value) {
		try {
			col.add(value);
		} catch(NullPointerException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * Removes last value pushed on stack from stack and returns it. If the stack is empty EmptyStackExcpetion is thrown.
	 * @return Object
	 */
	public Object pop() {
		if(size() == 0) {
			throw new EmptyStackException("Stack empty, pop not allowed.");
		}
		Object ob =  col.get(size() -1);
		col.remove(size() -1);
		return ob;
	}
	
	/**
	 * Same as pop but doesn't removes item. If the stack is empty EmptyStackExcpetion is thrown.
	 * @return object
	 */
	public Object peek() {
		if(size() == 0) {
			throw new EmptyStackException("Stack empty, pop not allowed");
		}
		return col.get(size() -1);
	}
	
	/**
	 * Removes all elements stack
	 */
	public void clear() {
		col.clear();
	}
}
