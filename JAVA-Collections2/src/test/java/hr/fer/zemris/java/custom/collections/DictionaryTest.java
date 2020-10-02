package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DictionaryTest {

	@Test
	public void test1() { //just some basic conditions to be satisfied
		Dictionary<String, Integer> dic = new Dictionary<String, Integer>();
		assertEquals(false, dic.containsKey("key"));
		assertEquals(0, dic.size()); 
		assertEquals(true, dic.isEmpty());
		assertEquals(null, dic.get("null"));
	}
	
	@Test
	public void test2() { //let's test something normal
		Dictionary<String, Integer> dic = new Dictionary<String, Integer>();
		dic.put("null", null);
		assertEquals(null, dic.get("null"));
		assertEquals(null, dic.get("no-mapping"));
	}
	
	@Test
	public void test3() { //now we are for real
		Dictionary<String, Integer> dic = new Dictionary<String, Integer>();
		dic.put("firstKey", 1);
		assertEquals(1, dic.get("firstKey"));
		dic.put("sndKey", 0);
		assertEquals(0, dic.get("sndKey"));
		dic.put("firstKey", 10);
		assertEquals(10, dic.get("firstKey"));	
	}
	
	@Test
	public void test4() { //show must go on
		Dictionary<String, Integer> dic = new Dictionary<String, Integer>();
		dic.put("firstKey", 1);
		assertEquals(1, dic.get("firstKey"));
		dic.put("sndKey", 0);
		assertEquals(0, dic.get("sndKey"));
		dic.put("firstKey", 10);
		assertEquals(10, dic.get("firstKey"));	
		dic.clear();
		assertEquals(0, dic.size());
		dic.put("firstKey", 1);
		assertEquals(1, dic.get("firstKey"));
		dic.put("sndKey", 0);
		assertEquals(0, dic.get("sndKey"));
		dic.put("firstKey", 10);
		assertEquals(10, dic.get("firstKey"));
	}
	
	@Test
	public void test5() { //walking over firstKey many times..
		Dictionary<String, Integer> dic = new Dictionary<String, Integer>();
		dic.put("firstKey", 1);
		assertEquals(1, dic.get("firstKey"));
		dic.put("firstKey", 2);
		assertEquals(2, dic.get("firstKey"));
		dic.put("firstKey", 3);
		assertEquals(3, dic.get("firstKey"));
	}
	
	@Test
	public void test6() { //oh no key null
		Dictionary<String, Integer> dic = new Dictionary<String, Integer>();
		assertThrows(NullPointerException.class, () -> {
			dic.put(null, 2);
		});
	}
	
	@Test
	public void test7() { //overwriting no problem
		Dictionary<String, Integer> dic = new Dictionary<String, Integer>();
		dic.put("key", 1);
		assertEquals(1, dic.size());
		dic.put("key",2);
		assertEquals(1, dic.size());
	}
	
	@Test
	public void test8() { //overwriting with null
		Dictionary<String, Integer> dic = new Dictionary<String, Integer>();
		dic.put("key", null);
		assertEquals(1, dic.size());
		dic.put("key",2);
		assertEquals(1, dic.size());
	}
	
	@Test
	public void test9() { //isEmpty
		Dictionary<String, Integer> dic = new Dictionary<String, Integer>();
		assertEquals(true, dic.isEmpty());
		dic.put("key", null);
		assertEquals(false, dic.isEmpty());
	}

	

}
