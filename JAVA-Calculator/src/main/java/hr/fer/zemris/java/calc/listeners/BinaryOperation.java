package hr.fer.zemris.java.calc.listeners;

import java.util.function.DoubleBinaryOperator;
import hr.fer.zemris.java.gui.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.CalculatorOperation;
import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Implementation of calculator's listener that schedules operations to be executed or executes one if it hasn't been before
 * @author Andi Škrgat
 * @version 1.0
 */
public class BinaryOperation implements CalculatorOperation{

	/**
	 * Reference to the concrete operation
	 */
	private DoubleBinaryOperator op;
	
	/**
	 * @param op the op to set
	 */
	public void setOp(DoubleBinaryOperator op) {
		this.op = op;
	}

	@Override
	public void performOperation(CalcModel model) {
		CalcModelImpl model2 = ((CalcModelImpl) (model));
		if(model2.hasFrozenValue() == true) {
			throw new CalculatorInputException("Frozen value has been set");
		}
		if(model2.getPendingBinaryOperation() == null) {
			if(model2.isActiveOperandSet() == false) {
				model2.setActiveOperand(model.getValue());
			} 
			model2.setPendingBinaryOperation(op);
			model2.freezeValue(model2.toString());
			model2.clear();
		} else {
			double param1 = model2.getActiveOperand();
			DoubleBinaryOperator oldOp = model2.getPendingBinaryOperation();
			double param2 = model2.getValue();
			double result = oldOp.applyAsDouble(param1, param2);
			model2.setActiveOperand(result);
			model2.setValue(result);
			model2.setPendingBinaryOperation(op);
			model2.freezeValue(model2.toString());
			model2.clear();
		}
		
	}

}
