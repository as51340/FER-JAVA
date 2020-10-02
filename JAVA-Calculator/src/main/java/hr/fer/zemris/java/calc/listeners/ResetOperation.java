package hr.fer.zemris.java.calc.listeners;

import hr.fer.zemris.java.gui.calc.CalculatorOperation;
import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Resets calculator
 * @author Andi Škrgat
 * @version 1.0
 */
public class ResetOperation implements CalculatorOperation{

	@Override
	public void performOperation(CalcModel model) {
		model.clearAll();
	}

}
