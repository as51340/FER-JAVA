package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Command performs rotation of current turtle state for given angle
 * @author Andi Škrgat
 * @version 1.0
 */
public class RotateCommand implements Command{
	
	/**
	 * Rotation angle in radians
	 */
	private double angle;
	
	/**
	 * Constructor that initializes property angle
	 * @param angle angle in degrees
	 */
	public RotateCommand(double angle) {
		this.angle = Math.toRadians(angle);
	}
	
	/**
	 * Rotates peek state from the stack of context for property angle
	 * @param ctx Context where current state is placed
	 * @param painter class that we don't use in this method
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState peekState = ctx.getCurrentState();
		Vector2D oldDirection = peekState.getDirection();
		Vector2D newDirection = oldDirection.rotated(this.angle);
		peekState.setDirection(newDirection);
		
		
	}
	
	
	
}
