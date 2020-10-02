package hr.fer.zemris.java.custom.collections;

/**
 * Implementation of linked list that stores objects. Null values are not allowed and duplicates are.
 * @author Andi Škrgat
 * @version 1.0
 */
public class LinkedListIndexedCollection extends Collection{
	
	/**
	 * Private class ListNode represents one node of our list with references for previous and next node and variable for storage object.
	 * @author Andi Škrgat
	 */
	private static class ListNode {
		Object value;
		ListNode previous;
		ListNode next;
	}
	
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
	
	/**
	 * For every object in the array elements processor.process is called
	 * @param processor 
	 */
	@Override
	public void forEach(Processor processor) {
		ListNode tmp = first;
		while(tmp != null) {
			processor.process(tmp.value);
			tmp = tmp.next;
		}
	}
	
	@Override
	public void addAll(Collection other) {
		Object[] arr = other.toArray();
		for(Object a: arr) {
			this.add(a);
		}
	}
}

