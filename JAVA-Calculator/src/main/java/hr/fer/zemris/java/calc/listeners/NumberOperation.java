package hr.fer.zemris.java.calc.listeners;

import hr.fer.zemris.java.gui.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.CalculatorOperation;
import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Performs inserting digit into calculator
 * @author Andi Škrgat
 * @version 1.0
 */
public class NumberOperation implements CalculatorOperation{

	private int digit;
	
	@Override
	public void performOperation(CalcModel model) {
		model.insertDigit(digit);
		CalcModelImpl model2 = ((CalcModelImpl) model);
		model2.freezeValue(null);
	}

	/**
	 * @param digit the digit to set
	 */
	public void setDigit(int digit) {
		this.digit = digit;
	}

}
