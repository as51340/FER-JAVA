package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Interface that is used for performing operations on calculator model
 * @author Andi Škrgat
 * @version 1.0
 */
public interface CalculatorOperation {
	
	/**
	 * Concrete operation that will be executed
	 * @param model
	 */
	void performOperation(CalcModel model);

}
