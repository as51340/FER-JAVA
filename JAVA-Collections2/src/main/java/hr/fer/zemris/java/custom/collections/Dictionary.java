package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;

/**
 * An object that maps keys to values. A map cannot contain duplicate keys; each key can be mapped to at most one value. Keys must not be null and values are allowed to be null. 
 * @author Andi Škrgat
 * @version 1.0
 * @param <K> key 
 * @param <V> value
 */
public class Dictionary<K, V> {

	/**
	 * Private class that's used for storing values for corresponding keys. 
	 * @author Andi Škrgat
	 * @version 1.0
	 * @param <K> key
	 * @param <V> value
	 */
	private class Node{
		
		/**
		 * K key of mapping
		 */
		private K key;
		
		/**
		 * Value of mapping
		 */
		private V value;
		
		/**
		 * Constructor for initialization node with properties key and value.
		 * @param key key with which the specified value is to be associated
		 * @param value value to be associated with the specified key
		 */
		private Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}
	
	/**
	 * Collection where mappings are internally stored.
	 */
	ArrayIndexedCollection<Node> col;
	
	/**
	 * Default constructor for allocation memory for this map. 
	 */
	public Dictionary() {
		col = new ArrayIndexedCollection<Node>();
	}	
	
	/**
	 * @returns true if dictionary is empty, else false.
	 */
	public boolean isEmpty() {
		return col.isEmpty();
	}
	
	/**
	 * @returns number of key-value mappings stored in dictionary
	 */
	public int size() {
		return col.size();
	}
	
	/**
	 * Clears map. After this operator map will be empty.
	 */
	public void clear() {
		col.clear();
	}
	
	/**
	 * Associates specified value with the specified key. If already exists current mapping for this key, then old value is replaced by new value. 
	 * @param key key with which the specified values is to be associated.
	 * @param value value to be associated with key
	 */
	public void put(K key, V value) {
		if(key == null) {
			throw new NullPointerException("Key must not be null!");
		}
		V val = this.get(key);
		if((val == null && containsKey(key) == true) || val != null) {
			ElementsGetter<Node> iterator = col.createElementsGetter();
			while(iterator.hasNextElement() == true) {
				Node node = iterator.getNextElement();
				if(node.key.equals(key) == true) {
					node.value = value;
					break;
				}
			}
		} else {
			col.add(new Node(key, value));
		}
		
		
	}
	
	/**
	 * Returns the value mapped to the given key. If there is no such key then null is returned. Considering it is also possible to store null values for specific key, containsKey() is used to distinguish these 2 cases. 
	 * @param key Object key with which specific values is to be associated
	 * @returns value that is mapped to the given key.
	 * @throws ConcurrentModificationException if map is being modified while searching for some value.
	 */
	public V get(Object key) {
		ElementsGetter<Node> iterator = col.createElementsGetter();
		while(iterator.hasNextElement() == true) {
			Node node = iterator.getNextElement();
			if(node.key.equals(key) == true) {
				return node.value;
			}
		}
		return null;
	}
	
	/**
	 * Checks if map contains key. This method is used for checking if some map has value mapped to some key and is null or map does not contain key.
	 * @param key key whose presence is to be tested.
	 * @returns true if map contains mapping for this key, else false.
	 */
	public boolean containsKey(Object key) {
		ElementsGetter<Node> iterator = col.createElementsGetter();
		while(iterator.hasNextElement() == true) {
			Node node = iterator.getNextElement();
			if(node.key.equals(key) == true) {
				return true;
			}
		}
		return false;
	}
	
}
