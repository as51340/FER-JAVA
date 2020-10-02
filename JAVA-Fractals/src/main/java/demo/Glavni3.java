package demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Test example
 * @author Andi Škrgat
 * @version 1.0
 */
public class Glavni3 {
	
	/**
	 * Main method from where program starts
	 * @param args arguments from console
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new)	;
	}

}
