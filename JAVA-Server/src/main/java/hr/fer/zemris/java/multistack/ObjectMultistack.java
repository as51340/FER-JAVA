package hr.fer.zemris.java.multistack;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


/**
 * Structure that maps string to stack implemented as {@linkplain MultistackEntry}
 * @author Andi Škrgat
 * @version 1.0
 */
public class ObjectMultistack {
	
	/**
	 * Intern map used for mapping
	 */
	private Map<String, MultistackEntry> map;
	
	/**
	 * Constructor that initializes map
	 */
	public ObjectMultistack() {
		map = new HashMap<String, ObjectMultistack.MultistackEntry>();
	}
	
	/**
	 * Adds value to the stack for given key name
	 * @param keyName key in map for finding existing or non existing stack
	 * @param valueWrapper value that will be put into some entry
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {
		if(map.containsKey(keyName) == false) {
			MultistackEntry head = new MultistackEntry();
			head.value = valueWrapper;
			head.next = null;
			map.put(keyName, head);
		} else {
			MultistackEntry currentHead = map.get(keyName);
			MultistackEntry newEntry = new MultistackEntry();
			newEntry.value = valueWrapper;
			newEntry.next = currentHead;
			map.put(keyName, newEntry);
		}
	}
	
	/**
	 * Returns peek element from stack for this key name and removes it 
	 * @param keyName key for finding stack in map
	 * @return peek element
	 */
	public ValueWrapper pop(String keyName) {
		if(map.containsKey(keyName) == false || map.get(keyName) == null) {
			throw new NoSuchElementException("No peek element in stack for given key name");
		} else {
			MultistackEntry currentHead = map.get(keyName);
			ValueWrapper ret = currentHead.value;
			map.put(keyName, currentHead.next);
			currentHead = null;
			return ret;
		}
	}
	
	/**
	 * Returns peek element from stack for this key name 
	 * @param keyName key for finding stack in map
	 * @return peek element
	 */
	public ValueWrapper peek(String keyName) {
		if(map.containsKey(keyName) == false || map.get(keyName) == null) {
			throw new NoSuchElementException("No peek element in stack for given key name");
		} else {
			return map.get(keyName).value;
		}
	}
	
	/**
	 * One entry in our implementation of stack as linked list
	 * @author Andi Škrgat
	 * @version 1.0
	 */
	private static class MultistackEntry {
		
		/**
		 * Value for one entry
		 */
		ValueWrapper value;
		
		/**
		 * Reference to the next entry in linked list
		 */
		MultistackEntry next;
		
	}

}
