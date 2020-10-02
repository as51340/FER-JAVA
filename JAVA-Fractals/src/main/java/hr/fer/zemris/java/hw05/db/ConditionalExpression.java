package hr.fer.zemris.java.hw05.db;

/**
 * Class that represents one expression formed by part of query command
 * @author Andi Škrgat
 * @version 1.0
 */
public class ConditionalExpression {
	
	/**
	 * Getter for some field from student's record
	 */
	private IFieldValueGetter fieldGetter;
	
	/**
	 * Reference to string literal
	 */
	private String stringLiteral;
	
	/**
	 * Reference to concrete implementation of operator
	 */
	private IComparisonOperator comparisonOperator;
	
	/**
	 * Constructor that initializes fields: getter, literal and operator
	 * @param getter IFieldValueGetter for field getting
	 * @param literal String literal
	 * @param operator IComparisonOperator operator for checking some condition between 2 strings
	 */
	public ConditionalExpression(IFieldValueGetter getter, String literal ,IComparisonOperator operator) {
		this.fieldGetter = getter;
		this.stringLiteral = literal;
		this.comparisonOperator = operator;
	}

	/**
	 * @return the getter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * @return the literal
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * @return the operator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
	
	

}
