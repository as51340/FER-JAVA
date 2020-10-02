package hr.fer.zemris.java.multistack;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class MultistackTest {

	
	@Test
	void test1() { //test null values
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue()); 
		assertEquals(0, v1.getValue());
		assertEquals(null, v2.getValue());
	}
	
	
	@Test
	void test2() {
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue());
		assertEquals(13.0, v3.getValue());
		assertEquals(Double.class, v3.getValue().getClass());
		assertEquals(1, v4.getValue());
		assertEquals(Integer.class, v4.getValue().getClass());
	}
	
	
	@Test
	void test3() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue());
		assertEquals(13, v5.getValue());
		assertEquals(Integer.class, v5.getValue().getClass());
		assertEquals(1, v6.getValue());
		assertEquals(Integer.class, v6.getValue().getClass());
	}
	
	
	@Test
	void test4() {
		ValueWrapper v5 = new ValueWrapper("Ankica");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		assertThrows(RuntimeException.class, () -> {
			v5.add(v6);
		});
	}
	
	
	@Test
	void test5() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		assertEquals(2000, multistack.peek("year").getValue());
		assertEquals(Integer.class, multistack.peek("year").getValue().getClass());
	}
	
	
	@Test
	void test6() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		multistack.push("year", new ValueWrapper(Integer.valueOf(1900)));
		assertEquals(1900, multistack.peek("year").getValue());
		multistack.peek("year").setValue(
				((Integer)multistack.peek("year").getValue()).intValue() + 50
				);
		assertEquals(1950, multistack.peek("year").getValue());
		multistack.pop("year");
		assertEquals(2000, multistack.peek("year").getValue());
	}
	
	
	@Test
	void test7() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		multistack.peek("year").add("5");
		assertEquals(2005, multistack.peek("year").getValue());
		assertEquals(Integer.class, multistack.peek("year").getValue().getClass());
	}
	
	@Test
	void test8() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		multistack.peek("year").divide("5");
		assertEquals(400, multistack.peek("year").getValue());
		assertEquals(Integer.class, multistack.peek("year").getValue().getClass());	
	}
	
	@Test
	void test9() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(22.5);
		multistack.push("year", year);
		multistack.peek("year").divide("3");
		assertEquals(Double.class, multistack.peek("year").getValue().getClass());
		assertEquals(7.5, (Double)multistack.peek("year").getValue(),1e-7);
		multistack.peek("year").divide("3");
		assertEquals(2.5, (Double)multistack.peek("year").getValue(),1e-7);
		multistack.peek("year").add("5.0");
		assertEquals(7.5, (Double)multistack.peek("year").getValue(),1e-7);
	}
	
	@Test
	void test10() {
		ObjectMultistack multistack = new ObjectMultistack();
		assertThrows(NoSuchElementException.class, () -> {
			multistack.pop("year");
		});
	}
	
	@Test
	void test11() {
		ObjectMultistack multistack = new ObjectMultistack();
		assertThrows(NoSuchElementException.class, () -> {
			multistack.peek("year");
		});
	}
	
	@Test
	void test12() {
		ObjectMultistack multistack = new ObjectMultistack();
		assertThrows(NoSuchElementException.class, () -> {
			multistack.pop("year");
		});
		ValueWrapper year = new ValueWrapper(22.5);
		multistack.push("year", year);
		ValueWrapper year1 = new ValueWrapper(1);
		multistack.push("year", year1);
		ValueWrapper year2 = new ValueWrapper(2);
		multistack.push("year", year2);
		ValueWrapper year3 = new ValueWrapper(3);
		multistack.push("year", year3);
		assertEquals(Integer.class, multistack.peek("year").getValue().getClass());
		assertEquals(3, (Integer)multistack.pop("year").getValue(),1e-7);
		assertEquals(Integer.class, multistack.peek("year").getValue().getClass());
		assertEquals(2, (Integer)multistack.pop("year").getValue(),1e-7);
		assertEquals(Integer.class, multistack.peek("year").getValue().getClass());
		assertEquals(1, (Integer)multistack.pop("year").getValue(),1e-7);
		assertEquals(Double.class, multistack.peek("year").getValue().getClass());
		assertEquals(22.5, (Double)multistack.pop("year").getValue(),1e-7);
		assertThrows(NoSuchElementException.class, () -> {
			multistack.pop("year");
		});
	}
	
	@Test
	void test13() {
		ObjectMultistack multistack = new ObjectMultistack();
		ValueWrapper year = new ValueWrapper(2000);
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200.51);
		multistack.push("price", price);
		assertEquals(Integer.class, multistack.peek("year").getValue().getClass());
		assertEquals(2000, multistack.peek("year").getValue());
		assertEquals(Double.class, multistack.peek("price").getValue().getClass());
		assertEquals(200.51, multistack.peek("price").getValue());
		multistack.push("year", new ValueWrapper(Integer.valueOf(1900)));
		assertEquals(Integer.class, multistack.peek("year").getValue().getClass());
		assertEquals(1900, multistack.peek("year").getValue());
		multistack.peek("year").setValue(
				((Integer)multistack.peek("year").getValue()).intValue() + 50
				);
		assertEquals(Integer.class, multistack.peek("year").getValue().getClass());
		assertEquals(1950, multistack.peek("year").getValue());
		multistack.pop("year");
		assertEquals(Integer.class, multistack.peek("year").getValue().getClass());
		assertEquals(2000, multistack.peek("year").getValue());
		multistack.peek("year").add("5");
		assertEquals(Integer.class, multistack.peek("year").getValue().getClass());
		assertEquals(2005, multistack.peek("year").getValue());
		multistack.peek("year").add(5);
		assertEquals(Integer.class, multistack.peek("year").getValue().getClass());
		assertEquals(2010, multistack.peek("year").getValue());
		multistack.peek("year").add(5.0);
		assertEquals(Double.class, multistack.peek("year").getValue().getClass());
		assertEquals(2015.0, multistack.peek("year").getValue());
	}
	
	@Test
	void test14() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(2);
		v1.add(v2.getValue());
		assertEquals(Integer.class, v1.getValue().getClass());
		assertEquals(2, v1.getValue());
		assertEquals(2, v2.getValue());
	}
	
	@Test
	void test15() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(2);
		v2.add(v1.getValue());
		assertEquals(Integer.class, v2.getValue().getClass());
		assertEquals(2, v2.getValue());
		assertEquals(null, v1.getValue());
	}
	
	@Test
	void test16() {
		ValueWrapper v1 = new ValueWrapper("1.4");
		ValueWrapper v2 = new ValueWrapper("2.5");
		v1.add(v2.getValue());
		assertEquals(Double.class, v1.getValue().getClass());
		assertEquals(3.9, v1.getValue());
		assertEquals("2.5", v2.getValue());
	}
	
	@Test
	void test17() {
		ValueWrapper v1 = new ValueWrapper("1.4");
		ValueWrapper v2 = new ValueWrapper(2.5);
		v1.add(v2.getValue());
		assertEquals(Double.class, v1.getValue().getClass());
		assertEquals(3.9, v1.getValue());
		assertEquals(2.5, v2.getValue());
	}
	
	@Test
	void test18() {
		ValueWrapper v1 = new ValueWrapper(1.4);
		ValueWrapper v2 = new ValueWrapper("2.5");
		v1.add(v2.getValue());
		assertEquals(Double.class, v1.getValue().getClass());
		assertEquals(3.9, v1.getValue());
		assertEquals("2.5", v2.getValue());
	}
	
	@Test
	void test19() {
		ValueWrapper v1 = new ValueWrapper(2);
		ValueWrapper v2 = new ValueWrapper("3");
		v1.multiply(v2.getValue());
		assertEquals(Integer.class, v1.getValue().getClass());
		assertEquals(6, v1.getValue());
		assertEquals("3", v2.getValue());
	}
	
	@Test
	void test20() {
		ValueWrapper v1 = new ValueWrapper(3);
		ValueWrapper v2 = new ValueWrapper(2);
		v1.divide(v2.getValue());
		assertEquals(Integer.class, v1.getValue().getClass());
		assertEquals(1, v1.getValue());
		assertEquals(2, v2.getValue());
	}
	
	@Test
	void test21() {
		ValueWrapper v1 = new ValueWrapper(3);
		ValueWrapper v2 = new ValueWrapper(2.0);
		v1.divide(v2.getValue());
		assertEquals(Double.class, v1.getValue().getClass());
		assertEquals(1.5, v1.getValue());
		assertEquals(2.0, v2.getValue());
	}
	
	@Test
	void test22() {
		ValueWrapper v1 = new ValueWrapper(3.0);
		ValueWrapper v2 = new ValueWrapper(2);
		v1.divide(v2.getValue());
		assertEquals(Double.class, v1.getValue().getClass());
		assertEquals(1.5, v1.getValue());
		assertEquals(2, v2.getValue());
	}
	
	@Test
	void test23() {
		ValueWrapper v1 = new ValueWrapper(0);
		ValueWrapper v2 = new ValueWrapper("0");
		v1.multiply(v2.getValue());
		assertEquals(Integer.class, v1.getValue().getClass());
		assertEquals(0, v1.getValue());
		assertEquals("0", v2.getValue());
	}
	
	@Test
	void test24() {
		ValueWrapper v1 = new ValueWrapper("2...2");
		ValueWrapper v2 = new ValueWrapper(5);
		assertThrows(RuntimeException.class, () -> {
			v1.divide(v2.getValue());	
		});
	}
	
	@Test
	void test25() {
		ValueWrapper v1 = new ValueWrapper(234.1);
		ValueWrapper v2 = new ValueWrapper("1.1");
		v1.subtract(v2.getValue());
		assertEquals(Double.class, v1.getValue().getClass());
		assertEquals(233.0, v1.getValue());
		assertEquals("1.1", v2.getValue());
	}
	
	@Test
	void test26() {
		ValueWrapper v1 = new ValueWrapper(234.1);
		ValueWrapper v2 = new ValueWrapper("1.1");
		assertEquals(1, v1.numCompare(v2.getValue()));
	}
	
	@Test
	void test27() {
		ValueWrapper v1 = new ValueWrapper("1.1");
		ValueWrapper v2 = new ValueWrapper("1.1");
		assertEquals(0, v1.numCompare(v2.getValue()));
	}
	
	@Test
	void test28() {
		ValueWrapper v1 = new ValueWrapper("-1.1");
		ValueWrapper v2 = new ValueWrapper("1.1");
		assertEquals(-1, v1.numCompare(v2.getValue()));
	}
	
	@Test
	void test29() {
		ValueWrapper v1 = new ValueWrapper(-1.1);
		ValueWrapper v2 = new ValueWrapper(1.1);
		assertEquals(-1, v1.numCompare(v2.getValue()));
	}
	
	@Test
	void test30() {
		ValueWrapper v1 = new ValueWrapper(-1.1);
		assertEquals(0, v1.numCompare(v1.getValue()));
	}
	

}
