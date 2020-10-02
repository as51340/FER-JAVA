package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Class that represents skip command to "jump" from one point to another point.
 * @author Andi Škrgat
 * @version 1.0
 */
public class SkipCommand implements Command {

	/**
	 * Length of line that will be drawn
	 */
	private double step;
	
	public SkipCommand(double step) {
		this.step = step;
	}
	
	/**
	 * Jumps from current position to the new position of current state and saves new position as current position in current state
	 * @param ctx Context where current state is placed
	 * @param painter class that is not used in this method
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		Vector2D currPosition = currentState.getTurtlePosition();
		Vector2D newPosition = currPosition.translated(new Vector2D(step * currentState.getDirection().getX(), step * currentState.getDirection().getY()));
		currentState.setTurtlePosition(newPosition);
	}

	
}
