package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

////Javadoc isn't written only for methods that should behave in exactly the same way as in interface Collection 

/**
 * Implementation of resizable array-backed collection of objects.
 * @author Andi Škrgat
 * @version 2.0
 */
public class ArrayIndexedCollection implements List{
	public static final int defaultCapacity = 16;
	private int size;
	private Object[] elements;
	private long modificationCount = 0;
	
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
			modificationCount++;
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
		modificationCount++;
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
			modificationCount++;
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
		modificationCount++;
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
		modificationCount++;
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
	
	@Override
	public void addAll(Collection other) {
		Object[] arr = other.toArray();
		for(Object a: arr) {
			this.add(a);
		}
	}
	
	@Override
	public void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter getter = col.createElementsGetter();
		while(getter.hasNextElement() == true) {
			Object obj = getter.getNextElement();
			if(tester.test(obj) == true) {
				add(obj);
			}
		}
	}
	
	/**
	 * @returns ElementsGetter that represents iterator of collection.
	 */
	@Override
	public ElementsGetter createElementsGetter() {
		return new ArrayIndexedCollectionElementsGetter(this);
	}
	
	/**
	 * Class used as iterator over instance of collection, implements ElementsGetter.
	 * @author Andi Škrgat
	 */
	private static class ArrayIndexedCollectionElementsGetter implements ElementsGetter {
		
		private int index = 0;
		private ArrayIndexedCollection col;
		private long savedModificationCount;
		
		/**
		 * @param col reference on outer instance of ArrayIndexedCollection
		 */
		private ArrayIndexedCollectionElementsGetter(ArrayIndexedCollection col) {
			this.col = col;
			savedModificationCount = col.modificationCount;
		}
		
		
		@Override
		public boolean hasNextElement() {
			if(col.modificationCount != savedModificationCount) {
				throw new ConcurrentModificationException("Collection has been changed!");
			}
			return index < col.size;
		}
		
		@Override
		public Object getNextElement() {
			if(hasNextElement() == true) {
				return col.elements[index++];
			}
			else {
				throw new NoSuchElementException("There are no more elements in collection.");
			}
		}
		
		@Override
		public void processRemaining(Processor p) {	
			while(hasNextElement() == true) {
				p.process(getNextElement());
			}
		}
		
		
	}
}
