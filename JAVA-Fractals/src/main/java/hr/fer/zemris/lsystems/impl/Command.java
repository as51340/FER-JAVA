package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Represents allowed actions that turtle can take.	
 * @author Andi Škrgat
 * @version 1.0
 */
public interface Command {
	
	/**
	 * Method that will execute some command in concrete implementation.
	 * @param ctx Context from where we'll get some state or to which we'll put some state etc.
	 * @param painter class used for drawing objects.
	 */
	void execute(Context ctx, Painter painter);
}
