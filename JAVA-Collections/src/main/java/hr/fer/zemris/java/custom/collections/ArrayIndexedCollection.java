package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

////Javadoc isn't written only for methods that should behave in exactly the same way as in parent class Collection 

/**
 * Implementation of resizable array-backed collection of objects.
 * @author Andi Škrgat
 * @version 1.0
 */
public class ArrayIndexedCollection extends Collection{
	public static final int defaultCapacity = 16;
	private int size;
	private Object[] elements;
	
	/**
	 * Sets capacity of collection to default value(16) and allocates memory for array elements.
	 */
	public ArrayIndexedCollection() {
		this(defaultCapacity);
	}
	
	/**
	 * Sets capacity of collection to initialCapacity and allocates memory for array elements.
	 * @param initalCapacity capacity of collection 
	 */
	public ArrayIndexedCollection(int initalCapacity) {
		if(initalCapacity < 1) {
			throw new IllegalArgumentException("Capacity should be at least 1!");
		}
		elements = new Object[initalCapacity];
		this.size = 0;
	}
	
	/**
	 * Copies collection's items to this collection and sets size to defaultCapacity or size of accepted collection if is larger.
	 * @param other Collection from which we'll copy items.
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, defaultCapacity);
	}
	
	/**
	 * Copies collection's items to this collection and sets size to initialCapacity or size of accepted collection if it is larger.
	 * @param other Collection from which we'll copy items.
	 * @param initialCapacity initialCapacity of collection
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		if(other == null) {
			throw new NullPointerException("Can't add items from null reference.");
		}
		if(other.size() > initialCapacity) {
			elements = new Object[other.size()];
		}
		else {
			elements = new Object[initialCapacity];
		}
		this.addAll(other);
	}
	/**
	 * This method adds given object into first empty place in the elements array and refuses to add null.
	 * @param value object we are adding to this collection, if null NullPointerException() is thrown
	 */
	@Override
	public void add(Object value) {
		if(value == null) {
			throw new NullPointerException("It's not possible to add null to collection.");
		}
		if(this.size == elements.length) {
			Object[] newElements = new Object[2 * elements.length];
			for(int i = 0; i < this.size; i++) {
				newElements[i] = elements[i];
			}
			newElements[this.size] = value;
			elements = newElements;
		}
		else {
			elements[this.size] = value;
		}
		size++;
	}
	
	/**
	 * @param index position of taken object, if index is invalid throws IndexOutOfBoundsException.  
	 * @return object at position "index"
	 */
	public Object get(int index) {
		if(index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException("Index out of range.");
		}
		return elements[index];
	}
	
	/**
	 * Removes all elements from the collection. 
	 */
	@Override
	public void clear() {
		for(int i = 0; i < this.size; i++) {
			elements[i] = null;
		}
		this.size = 0;
	}
	
	/**
	 * Method inserts the given object at the given position and shifts else for one position. 
	 * @param value object we are inserting
	 * @param position index at which object "value" will be put
	 */
	public void insert(Object value, int position) {
		if(position < 0 || position > this.size) {
			throw new IndexOutOfBoundsException("Index out of range.");
		}
		if(value == null) {
			throw new NullPointerException("Object cannot be null");
		}
		if(position == this.size) {
			this.add(value);
		}
		else {
			for(int i = this.size; i > position; i--) {
				elements[i] = elements[i-1];
			}
			elements[position]= value;
			this.size++;
		}
	}
	
	/**
	 * Searches the collection and returns the index of first occurrence. 
	 * @param value object we are searching in array elements.
	 * @return -1 if the value is not found or is null, else index of first occurence
	 */
	public int indexOf(Object value) {
		if(value == null) {
			return -1;
		}
		for(int i = 0; i < this.size; i++) {
			if(elements[i].equals(value) == true) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Removes element at specified position or throws IndexOutOfBoundsException()
	 * @param index position from which we are removing element.
	 */
	public void remove(int index) {
		if(index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException("Index out of range.");
		}
		for(int i = index; i < this.size -1; i++) {
			elements[i] = elements[i+1];
		}
		elements[size -1] = null;
		size--;
	}
	
	@Override
	public int size() {
		return this.size;
	}
	
	@Override
	public boolean contains(Object value) {
		for(int i = 0; i < this.size; i++) {
			if(elements[i].equals(value) == true) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean remove(Object value) {
		int index = this.indexOf(value);
		if(index == -1) {
			return false;
		}
		this.remove(index);
		return true;
	}
	
	@Override
	public Object[] toArray() {
		Object[] newArray = new Object[this.size];
		for(int i = 0; i < this.size; i++) {
			newArray[i] = elements[i];
		}
		return newArray;
	}
	
	/**
	 * For every object in the array elements processor.process is called.
	 * @param processor 
	 */
	@Override
	public void forEach(Processor processor) {
		for(Object o: elements) {
			if(o != null) {
				processor.process(o);
			}
		}
	}
	
	@Override
	public void addAll(Collection other) {
		Object[] arr = other.toArray();
		for(Object a: arr) {
			this.add(a);
		}
	}
	
	public static void main(String[] args) {
		ArrayIndexedCollection col = new ArrayIndexedCollection(2);
		col.add(Integer.valueOf(20));
		col.add("New York");
		col.add("San Francisco");  // here the internal array is reallocated to 4
		System.out.println(col.size());
		System.out.println(col.contains("New York")); // writes: true
		col.remove(1);// removes "New York"; shifts "San Francisco" to position 1
		System.out.println(col.get(1)); // writes: "San Francisco"
		System.out.println(col.size()); // writes: 2
		col.add("Los Angeles");
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col);// This is local class representing a Processor which writes objects to System.out
		class P extends Processor {
			public void process(Object o) {    
				System.out.println(o); 
			}
		};
		System.out.println("col elements:");
		col.forEach(new P());
		System.out.println("col elements again:");
		System.out.println(Arrays.toString(col.toArray()));
		System.out.println("col2 elements:");
		col2.forEach(new P());
		System.out.println("col2 elements again:");
		System.out.println(Arrays.toString(col2.toArray()));
		System.out.println(col.contains(col2.get(1))); // true
		System.out.println(col2.contains(col.get(1))); // true
		col.remove(Integer.valueOf(20)); // removes 20 from collection (at position 0).
		col.forEach(new P());
	}
	
}
