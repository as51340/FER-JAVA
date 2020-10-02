package hr.fer.zemris.java.custom.scripting.nodes;


import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Node representing an entire document. It inherits from node class.
 * @author Andi Škrgat
 * @version 1.0
 */
public class DocumentNode extends Node{
	
	/**
	 * Method used for string representation of whole document in which other nodes could be.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb = intern(this, sb);
		return sb.toString();
	}

	/**
	 * Method that toString uses. It recursively passes children and appending to string builder.
	 * @param child root Node here is this class
	 * @param sb string builder from method toString
	 * @return StringBuilder=class from which we'll get string
	 */
	private StringBuilder intern(Node child, StringBuilder sb) {
		if(child instanceof TextNode) {
			sb.append(((TextNode) child).toString());
		} else if(child instanceof EchoNode) {
			EchoNode echoChild = (EchoNode) child;
			sb.append(echoChild.toString());
		} else if(child instanceof ForLoopNode) {
			ForLoopNode fln = (ForLoopNode) child;
			sb.append(fln.toString());
		}
		int number = 0;
		try {
			number = child.numberOfChildren();
			for(int i = 0; i < number; i++) {
				Node child2 = child.getChild(i);
				if(child2 != null) {
					sb = intern(child2, sb);
				}
			}
		} catch(NullPointerException ex) {
			//System.out.println(ex.getLocalizedMessage()); // null pointer happens because intern collection is allocated only for nodes in which something was placed
		}
		
		if(child instanceof ForLoopNode) {
			sb.append("{$END$}");
		}
		return sb;
	}
	
	/**
	 * @returns true if 2 documents are same. 
	 */
	@Override
	public boolean equals(Object doc) {
		DocumentNode temp;
		if(doc instanceof DocumentNode) {
			temp = (DocumentNode) doc;
		} else {
			return false;
		}
		return internEquals(this.getChild(0), temp.getChild(0));
	}
	
	/**
	 * 
	 * @param a class Node in this case THIS
	 * @param b some other Node with which are comparing
	 * @returns true if two DocumentNodes are same, else otherwise.
	 */
	private boolean internEquals(Node a, Node b) {
		try {
			if(a != null && a.equals(b) == false) {
				return false;
			}	
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		int number = 0;
		try {
			number = a.numberOfChildren();
			System.out.println(number);
			for(int i = 0; i < number; i++) {
				if(internEquals(a.getChild(i), b.getChild(i)) == false) {
					return false;
				}
			}
		} catch(NullPointerException ex) {
			//System.out.println(ex.getLocalizedMessage()); // null pointer happens because intern collection is allocated only for nodes in which something was placed
		}
		//System.out.println("tu");
		return true;
	}
}
	

