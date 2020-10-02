package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents one simple compiler with subsystems for lexical analysis of query command.
 * @author Andi Škrgat
 * @version 1.0
 */
public class QueryParser {
	
	/**
	 * Subsystem for lexical analysis
	 */
	QueryLexer lexer;
	
	/**
	 * List of all expressions in one query command
	 */
	List<ConditionalExpression> expressions;
	
	/**
	 * Constructor from where tokenization starts.
	 * @param input query command as string
	 */
	public QueryParser(String input) {
		lexer = new QueryLexer(input);
		expressions = new ArrayList<ConditionalExpression>();
		extract();
	}
	
	/**
	 * @return the expressions
	 */
	public List<ConditionalExpression> getQuery() {
		return expressions;
	}

	/**
	 * @returns true if query was in the form jmbag="xxx". This type of query is called direct query
	 */
	public boolean isDirectQuery() {
		if(expressions.size() > 1) {
			return false;
		}
		if(expressions.get(0).getFieldGetter() == FieldValueGetters.JMBAG &&
			expressions.get(0).getComparisonOperator() == ComparisonOperators.EQUALS) {
				return true;
			}
			return false;
	}
	
	/** 
	 * @returns jmbag if query is direct query(in form JMBAG="xxx"), else throws {@link IllegalStateException}
	 */
	public String getQueriedJMBAG() {
		if(isDirectQuery() == false) {
			throw new IllegalStateException("Query is not in direct form");
		}
		return expressions.get(0).getStringLiteral();
	}
	
	/**
	 * Method used for extraction tokens from lexer. 
	 */
	private void extract() {
		Token currToken = null;
		boolean[] arr = {true, false, false, false}; //this array is used to see if extracted token satisfies form of query. 
		//arr[0] = true means that is it okey that attribute is now generated, arr[1] -> operator, arr[2] -> literal, arr[3] -> logical operator
		ConditionalExpression expr = null;
		IFieldValueGetter getter = null;
		IComparisonOperator oper = null;
		while((currToken = lexer.getToken()).getType() != TokenType.EOF) {
			
			if(currToken.getType() == TokenType.ATTRIBUTE) {
				if(arr[0] == false) {
					throw new ParserException("Attribute shouldn't be here");
				}
				if(currToken.getContent().equals("jmbag") == true) {
					getter = FieldValueGetters.JMBAG;
				} else if(currToken.getContent().equals("firstName") == true) {
					getter = FieldValueGetters.FIRST_NAME;
				} else if(currToken.getContent().equals("lastName") == true) {
					getter = FieldValueGetters.LAST_NAME;
				} else {
					throw new ParserException("Wrong format of attribute");
				}
				arr[0] = false;
				arr[1] = true;
				
			} else if(currToken.getType() == TokenType.OPERATOR) {
				if(arr[1] == false) {
					throw new ParserException("Operator shouldn't be here");
				}
				String content = currToken.getContent();
				if(content.equals("=") == true) {
					oper = ComparisonOperators.EQUALS;
				} else if(content.equals("<") == true) {
					oper = ComparisonOperators.LESS;
				} else if(content.equals("<=") == true) {
					oper = ComparisonOperators.LESS_OR_EQUALS;
				} else if(content.equals(">") == true) {
					oper = ComparisonOperators.GREATER;
				} else if(content.equals(">=") == true) {
					oper = ComparisonOperators.GREATER_OR_EQUALS;
				} else if(content.equals("!=") == true) {
					oper = ComparisonOperators.NOT_EQUALS;
				} else if(content.equals("LIKE") == true) {
					oper = ComparisonOperators.LIKE;
				} else {
					throw new ParserException("Wrong format of operator");				
				}
				arr[1] = false;
				arr[2] = true;
				
			} else if(currToken.getType() == TokenType.LITERAL) {
				if(arr[2] == false) {
					throw new ParserException("Literal shouldn't be here");
				}
				String content = currToken.getContent();
				boolean wildcard = false;
				char[] data = content.toCharArray();
				int length = data.length;
				for(int i = 0; i < length; i++) {
					if(data[i] == '*' && wildcard == false) {
						wildcard = true;
					} else if(data[i] == '*' && wildcard == true) {
						throw new ParserException("Two * as wildcards are not allowed");
					} else if(data[i] == '\"') {
						throw new ParserException("Wrong format of literal, escaping and two or more wildards are not allowed");
					}
				}
				if(getter != null && oper != null) {
					expr = new ConditionalExpression(getter, content, oper);
					expressions.add(expr);
				} else {
					throw new ParserException("General error in parsing...");
				}
				arr[2] = false;
				arr[3] = true;
			} else if(currToken.getType() == TokenType.LOGICAL_OPERATOR) {
				if(arr[3] == false) {
					throw new ParserException("Logical operator shouldn't be here");
				}
				String content = currToken.getContent();
				if(content.toLowerCase().equals("and") == true) {
					arr[3] = false;
					arr[0] = true;
				} else {
					throw new ParserException("Logical operator not appropriate");
				}
			}
		}
		
		if(arr[0] == true || arr[1] == true || arr[2] == true) {
			throw new ParserException("Query not finished");
		}
	}

}
