package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ArrayIndexedCollectionTest {

	@Test
	public void defaultConstructorTest1() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		assertEquals(0, col.size());		
	}
	
	@Test
	public void defaultConstructorTest2() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			col.get(0);
		});
	}
	
	@Test 
	public void initialConstructorTest1() {
		assertThrows(IllegalArgumentException.class, () -> {
			ArrayIndexedCollection col = new ArrayIndexedCollection(0);
		});
	}
	
	@Test
	public void colDefaultConstructorTest1() {
		assertThrows(NullPointerException.class, () ->{
			ArrayIndexedCollection col = new ArrayIndexedCollection(null);
		});
	}
	
	@Test
	public void colDefaultConstructorTest2() {
		Collection temp = new ArrayIndexedCollection();
		temp.add("New York");
		temp.add("LA");
		temp.add(10);
		ArrayIndexedCollection col2 = new ArrayIndexedCollection(temp);
		assertEquals("New York", col2.get(0));
		assertEquals("LA", col2.get(1));
		assertEquals(10, col2.get(2));
		assertThrows(IndexOutOfBoundsException.class,() -> {
			col2.get(3);
		});
		assertEquals(3, col2.size());
	}
	
	@Test
	public void colInitialConstructorTest1() {
		assertThrows(NullPointerException.class, () ->{
			ArrayIndexedCollection col = new ArrayIndexedCollection(null);
		});
	}
	
	@Test
	public void colInitialConstructorTest2() {
		Collection temp = new ArrayIndexedCollection();
		temp.add("New York");
		temp.add("LA");
		temp.add(10);
		ArrayIndexedCollection col2 = new ArrayIndexedCollection(temp);
		assertEquals("New York", col2.get(0));
		assertEquals("LA", col2.get(1));
		assertEquals(10, col2.get(2));
		assertThrows(IndexOutOfBoundsException.class,() -> {
			col2.get(3);
		});
		assertEquals(3, col2.size());
	}
	
	@Test
	public void addTest1() {
		assertThrows(NullPointerException.class, () -> {
			ArrayIndexedCollection col1 = new ArrayIndexedCollection();
			col1.add(null);
		});
	}
	
	@Test
	public void addTest2() {
		ArrayIndexedCollection col2 = new ArrayIndexedCollection();
		col2.add("NewElement");
		assertEquals("NewElement", col2.get(0));
	}
	
	@Test
	public void addTest3() {
		ArrayIndexedCollection col3 = new ArrayIndexedCollection(2);
		col3.add("LA");
		col3.add("Ontario");
		col3.add("Michigan");
		assertEquals("Michigan", col3.get(2));
	}
	
	@Test
	public void getTest1() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			ArrayIndexedCollection col = new ArrayIndexedCollection();
			col.get(-1);
		});
	}
	
	@Test
	public void getTest2() {
		ArrayIndexedCollection list = new ArrayIndexedCollection();
		list.add("value");
		assertEquals("value", list.get(0));
	}
	
	@Test
	public void clearTest1() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("LA");
		col.clear();
		assertEquals(0, col.size());
	}
	
	@Test
	public void insertTest1() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(2);
		assertThrows(IndexOutOfBoundsException.class, () -> {
			col.insert("novi_objekt",2);
		});
	}
	
	@Test
	public void insertTest2() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(2);
		col.add(3);
		col.add("java");
		col.add("test");
		col.insert("objekt", 2);
		assertEquals(3, col.get(1));
		assertEquals("objekt", col.get(2));
		assertEquals("test", col.get(4));
	}
	
	@Test
	public void insertTest3() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(2);
		col.add(3);
		col.add("java");
		col.add("test");
		col.insert("objekt", 2);
		assertEquals("objekt", col.get(2));
	}
	
	@Test
	public void insertTest4() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(2);
		col.add(3);
		col.add("java");
		col.add("test");
		col.insert("objekt", 2);
		assertEquals("test", col.get(4));
	}
	
	
	@Test
	public void indexTest1() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		assertEquals(-1,col.indexOf(null));
	}
	
	@Test
	public void indexTest2() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(1);
		col.add(1);
		col.add(1);
		assertEquals(0, col.indexOf(1));
	}
	
	@Test
	public void removeTest1() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> {
			col.remove(24);
		});
	}
	
	@Test
	public void removeTest2() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(1);
		col.add(2);
		col.add(3);
		col.remove(0);
		assertEquals(3,col.get(1));
	}
	
	@Test
	public void removeTest3() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(1);
		col.add(2);
		col.add(3);
		col.remove(2);
		assertEquals(2, col.get(1));
		
	}
}
