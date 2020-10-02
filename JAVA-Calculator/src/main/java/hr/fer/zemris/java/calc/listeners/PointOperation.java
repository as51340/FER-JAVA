package hr.fer.zemris.java.calc.listeners;

import hr.fer.zemris.java.gui.calc.CalculatorOperation;
import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Inserts decimal point in calculator
 * @author Andi Škrgat
 * @version 1.0
 */
public class PointOperation implements CalculatorOperation{

	@Override
	public void performOperation(CalcModel model) {
		model.insertDecimalPoint();
	}

}
