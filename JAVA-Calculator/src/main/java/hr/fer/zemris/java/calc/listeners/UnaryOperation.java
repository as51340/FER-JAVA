package hr.fer.zemris.java.calc.listeners;

import java.util.function.DoubleUnaryOperator;
import hr.fer.zemris.java.gui.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.CalculatorOperation;
import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

public class UnaryOperation implements CalculatorOperation{

	/**
	 * Regular function
	 */
	private DoubleUnaryOperator normal;
	
	/**
	 * Inverz function
	 */
	private DoubleUnaryOperator inverz;
	
	/**
	 * Flag for inverz-is it set
	 */
	private boolean inverzSet = false;
	
	@Override
	public void performOperation(CalcModel model) {
		DoubleUnaryOperator current = inverzSet == false ? normal : inverz;
		CalcModelImpl model2 = (CalcModelImpl) model;
		if(model2.hasFrozenValue() == true) {
			throw new CalculatorInputException("Frozen value has been set");
		}
		double val = model2.getValue();
		if(model2.isNegative() == true) {
			model2.swapSign();
		}
		model2.setValue(current.applyAsDouble(val));	
	}

	/**
	 * @param inverzSet the inverzSet to set
	 */
	public void setInverzSet(boolean inverzSet) {
		this.inverzSet = inverzSet;
	}
	
	/**
	 * Sets functions that will be used for all unary operations
	 * @param normal
	 * @param inverz
	 */
	public void setFunctions(DoubleUnaryOperator normal, DoubleUnaryOperator inverz) {
		this.normal = normal;
		this.inverz = inverz;
	}
	
	
}
