package demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;

/**
 * Test example
 * @author Andi Škrgat
 * @version 1.0
 */
public class Glavni1 {

	/**
	 * Main method from where program starts
	 * @param args arguments from console
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve(LSystemBuilderImpl::new));
	}
	
	/**
	 * Method for initializing LSystemBuilder
	 * @param provider for creating LSystemBuilder
	 * @returns LSystem that represents one Lindermayer's system
	 */
	private static LSystem createKochCurve(LSystemBuilderProvider provider) {
		return provider.createLSystemBuilder()
		.registerCommand('F', "draw 1")
		.registerCommand('+', "rotate 60")
		.registerCommand('-', "rotate -60")
		.setOrigin(0.05, 0.4)
		.setAngle(0)
		.setUnitLength(0.9)
		.setUnitLengthDegreeScaler(1.0/3.0)
		.registerProduction('F', "F+F--F+F")
		.setAxiom("F")
		.build();
	}

}
