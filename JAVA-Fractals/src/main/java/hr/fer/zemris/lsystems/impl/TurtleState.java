package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * Class that memorizes current position of where turtle is.
 * @author Andi Škrgat
 * @version 1.0
 */
public class TurtleState {
	
	/**
	 * Saves coordinates.
	 */
	private Vector2D turtlePosition;
	
	/**
	 * Saves direction in which turtle "watches".
	 */
	private Vector2D direction;
	
	/**
	 * Current color with which line will be drawn.
	 */
	private Color color;
	
	/**
	 * Current effective length of step.
	 */
	private double currEffLength;
	
	/**
	 * Constructor that initializes properties based on the given state
	 * @param copyState TurtleState from where attributes we'll be taken
	 */
	public TurtleState(TurtleState copyState) {
		this.turtlePosition = copyState.turtlePosition;
		this.direction = copyState.direction;
		this.color = copyState.color;
		this.currEffLength = copyState.currEffLength;
	}
	
	/**
	 * Default empty constructor
	 */
	public TurtleState() {
		
	}
	
	/**
	 * @returns copy of state 
	 */
	public TurtleState copy() {
		return new TurtleState(this);
	}

	/**
	 * @returns the turtlePosition
	 */
	public Vector2D getTurtlePosition() {
		return turtlePosition;
	}

	/**
	 * @param turtlePosition the turtlePosition to set
	 */
	public void setTurtlePosition(Vector2D turtlePosition) {
		this.turtlePosition = turtlePosition;
	}

	/**
	 * @returns the direction
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	/**
	 * @returns the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @returns the currEffLength
	 */
	public double getCurrEffLength() {
		return currEffLength;
	}

	/**
	 * @param currEffLength the currEffLength to set
	 */
	public void setCurrEffLength(double currEffLength) {
		this.currEffLength = currEffLength;
	}
	
	

}
