package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Concrete implementation of command that is used for removing peek element from context
 * @author Andi Škrgat
 * @version 1.0
 */
public class PopCommand implements Command{

	/**
	 * With this command, one state has been removed from the top
	 * @param ctx Context where current state is placed
	 * @param painter class that we don't use in this method
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}
	
	
}
