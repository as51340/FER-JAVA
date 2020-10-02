package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Base class for all graph nodes.
 * @author Andi Škrgat
 * @version 1.0
 */
public class Node {

	ArrayIndexedCollection children = null;
	
	/**
	 * Adds given child to an internally managed collection of children.
	 * @param child node to be added into collection of other siblings.  	
	 */
	public void addChildNode(Node child) {
		if(children == null) {
			children = new ArrayIndexedCollection();
		}

		children.add(child);
	}
	
	/**
	 * @returns a number of direct children. 
	 */
	public int numberOfChildren() {
		return children.size();
	}
	
	/**
	 * @param index of selected child
	 * @returns selected child or throws an appropriate exception if the index is invalid
	 */
	public Node getChild(int index) {
		Node child = null;
		try {
			child = (Node) children.get(index);
		} catch(IndexOutOfBoundsException ex) {
			System.out.println(ex.getMessage());
		}
		return child;
	}
	
}
