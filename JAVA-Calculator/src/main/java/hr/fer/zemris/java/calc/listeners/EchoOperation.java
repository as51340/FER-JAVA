package hr.fer.zemris.java.calc.listeners;

import java.util.function.DoubleBinaryOperator;
import hr.fer.zemris.java.gui.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.CalculatorOperation;
import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Job that will be done when "=" is pressed
 * @author Andi Škrgat
 * @version 1.0
 */
public class EchoOperation implements CalculatorOperation{

	@Override
	public void performOperation(CalcModel model) {
		CalcModelImpl model2 = ((CalcModelImpl) (model));
		if(model2.getPendingBinaryOperation() == null) {
			return;
		}
		DoubleBinaryOperator currentOper = model2.getPendingBinaryOperation();
		double activeOperand = model2.getActiveOperand();
		double value = model2.getValue();
		double result = currentOper.applyAsDouble(activeOperand, value);
		model2.setValue(result);
		model2.setPendingBinaryOperation(null);
		model2.clearActiveOperand();
		model2.freezeValue(null);
//		System.out.println(model.toString());
	}

}
