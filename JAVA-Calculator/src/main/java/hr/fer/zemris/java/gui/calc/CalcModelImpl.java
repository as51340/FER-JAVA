package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * Implementation of calculator's model
 * @author Andi Škrgat
 * @version 1.0
 */
public class CalcModelImpl implements CalcModel{
	
	/**
	 * Return true if user can enter one more digit, add decimal point or change sign of number
	 */
	private boolean editable = true;
	
	/**
	 * True if number is negative, false otherwise
	 */
	private boolean negative = false;
	
	/**
	 * Remembers all entered digits from user
	 */
	private String enteredDigits = "";
	
	/**
	 * Current value
	 */
	private double value = 0;
	
	/**
	 * Current value in string
	 */
	private String freezeValue = null;
	
	/**
	 * Current active operand
	 */
	private double activeOperand;
	
	/**
	 * Flag for operand
	 */
	private boolean operandSet = false;
	
	/**
	 * Current pending operation
	 */
	private DoubleBinaryOperator pendingOperation = null;
	
	/**
	 * List of listeners
	 */
	private List<CalcValueListener> listenerList = new ArrayList<CalcValueListener>();

	/**
	 * @return the listenerList
	 */
	public List<CalcValueListener> getListenerList() {
		return listenerList;
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listenerList.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listenerList.remove(l);
	}

	@Override
	public double getValue() {
		double d = value;
		if(negative == true) {
			d *= -1;
		}
		return d;
	}

	@Override
	public void setValue(double value) {
		this.value = value;
		enteredDigits = Double.toString(Math.abs(value));
		editable = false;
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		value = 0;
		enteredDigits = new String("");
		editable = true;
		negative = false;
	}

	/**
	 * @return the negative
	 */
	public boolean isNegative() {
		return negative;
	}

	@Override
	public void clearAll() {
		operandSet = false;
		pendingOperation = null;
		value = 0;
		enteredDigits = new String("");
		freezeValue = null;
		editable = true;
		negative = false;
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if(isEditable() == false) {
			throw new CalculatorInputException("Calculator is not editable, ending operation of sign swaping");
		}
		negative = negative == false ? true : false;
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if(isEditable() == false) {
			throw new CalculatorInputException("Calculator is not editable, ending operation of inserting decimal point");
		}
		if(enteredDigits.contains(".") == true) {
			throw new CalculatorInputException("Decimal point already in number");
		}
		enteredDigits = enteredDigits +".";
		try { 
			value = Double.parseDouble(enteredDigits);
		} catch(NumberFormatException ex) {
			throw new CalculatorInputException("Mistake in implementation, decimal point error");
		}
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if(isEditable() == false) {
			throw new CalculatorInputException("Calculator not editable");
		}
		String tmpString = enteredDigits + Integer.toString(digit);
		try {
			double d = Double.parseDouble(tmpString);
			if(d > Double.MAX_VALUE) {
				throw new CalculatorInputException("Safe with large numbers");
			}
			value = d;
			if(digit == 0) {
				if(enteredDigits.equals("0") == false) {
					enteredDigits = enteredDigits.concat("0");
				}
			} else {
				if(enteredDigits.length() == 1 && enteredDigits.charAt(0) == '0') {
					enteredDigits = new String(Integer.toString(digit));
				} else {
					enteredDigits = enteredDigits.concat(Integer.toString(digit));
				}
			}
		} catch(NumberFormatException ex) {
			throw new CalculatorInputException("Cannot parse number into decimal number");
		}
	}

	@Override
	public boolean isActiveOperandSet() {
		return operandSet;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(isActiveOperandSet() == false) {
			throw new IllegalStateException("Operand has not been set");
		}
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		operandSet = true;
		this.activeOperand = activeOperand;
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = 0;
		operandSet = false;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
	}
	
	/**
	 * @returns true if exists value in calculator
	 */
	public boolean hasFrozenValue() {
		return freezeValue == null ? false : true;
	}
	
	/**
	 * Sets freeze value
	 * @param value new value for freeze value
	 */
	public void freezeValue(String value) {
		this.freezeValue = value;
	}
	
	@Override
	public String toString() {
		if(hasFrozenValue() == false) {
			if(enteredDigits.isEmpty() == true) {
				String ret = "";
				if(negative == true) {
					ret = ret.concat("-");
				}
				return ret.concat("0");
			} else {
				double d = this.getValue();
				String ret = "";
				if(d == 0) {
					String tmp = Double.toString(d);
					int index = tmp.indexOf(".");
					return tmp.substring(0, index);
				}
				if(d < 0) {
					ret = ret.concat("-");
				}
				ret = ret.concat(enteredDigits);
				return ret;
			}
		} 
		String s = freezeValue;
		return s;
	}
	

}
