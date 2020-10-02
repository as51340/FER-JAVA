package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Arrays;

/**
 * Node representing a piece of textual data. Inherits from node class.
 * @author Andi Škrgat
 * @version 1.0
 */
public class TextNode extends Node{
	
	private String text;
	
	/**
	 * @returns private property text
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * Constructor of class TextNode
	 * @param text string that will define text
	 */
	public TextNode(String text) {
		this.text = text;
	}
	
	/**
	 * @returns string representation of ForLoopNode
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < text.length(); i++) {
			if(text.charAt(i) == '\\' || text.charAt(i) == '{') {
				sb.append('\\');
			}
			sb.append(text.charAt(i));
		}
		//System.out.println(sb.toString());
		return sb.toString();
	}
	
	/**
	 * @returns true if 2 TextNodes are same, else otherwise. Here it's just important that they are both instanceof textNodes
	 */
	@Override
	public boolean equals(Object obj) {
		TextNode tx = null;
		if(obj != null && obj instanceof TextNode)  {
			tx = (TextNode) obj;
		} 
		return this.text.equals(tx.text) == true ? true : false;
	}
}
