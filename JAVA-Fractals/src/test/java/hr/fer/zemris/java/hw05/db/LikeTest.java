package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class LikeTest {

	
	@Test
	public void test1() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(false, oper.satisfied("Zagreb", "Aba*")); // false
		
	}
	
	
	@Test
	public void test2() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(false,oper.satisfied("AAA", "AA*AA")); // false
	}

	
	@Test
	public void test3() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(true, oper.satisfied("AAAA", "AA*AA")); // true
	}
	
	
	@Test
	public void test4() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(true, oper.satisfied("Bosnić", "Bos*"));
	}
	
	
	@Test
	public void test5() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(true, oper.satisfied("Bosnić", "Bo*ć"));
	}
	
	
	@Test
	public void test6() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(true, oper.satisfied("Bosnić", "*Bosnić"));
	}
	
	
	@Test
	public void test7() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(true, oper.satisfied("AAA", "A*A"));
	}	
	
	
	@Test
	public void test8() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(true, oper.satisfied("Bosnić", "*ić"));
	}
	
	
	@Test
	public void test9() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(false, oper.satisfied("Bosnić", "Lukson*ić"));
	}
	
	@Test
	public void test10() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(false, oper.satisfied("rep", "ep*"));
	}
	
	
}
