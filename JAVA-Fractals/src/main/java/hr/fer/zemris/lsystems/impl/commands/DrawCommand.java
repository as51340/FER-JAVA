package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Concrete implementation of drawing command; accepts step from the constructor.
 * @author Andi Škrgat
 * @version 1.0
 */
public class DrawCommand implements Command{
	
	/**
	 * Length of line that we will draw
	 */
	private double step;
	
	/**
	 * Constructor that initializes step property
	 * @param step length of one step
	 */
	public DrawCommand(double step) {
		this.step = step;
	}
	
	/**
	 * Draws line with the color of current state of context from current position to the calculated one and saves newPosition to the current state.
	 * @param ctx Context where current state is placed
	 * @param painter class that use in this example for drawing lines
	 */
	@Override 
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		Vector2D currPosition = currentState.getTurtlePosition();
		Vector2D newPosition = currPosition.translated(new Vector2D(currentState.getCurrEffLength() * step * currentState.getDirection().getX(), currentState.getCurrEffLength() * step * currentState.getDirection().getY()));
		painter.drawLine(currPosition.getX(), currPosition.getY(), newPosition.getX(), newPosition.getY(), currentState.getColor(), 1);
		currentState.setTurtlePosition(newPosition);
	}

}
