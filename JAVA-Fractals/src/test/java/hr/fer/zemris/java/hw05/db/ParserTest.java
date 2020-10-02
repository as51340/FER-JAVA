package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ParserTest {

	
	@Test
	public void test1() {
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		assertEquals(true, qp1.isDirectQuery()); // true
		assertEquals("0123456789", qp1.getQueriedJMBAG());
		assertEquals(1, qp1.getQuery().size());
	}
	
	
	@Test
	public void test2() {
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		assertEquals(false,  qp2.isDirectQuery()); // false
		assertThrows(IllegalStateException.class, ()-> {
			qp2.getQueriedJMBAG();
		});
		assertEquals(2, qp2.getQuery().size()); // 2
	}
	
	
	@Test
	public void test3() {
		QueryParser qp1 = new QueryParser(" jmbag =\"0123456789\" ");
		assertEquals(FieldValueGetters.JMBAG, qp1.getQuery().get(0).getFieldGetter());
		assertEquals(ComparisonOperators.EQUALS, qp1.getQuery().get(0).getComparisonOperator());
		
	}
	
	
	@Test
	public void test4() {
		QueryParser qp2 = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		assertEquals(FieldValueGetters.JMBAG, qp2.getQuery().get(0).getFieldGetter());
		assertEquals(ComparisonOperators.EQUALS, qp2.getQuery().get(0).getComparisonOperator());
		assertEquals("0123456789", qp2.getQuery().get(0).getStringLiteral());
		assertEquals(FieldValueGetters.LAST_NAME, qp2.getQuery().get(1).getFieldGetter());
		assertEquals("J", qp2.getQuery().get(1).getStringLiteral());
	}
	
	
	@Test
	public void test5() {
		QueryParser qp = new QueryParser("   jmbag   =   \"232\"   ANd  jmbag = \"21\"");
		assertEquals(false, qp.isDirectQuery());
		assertEquals(2, qp.getQuery().size());
		assertEquals(FieldValueGetters.JMBAG, qp.getQuery().get(0).getFieldGetter());
		assertEquals("232", qp.getQuery().get(0).getStringLiteral());
		assertEquals(FieldValueGetters.JMBAG, qp.getQuery().get(1).getFieldGetter());
		assertEquals(ComparisonOperators.EQUALS, qp.getQuery().get(1).getComparisonOperator());
		assertEquals(ComparisonOperators.EQUALS, qp.getQuery().get(1).getComparisonOperator());
	}

	
	@Test
	public void test6() {
		QueryParser qp = new QueryParser("   firstName   !=   \"Giuseppe\"   aNd  jmbag <= \"21\" and lastName >= \"Corleone\"");
		assertEquals(false, qp.isDirectQuery());
		assertEquals(3, qp.getQuery().size());
		assertEquals(FieldValueGetters.FIRST_NAME, qp.getQuery().get(0).getFieldGetter());
		assertEquals(ComparisonOperators.NOT_EQUALS, qp.getQuery().get(0).getComparisonOperator());
		assertEquals("Giuseppe", qp.getQuery().get(0).getStringLiteral());
		assertEquals(FieldValueGetters.JMBAG, qp.getQuery().get(1).getFieldGetter());
		assertEquals(ComparisonOperators.LESS_OR_EQUALS, qp.getQuery().get(1).getComparisonOperator());
		assertEquals("21", qp.getQuery().get(1).getStringLiteral());
		assertEquals(FieldValueGetters.LAST_NAME, qp.getQuery().get(2).getFieldGetter());
		assertEquals(ComparisonOperators.GREATER_OR_EQUALS, qp.getQuery().get(2).getComparisonOperator());
		assertEquals("Corleone", qp.getQuery().get(2).getStringLiteral());
	}
	
	
	@Test
	public void test7() {
		QueryParser qp = new QueryParser("   firstName   LIKE   \"Giuseppe\"");
		assertEquals(false, qp.isDirectQuery());
		assertEquals(1, qp.getQuery().size());
		assertEquals(FieldValueGetters.FIRST_NAME, qp.getQuery().get(0).getFieldGetter());
		assertEquals(ComparisonOperators.LIKE, qp.getQuery().get(0).getComparisonOperator());
		assertEquals("Giuseppe", qp.getQuery().get(0).getStringLiteral());
	}
	
	
	@Test
	public void test8() {
		assertThrows(ParserException.class, () -> {
			QueryParser qp = new QueryParser("   firstName   like   \"Giuseppe\"");
		});
	}
	
	
	@Test
	public void test9() {
		assertThrows(ParserException.class, () -> {
			QueryParser qp = new QueryParser("   ime   =   \"Giuseppe\"");
		});
	}
	
	@Test
	public void test10() {
		assertThrows(ParserException.class, () -> {
			QueryParser qp = new QueryParser("   firstName   LIKE   \"Gi*usepp*e\"");
		});
	}
	
	
	@Test
	public void test11() {
		assertThrows(ParserException.class, () -> {
			QueryParser qp = new QueryParser("   firstName   LIKE   \"Giusepp\"e\"");
		});
	}
	
	
	@Test
	public void test12() {
		assertThrows(ParserException.class, () -> {
			QueryParser qp = new QueryParser("   firstName   LIKE   \"Giusepp\"e\" and ");
		});
	}
	
	
	@Test
	public void test13() {
		assertThrows(ParserException.class, () -> {
			QueryParser qp = new QueryParser("   firstName=lastName");
		});
	}

	
	@Test
	public void test14() {
		assertThrows(ParserException.class, () -> {
			QueryParser qp = new QueryParser("   \"Hadžiselimović\"=lastName");
		});
	}
	
	
	@Test
	public void test15() {
		assertThrows(ParserException.class, () -> {
			QueryParser qp = new QueryParser("firstName=\"Luka\" ande su u južnoj americi ");
		});
	}
	
	@Test
	public void test16() {
		assertThrows(ParserException.class, () -> {
			QueryParser qp = new QueryParser("query firstName=\"Luka\"");
		});
	}
}
