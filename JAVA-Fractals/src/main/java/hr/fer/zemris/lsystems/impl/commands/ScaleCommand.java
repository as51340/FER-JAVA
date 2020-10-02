package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class used for updating current effective length of step of current state. Accepts factor from the constructor.
 * @author Andi Škrgat
 * @version 1.0
 */
public class ScaleCommand implements Command {

	/**
	 * Factor with which we'll update current effective length of step for current state
	 */
	private double factor;
	
	/**
	 * Constructor that initializes factor
	 * @param factor new factor of current effective length of step for current state
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}
	
	/**
	 * Updates current effective length of step for current state.
	 * @param ctx Context where current state is placed
	 * @param painter class that is not used in this method
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		currentState.setCurrEffLength(factor);
	}
	
	

}
