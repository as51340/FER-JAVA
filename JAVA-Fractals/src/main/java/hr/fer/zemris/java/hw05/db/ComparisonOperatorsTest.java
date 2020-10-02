package hr.fer.zemris.java.hw05.db;

public class ComparisonOperatorsTest {
	public static void main(String[] args) {
		IComparisonOperator oper = ComparisonOperators.LESS;
		System.out.println(oper.satisfied("Ana", "Jasna")); // true, since Ana < Jasna
	}
	

}
