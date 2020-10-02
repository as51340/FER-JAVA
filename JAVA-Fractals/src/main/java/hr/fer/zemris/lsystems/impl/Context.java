package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * With instances of this class we are able to simulate process of representing fractal.
 * @author Andi Škrgat
 * @version 1.0
 */
public class Context {
	
	/**
	 * Stack that we will use to push some states on it and to get state from top.
	 */
	private ObjectStack<TurtleState> stack;
	
	/**
	 * Empty default constructor
	 */
	public Context() {
		stack = new ObjectStack<TurtleState>();
	}
	
	/**
	 * @returns state from the top
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}
	
	/**
	 * Pushes state to the top.
	 * @param state state that will be put on the top.
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}
	
	/**
	 * Removes one state from the top.
	 */
	public void popState() {
		stack.pop();
	}
	
	

}
