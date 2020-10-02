package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Represents command that is used for pushing states to the stack of context 
 * @author Andi Škrgat
 * @version 1.0
 */
public class PushCommand implements Command{

	/**
	 * Method copies peek state and pushes copy to the stack of context
	 * @param ctx Context where current state is placed
	 * @param painter class that we don't use in this method
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState copyState = ctx.getCurrentState();
		ctx.pushState(copyState.copy());
	}
	

}
