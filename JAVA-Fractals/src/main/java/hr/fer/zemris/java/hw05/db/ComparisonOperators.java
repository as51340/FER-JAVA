package hr.fer.zemris.java.hw05.db;

/**
 * Class the has all possible concrete implementations of {@link IComparisonOperator}
 * @author Andi Škrgat
 * @version 1.0
 */
public class ComparisonOperators {
	
	/**
	 * Comparator used to check if first string is alphabetically less than second one.
	 */
	public static final IComparisonOperator LESS = (a, b) -> (a.compareTo(b) < 0 ? true : false);
	
	/**
	 * Comparator used to check if first string is alphabetically less or equal comparing to second one.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (a, b) -> (a.compareTo(b) <= 0 ? true : false);
	
	/**
	 * Comparator used to check if first string is alphabetically greater than second one.
	 */
	public static final IComparisonOperator GREATER = (a, b) -> (a.compareTo(b) > 0 ? true : false);
	
	/**
	 * Comparator used to check if first string is alphabetically greater or equal than second one.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (a, b) -> (a.compareTo(b) >= 0 ? true : false);
	
	/**
	 * Comparator checks if 2 string are equal
	 */
	public static final IComparisonOperator EQUALS = (a, b) -> (a.equals(b));
	
	/**
	 * Comparator checks if 2 string are not equal
	 */
	public static final IComparisonOperator NOT_EQUALS = (a, b) -> (!a.equals(b));
	
	/**
	 * Operator like is usually used with wildcard char * so we check if value1 has same structure of word as value2
	 */
	public static final IComparisonOperator LIKE = (value1, value2) -> {
		
		if(value2.contains("*") == false) {
				return value1.equals(value2);
		}
		if(value2.length() -1 > value1.length() ) {
			return false;
		}
		int index = value2.indexOf('*');
		String like_begin = value2.substring(0, index);
		String word_begin = value1.substring(0, index);
		if(index == value2.length() -1) {
			return like_begin.equals(word_begin);
		}
		String like_end = value2.substring(index +1);
		int size = like_end.length();
		String word_end = value1.substring(value1.length()- size);
		return like_begin.equals(word_begin) && like_end.equals(word_end);
		
	};
}
