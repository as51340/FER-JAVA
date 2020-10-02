package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;

/**
 * A node representing a command which generates some textual output dynamically. It inherits from node class.
 * @author Andi Škrgat
 * @version 1.0
 */
public class EchoNode extends Node{
	
	private Element[] elements;

	/**
	 * @return the elements
	 */
	public Element[] getElements() {
		return elements;
	}

	/**
	 * Constructor that initializes elements of EchoNode
	 * @param elements array of elements
	 */
	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}
	
	/**
	 * @returns string representation of class EchoNode
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{$= ");
		for (Element el: elements) {
			if(el != null) {
				sb.append(el.asText());
				sb.append(" ");
			}
		}
		sb.append("$}");
		return sb.toString();
	}
	
	/**
	 * @returns true if 2 echoNodes are same, else otherwise. Here it's just important that they are both instanceof EchoNode
	 */
	@Override
	public boolean equals(Object obj) {
		EchoNode eh;
		if(obj instanceof EchoNode)  {
			eh = (EchoNode) obj;
		} else {
			return false;
		}
		for(int i = 0; i < eh.numberOfChildren(); i++) {
			if(eh.getChild(i).equals(this.getChild(i)) == false) {
				return false;
			}
		}
		return true;
	}
	
	
	
}
