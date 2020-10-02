package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * Concrete implementation of IFilter interface with a reference to the list of conditional expressions.
 * @author Andi Škrgat
 * @version 1.0
 */
public class QueryFilter implements IFilter{

	/**
	 * Reference to the list of conditional expressions
	 */
	List<ConditionalExpression> query;
	
	/**
	 * Constructor that sets reference expressions to the given list of expressions
	 * @param expressions
	 */
	public QueryFilter(List<ConditionalExpression> query) {
		this.query = query;
	}
	
	/**
	 * Loops through all ConditionalExpressions and checks for given record if all conditions are met
	 * @param record Student's record that we'll be checked
	 * @returns true if record satisfies all conditions, false otherwise
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		IComparisonOperator oper = null;
		IFieldValueGetter getter = null;
		for(ConditionalExpression ex: query) {
			getter = ex.getFieldGetter();
			oper = ex.getComparisonOperator();
			if(getter == FieldValueGetters.JMBAG) {
				if(oper.satisfied(record.getJmbag(), ex.getStringLiteral()) == false) {
					return false;
				}
			} else if(getter == FieldValueGetters.FIRST_NAME) {
				if(oper.satisfied(record.getFirstName(), ex.getStringLiteral()) == false) {
					return false;
				}
			} else if(getter == FieldValueGetters.LAST_NAME) {
				if(oper.satisfied(record.getLastName(), ex.getStringLiteral()) == false) {
					return false;
				}
			}
		}
		return true;
	}

}
