package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Class that updates color of current state from context.
 * @author Andi Škrgat
 * @version 1.0
 */
public class ColorCommand implements Command {

	/**
	 * New color for current state
	 */
	private Color color;
	
	/**
	 * Constructor that accepts argument color
	 * @param color Color
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}
	
	/**
	 * Updates color of current state from context
	 * @param ctx Context where current state is placed
	 * @param painter class that is not used in this method
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		state.setColor(color);
	} 
	
}
