package hr.fer.zemris.java.multistack;

/**
 * Class that represents value used as one value for entry in linked list for {@linkplain ObjectMultistack}. It has methods for performing
 * arithmetic operation and all of them are modifying current value. For all arithmetic operations, at the moment of the method call,
 * current value and the given argument can only be null, {@linkplain Integer}, {@linkplain Double} or {@linkplain String}
 * @author Andi Škrgat
 * @version 1.0
 */
public class ValueWrapper {

	/**
	 * Value that can be changed or only used in some calculations
	 */
	private Object value;
	
	/**
	 * Constructor that accepts single value for this wrapper
	 * @param value existing value
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	/**
	 * @param flag1 true if value is double
	 * @param flag2 true if incValue is double
	 * @param incValue value of the other object
	 * @param op 0 sum,1 sub, 2 mul, 3 div
	 */
	private int performOperation(boolean numTypeCurr, boolean numTypeArg, Object incValue, int op) {
		boolean intDouble = false;
		if(numTypeCurr == false && numTypeArg == false) {
			intDouble = false;
		} else { 
			if(numTypeCurr == true && numTypeArg == false) {
				incValue = Double.valueOf((Integer) incValue);
			} else if(numTypeArg == true && numTypeCurr == false) {
				value =  Double.valueOf((Integer) value);
			}
			intDouble = true;
		}
		if(intDouble == true) {
			if(op == 0) {
				value = ((Double)incValue) + ((Double)value);
			} else if(op == 1) {
				value = ((Double)value) - ((Double)incValue);
			} else if(op == 2) {
				value = ((Double)incValue) * ((Double)value);
			} else if(op == 3){
				value = ((Double)value) / ((Double)incValue);
			} else {
				return ((Double)value).compareTo((Double)incValue);
			}
		} else {
			if(op == 0) {
				value = ((Integer)incValue) + ((Integer)value);
			} else if(op == 1) {
				value = ((Integer)value) - ((Integer)incValue);
			} else if(op == 2) {
				value = ((Integer)incValue) * ((Integer)value);
			} else if(op == 3){
				value = ((Integer)value) / ((Integer)incValue);
			} else {
				return ((Integer)value).compareTo((Integer)incValue);
			}
		}
		return -1;
	}
	
	/**
	 * Arithmetic operation of adding
	 * @param incValue value that will be added to the current value
	 */
	public void add(Object incValue) {
		value = checkForNull(value);
		value = checkForString(value);
		boolean numTypeCurr = checkNumerical(value);
		incValue = checkForNull(incValue);
		incValue = checkForString(incValue);
		boolean numTypeArg = checkNumerical(incValue);
		performOperation(numTypeCurr, numTypeArg, incValue, 0);
	}

	/**
	 * Arithmetic operation of subtraction
	 * @param decValue value that will be subtracted from the current value
	 */
	public void subtract(Object decValue) {
		value = checkForNull(value);
		value = checkForString(value);
		boolean numTypeCurr = checkNumerical(value);
		decValue = checkForNull(decValue);
		decValue = checkForString(decValue);
		boolean numTypeArg = checkNumerical(decValue);
		performOperation(numTypeCurr, numTypeArg, decValue, 1);
	}
	
	/**
	 * Arithmetic operation of multiplication
	 * @param mulValue value that will be multiplied with the current value
	 */
	public void multiply(Object mulValue) {
		value = checkForNull(value);
		value = checkForString(value);
		boolean numTypeCurr = checkNumerical(value);
		mulValue = checkForNull(mulValue);
		mulValue = checkForString(mulValue);
		boolean numTypeArg = checkNumerical(mulValue);
		performOperation(numTypeCurr, numTypeArg, mulValue, 2);
	}
	
	/**
	 * Arithmetic operation of dividing
	 * @param divValue current value will be divided with this value
	 */
	public void divide(Object divValue) {
		value = checkForNull(value);
		value = checkForString(value);
		boolean numTypeCurr = checkNumerical(value);
		divValue = checkForNull(divValue);
		divValue = checkForString(divValue);
		boolean numTypeArg = checkNumerical(divValue);
		performOperation(numTypeCurr, numTypeArg, divValue, 3);
	}
	
	/**
	 * Checks if value if is double or integer. If value is double then everything must be calculated as double
	 * @param value value that will be checked
	 * @return true if value is double false if integer
	 * @throws RuntimeException if it is thrown that means that something wasn't implemented in the right way
	 */
	private boolean checkNumerical(Object value) {
		if(value instanceof Double) {
			return true;
		} else if(value instanceof Integer) {
			return false;
		} else {
			throw new RuntimeException("Wrong implementation, please check");
		}
	}
	
	/**
	 * Checks if value is instance of String and if it can be parsed to numerical type-double or integer
	 * @param value that will be checked
	 * @return casted version of value if it can be parsed
	 */
	private Object checkForString(Object value) {
		if(value instanceof String) {
			String tmp = (String) value;
			if(tmp.contains(".") == true || tmp.contains("E") == true) {
				try {
					return Double.parseDouble(tmp);
				} catch(NumberFormatException ex) {
					throw new RuntimeException("Conversation from string to double failed");
				}
			} else {
				try {
					return Integer.parseInt(tmp);
				} catch(NumberFormatException ex) {
					throw new RuntimeException("Conversation from string to integer failed");
				}
			}
		} else {
			return value;
		}
	} 
	
	/**
	 * Cheks if given value is null
	 * @param value value that will be checkhed
	 * @return 0 is value is null otherwise returns value
	 */
	private Object checkForNull(Object value) {
		if(value == null) {
			return 0;
		}
		return value;
	}
	
	/**
	 * Compares current value with argument withValue
	 * @param withValue Object with which current value will be compared
	 * @return {@linkplain Integer} less than zero if the currently stored values is smaller than the argument, an {@linkplain Integer} greater
	 * than zero if the currently stored value is larger than the argument, or 0 if they are equal
	 */
	public int numCompare(Object withValue) {
		value = checkForNull(value);
		value = checkForString(value);
		boolean numTypeCurr = checkNumerical(value);
		withValue = checkForNull(withValue);
		withValue = checkForString(withValue);
		boolean numTypeArg = checkNumerical(withValue);
		return performOperation(numTypeCurr, numTypeArg, withValue, 4);
	}
}
