package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An object that maps keys to values. One key has only one mapped value. It is possible that value is null, but key must not be null. Class provides simple methods for adding new mappings, getting mapped value, checking if some key or value exists, removing mappings, getting size of map and getting string representation of map.
 * @author Andi Škrgat
 * @version 1.0
 * @param <K> the type of key maintained by this class
 * @param <V> the type of mapped values
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>>{
	
	/**
	 * Determines max load factor of table(size / length), when load factor becomes larger than table has been resized.
	 */
	private static final double maxLoadFactor = 0.75;
	
	/**
	 * Variable that we use to give signals to all iterators that some modification in collection happened
	 */
	private int modificationCount;
	
	/**
	 * Array where mappings are internally stored
	 */
	private SimpleHashtable.TableEntry<K, V>[] table;
	
	/**
	 * Variable where we store size of collection
	 */
	private int size;
	
	/**
	 * Constructs new array with default size of 16.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable() {
		table = (SimpleHashtable.TableEntry<K,V>[]) new SimpleHashtable.TableEntry[16];
		size = 0;
		modificationCount = 0;
	}
	
	/**
	 * Constructs new table with the capacity that is power of 2. Minimum possible capacity is than initial. 
	 * @param initial minimum capacity from where searching for power of 2 starts.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int initial) {
		if(initial < 1) {
			throw new IllegalArgumentException("Capacity cannot be less than 1");
		}
		while(true) {
			if(isPowerOfTwo(initial) == true) {
				table = (SimpleHashtable.TableEntry<K,V>[]) new SimpleHashtable.TableEntry[initial];
				break;
			} else {
				initial++;
			}
		}
		size = 0;
		//System.out.println(initial);
	}
	
	/**
	 * Represents one slot of the table with its private variables :key, value and reference to the next TableEntry if their key provide same hashCode. Map stores mappings with the hashcode of key and all entries that have same hashcode are in one linked list. 
	 * @author Andi Škrgat
	 * @version 1.0
	 * @param <K> type of key that is to be associated to some value
	 * @param <V> type of value mapped to the key
	 */
	public static class TableEntry<K, V> {
		
		/**
		 * Key of mapping
		 */
		private K key;
		
		/**
		 * Value of mapping
		 */
		private V value;
		
		/**
		 * Reference to the next entry if they are both in same slot.
		 */
		private TableEntry<K, V> next;
		
		/**
		 * Constructs new TableEntry with the given mapping and reference to the next TableEntry
		 * @param key key that is to be associated with the key
		 * @param value value that is mapped to the key
		 * @param next reference to the next TableEntry in linkedList. 
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}
		
		/**
		 * @return the value
		 */
		public V getValue() {
			return value;
		}
		
		/**
		 * @param value the value to set
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
		/**
		 * @return the key
		 */
		public K getKey() {
			return key;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(this.key + "=" + this.value);
			return sb.toString();
		}
	}
	
	/**
	 * Intern method that is used for fast checking if some number is power of 2.
	 * @param number number that is to be tested
	 * @returns true if number is power of 2, false else.
	 */
	private static boolean isPowerOfTwo(int number) {
	    return number > 0 && ((number & (number - 1)) == 0);
	}
	
	/**
	 * @returns true if there are no existing mappings in the map
	 */
	public boolean isEmpty() {
		return this.size == 0 ? true: false;
	}
	
	/**
	 * @returns number of mappings in the map
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Method returns value that is mapped to the specified key. If there was not value mapped to the key than null is returned. 
	 * @param key key whose mapped value we are searching for. If key is null, null is returned. 
	 * @returns mapped value or null if there is no such. 
	 */
	public V get(Object key) {
		if(size == 0 || key == null) {
			return null;
		}
		int hashCode = key.hashCode() % table.length;
		if(hashCode < 0) {
			hashCode *= -1;
		}
		TableEntry<K, V> node = this.table[hashCode];
		while(node != null) {
			if(node.getKey().equals(key) == true) {
				return node.getValue();
			}
			node = node.next;
		}
		return null;
	}
	
	/**
	 * Removes mapping with key from map if there is such. If it doesn't exist than method does not do anything. 
	 * @param key key whose mapping is to be removed.
	 */
	public void remove(Object key) {
		if(key == null) {
			return;
		}
		int hashCode = key.hashCode() % table.length;
		if(hashCode < 0) {
			hashCode *= -1;
		}
		TableEntry<K, V> node = this.table[hashCode];
		if(node.getKey().equals(key) == true) {
			this.table[hashCode] = node.next;
			size--;
			modificationCount++;
			return;
		} 
		while(node.next != null) {
			if(node.next.getKey().equals(key) == true) {
				node.next = node.next.next;
				size--;
				modificationCount++;
				return;
			}
			node = node.next;
		}
	}
	
	/**
	 * Maps the given value to the given key. If key already exists than old value is replaced by new value. For checking if 2 keys are same, method equals is used. 
	 * @param key key with which the specified value is to be associated.
	 * @param value value that is to be mapped to the key.
	 * @throws NullPointerException if key is null.
	 */
	public void put(K key, V value) {
		if(key == null) {
			throw new NullPointerException("Value cannot be mapped to null");
		}
		int hashcode = key.hashCode() % table.length;
		if(hashcode < 0) {
			hashcode *= -1;
		}
		if(this.table[hashcode] == null) {
			table[hashcode] = new TableEntry<>(key, value, null);
			modificationCount++;
		} else {
			TableEntry<K,V> node = this.table[hashcode];
			if(node.getKey().equals(key) == true) {
				node.setValue(value);
				return;
			} else {
				while(node.next != null) {
					if(node.getKey().equals(key) == true) {
						//System.out.println("tu");
						node.setValue(value);
						return;
					}
					node = node.next;
				}
				modificationCount++;
				node.next = new TableEntry<>(key, value, null);
			}
		}
		size++;
		double loadFactor = size / (double)table.length;
		//System.out.println(loadFactor);
		if(loadFactor >= maxLoadFactor) {
			//System.out.println(table.length);
			resize();
			//System.out.println(table.length);
		}
		
	}
	
	/**
	 * Checks if map contain mapping with specified key
	 * @param key key whose presence is to be tested
	 * @returns true if map contains such a key, false otherwise
	 */
	public boolean containsKey(Object key) {
		if(key == null) {
			return false;
		}
		int hashcode = key.hashCode() % table.length;
		if(hashcode < 0) {
			hashcode *= -1;
		}
		TableEntry<K, V> node = this.table[hashcode];
		if(node == null) {
			return false;
		} 
		while(node != null) {
			if(node.getKey().equals(key) == true) {
				return true;
			}
			node = node.next;
		}
		return false;
	}
	
	/**
	 * Checks if in map exists mapping with the specified value. 
	 * @param value value whose presence is to be tested
	 * @returns true if map contains value, else false
	 */
	public boolean containsValue(Object value) {
		for(int i = 0; i < table.length; i++) {
			if(table[i] == null) {
				continue;
			}
			TableEntry<K,V> node = table[i];
			while(node != null) {
				if(value == null) {
					if(node.getValue() == null) {
						return true;
					}
				} else {
					if(node.getValue().equals(value) == true) {
						return true;
					}
				}
				node = node.next;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		int cnt = 0;
		for(int i = 0; i < table.length; i++) {
			TableEntry<K, V> entry = table[i];
			while(entry != null) {
				sb.append(entry.toString());
				cnt++;
				if(cnt < size) {
					sb.append(", ");
				}
				entry = entry.next;
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * Removes all mappings from the map.
	 */
	public void clear() {
		for(int i = 0; i < table.length; i++) {
			table[i] = null;
		}
		size = 0;
		modificationCount++;
	}

	/**
	 * @returns an iterator over elements of type {@link SimpleHashtable.TableEntry<K,V>}
	 */
	@Override
	public Iterator<SimpleHashtable.TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
	@SuppressWarnings("unchecked")
	private void resize() {
		int oldCap = table.length;
		int newCap = 2 * oldCap;
		SimpleHashtable.TableEntry<K,V>[] newTable = (SimpleHashtable.TableEntry<K, V>[]) new SimpleHashtable.TableEntry[newCap];
		for(int i = 0; i < oldCap; i++) {
			if(table[i] == null) {
				continue;
			}
			TableEntry<K,V> tmp = table[i];
			while(tmp != null) {
				TableEntry<K,V> node = new SimpleHashtable.TableEntry<K, V>(tmp.getKey(), tmp.getValue(), null);
				newTable = placeIt(newTable, node);
				tmp = tmp.next;
			}
		}
		table = newTable;
	}
	
	private SimpleHashtable.TableEntry<K,V>[] placeIt(SimpleHashtable.TableEntry<K,V>[] newTable, SimpleHashtable.TableEntry<K, V> node) {
		int newCap = newTable.length;
		int hash = node.getKey().hashCode() % newCap;
		if(hash < 0 ) {
			hash *= -1;
		}
		if(newTable[hash] == null) {
			newTable[hash] = node;
		} else {
			TableEntry<K,V> tmp = newTable[hash];
			while(tmp.next != null) {
				//System.out.println(tmp);
				tmp = tmp.next;
			}
			tmp.next = node;
		}
		return newTable;
	}
	
	/**
	 * Implementation of iterator over hash map. It has methods for checking if there are more elements,
	  for getting nextElement and for removing current element. However, this iterator forbids outside modification of map and throws 
	  {@link ConcurrentModificationException} if that happens. 
	 * @author Andi Škrgat
	 * @version 1.0
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		
		/**
		 * Index of current slot
		 */
		private int i = 0;
		
		/**
		 * Reference to the current entry
		 */
		private TableEntry<K, V> current;
		
		/**
		 * Reference to the next entry. This will be returned when next() is called
		 */
		private TableEntry<K, V> next;
		
		/**
		 * Variable with which we can see, by comparing it with modificationCount, if collection structurally changed
		 */
		private int modCountIterator;
		
		public IteratorImpl() {
			current = null;
			next = null;
			while(table != null && i < table.length && (next = table[i++]) == null) {
			}
			modCountIterator = modificationCount;
		}
		
		/**
		 *@returns true if there are more elements to iterate over, false otherwise. 
		 */
		public boolean hasNext() {
			if(modCountIterator != modificationCount) {
				throw new ConcurrentModificationException("Collection cannot be modified from outside");
			}
			return next != null;
		}
		
		/**
		 * @returns next TableEntry in our implementation of map.
		 * @throws NoSuchElementException if there are no more elements and user has asked for next element
		 */
		public SimpleHashtable.TableEntry<K, V> next() {
			if(modCountIterator != modificationCount) {
				throw new ConcurrentModificationException("Collection cannot be modified from outside");
			}
			if(hasNext() == false) {
				throw new NoSuchElementException("No more elements in collection");
			}
			TableEntry<K,V> temp = next;
			current = next;
			next = next.next;
			if(next == null) {
				do {
					//current = next;
					if(i < table.length) {
						next = table[i++];
					}	
				} while(table != null && i < table.length && next == null);
			} 	
			return temp;
		}
		
		/**
		 * Removes from the collection last element that was returned by method next. This method can be called only once per next().
		 */
		public void remove() {
			if(modCountIterator != modificationCount) {
				throw new ConcurrentModificationException("Collection cannot be modified from outside");
			}
			if(current == null) {
				throw new IllegalStateException("Cannot call remove twice for same element");
			}
			SimpleHashtable.this.remove(current.getKey());
			modCountIterator = modificationCount;
			current = null;
		}
		
		
		
	}
	
	

	
}
