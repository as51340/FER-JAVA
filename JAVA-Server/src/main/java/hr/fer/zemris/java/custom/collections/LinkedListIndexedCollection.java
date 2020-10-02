package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

//Javadoc isn't written only for methods that should behave in exactly the same way as in interface Collection

/**
 * Implementation of linked list that stores objects. Null values are not allowed and duplicates are.
 * @author Andi Škrgat
 * @version 2.0
 */
public class LinkedListIndexedCollection implements List{
	
	/**
	 * Private class ListNode represents one node of our list with references for previous and next node and variable for storage object.
	 * @author Andi Škrgat
	 */
	private static class ListNode {
		Object value;
		ListNode previous;
		ListNode next;
	}
	
	private long modificationCount = 0;
	private int size;
	private ListNode first;
	private ListNode last;
	
	/**
	 * Default constructor.
	 */
	public LinkedListIndexedCollection() {
		size = 0;
		first = last = null;
	}
	
	/**
	 * It other is not null copies all elements to this collection.
	 * @param other
	 */
	public LinkedListIndexedCollection(Collection other) {
		if(other == null) {
			throw new NullPointerException("Collection must not be null.");
		}
		size = 0;
		first = last = null;
		this.addAll(other);
	}
	
	/**
	 * Adds the given object at the end of this collection.
	 * @param value object we are adding to our collection.
	 */
	@Override
	public void add(Object value) {
		if(value == null) {
			throw new NullPointerException("Object cannot be null!");
		}
		ListNode myNode = new ListNode();
		myNode.value = value;
		myNode.next = null;
		myNode.previous = last;
		if(last == null) {
			first = myNode;
		}
		else {
			last.next = myNode;
		}
		last = myNode;
		size++;
		modificationCount++;
	}
	
	/**
	 * Returns the object that is stored in linked list at position index, if index invalid throws IndexOutOfBoundsException.
	 * @param index position from where we are getting object
	 * @return object at the index
	 */
	public Object get(int index) {
		if(index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException("Index out of range.");
		}
		if(index < size/2) {
			ListNode temp = first;
			for(int i = 0; i < index; i++) {
				temp = temp.next;
			}
			return temp.value;
		}
		else {
			ListNode temp = last;
			for(int i = 0; i < (size -1) - index; i++) {
				temp = temp.previous;
			}
			return temp.value;
		}
	}
	
	/**
	 * Removes all elements from the collection.
	 */
	@Override
	public void clear() {
		ListNode tmp = first;
		while(tmp != null) {
			ListNode net = tmp.next;
			tmp.previous = null;
			tmp.next = null;
			tmp = null;
			tmp = net;
		}
		first = last = null;
		size = 0;
		modificationCount++;
	}
	
	/**
	 * Insert the given value at the given position in linked-list. If position is invalid, appropriate exception will be thrown. 
	 * @param value object we are inserting into our list.
	 * @param position index where we are putting element.
	 */
	public void insert(Object value, int position) {
		if(position < 0 || position > this.size) {
			throw new IndexOutOfBoundsException("Index out of range.");
		}
		if(value == null) {
			throw new NullPointerException("Object cannot be null");
		}
		ListNode myNode = new ListNode();
		myNode.value = value;
		myNode.previous = null;
		myNode.next = null;
		if(position == size || first == null) {
			add(value);
			return;
		}
		ListNode tmp = first;
		for(int i = 0; i < position; i++) {
			tmp = tmp.next;
		}
		myNode.next = tmp;
		if(tmp != null && tmp.previous != null) {
			tmp.previous.next = myNode;
		}
		if(tmp != null) {
			myNode.previous = tmp.previous;
			tmp.previous = myNode;
		}
		if(position == 0) {
			first = myNode;
		}
		size++;
		modificationCount++;
	}
	
	/**
	 * Searches the collection and returns the index of first occurrence of the given value or -1 if such doesn't exist. 
	 * @param value object we are searching for in out list
	 * @return index of first occurrence 
	 */
	public int indexOf(Object value) {
		ListNode tmp = first;
		int i = 0;
		while(tmp != null) {
			if(tmp.value.equals(value) == true) {
				return i;
			}
			tmp = tmp.next;
		}
		return -1;
	}
	
	/**
	 * Removes element at specified index from collection. In case of invalid index throws IndexOutOfBoundsException.
	 * @param index position from where we are removing element.
	 */
	public void remove(int index) {
		if(index < 0 || index >= this.size) {
			throw new IndexOutOfBoundsException("Index out of range.");
		}
		ListNode tmp = first;
		for(int i = 0; i < index; i++) {
			tmp = tmp.next;
		}
		if(first.equals(tmp) == true && last.equals(tmp) == true) {
			first = last = null;
		}
		else if(first.equals(tmp) == true) {
			first = first.next;
			tmp.next.previous = null;
		}
		else if(last.equals(tmp) == true) {
			last = last.previous;
			tmp.previous.next = null;
		}
		else {
			tmp.next.previous = tmp.previous;
			tmp.previous.next = tmp.next;
		}
		tmp = null;
		size--;
		modificationCount++;
	}
	
	@Override
	public int size() {
		return this.size;
	}
	
	@Override
	public boolean contains(Object value) {
		ListNode tmp = first;
		while(tmp != null) {
			if(tmp.value.equals(value) == true) {
				return true;
			}
			tmp = tmp.next;
		}
		return false;
	}
	
	@Override
	public boolean remove(Object value) {
		int index = indexOf(value);
		if(index == -1) {
			return false;
		}
		remove(index);
		modificationCount++;
		return true;
	}
	
	@Override
	public Object[] toArray() {
		Object[] myArr = new Object[size];
		ListNode tmp = first;
		int i = 0;
		while(tmp != null) {
			myArr[i] = tmp.value;
			i++;
			tmp = tmp.next;
		}
		return myArr;
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
	 * @returns implementation of iterator over LinkedListIndexedCollection
	 */
	@Override
	public ElementsGetter createElementsGetter() {
		return new LinkedListIndexedCollectionElementsGetter(this);
	}
	
	/** 
	 * @author Andi Škrgat
	 * Class used as concrete implementation of iterator for LinkedListIndexedCollection.
	 */
	private static class LinkedListIndexedCollectionElementsGetter implements ElementsGetter{
		
		private ListNode current;
		private int index = 0;
		private LinkedListIndexedCollection col;
		private long savedModificationCount;
		
		/**
		 * Constructor for iterator
		 * @param first reference to first element in collection
		 * @param size of collection
		 */
		private LinkedListIndexedCollectionElementsGetter(LinkedListIndexedCollection col) {
			this.col = col;
			this.current = col.first;
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
				ListNode tmp = current;
				current = current.next;
				index++;
				return tmp.value;
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

