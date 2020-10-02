package hr.fer.zemris.java.calc.listeners;

import hr.fer.zemris.java.gui.calc.CalculatorOperation;
import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Performs sign swap for current number in calculator
 * @author Andi Škrgat
 * @version 1.0
 */
public class SignOperation implements CalculatorOperation{

	@Override
	public void performOperation(CalcModel model) {
		model.swapSign();
	}

}
