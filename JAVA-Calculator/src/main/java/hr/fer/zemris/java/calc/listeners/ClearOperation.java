package hr.fer.zemris.java.calc.listeners;

import hr.fer.zemris.java.gui.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.CalculatorOperation;
import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Clears current value from calculator
 * @author Andi Škrgat
 * @version 1.0
 */
public class ClearOperation implements CalculatorOperation{

	@Override
	public void performOperation(CalcModel model) {
		model.clear();
		((CalcModelImpl) model).freezeValue(null);
	}

}
