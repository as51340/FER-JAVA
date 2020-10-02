package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LinkedListIndexedCollectionTest {

	@Test
	public void defaultConstructorTest() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		assertEquals(0, list.size());
	}
	
	@Test
	public void colConstructorTest() {
		Collection temp = new LinkedListIndexedCollection();
		temp.add(1);
		temp.add("dva");
		LinkedListIndexedCollection list = new LinkedListIndexedCollection(temp);
		assertEquals("dva", list.get(1));
	}
	
	@Test
	public void addTest1() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, () -> {
			list.add(null);
		});
	}
	
	@Test
	public void addTest2() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add("prvi");
		list.add("zadnji");
		assertEquals("zadnji", list.get(1));
	}
	
	@Test
	public void addTest3() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add("prvi");
		list.add("drugi");
		assertEquals("prvi", list.get(0));
	}
	
	@Test
	public void getTest1() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			LinkedListIndexedCollection col = new LinkedListIndexedCollection();
			col.get(-1);
		});
	}
	
	@Test
	public void getTest2() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add("value");
		assertEquals("value", list.get(0));
	}
	
	@Test
	public void clearTest() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add("temp");
		list.add("virus");
		list.add("karantena");
		list.clear();
		assertEquals(0, list.size());
	}
	
	@Test
	public void insertTest1() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add("temp");
		list.add("virus");
		list.add("karantena");
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.insert("my_object", 4);
		});
	}
	
	@Test
	public void insertTest2() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add("temp");
		list.add("virus");
		list.add("karantena");
		assertThrows(NullPointerException.class, () -> {
			list.insert(null, 3);
		});
	}
	
	@Test
	public void insertTest3() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add("temp");
		list.add("virus");
		list.add("karantena");
		assertThrows(IndexOutOfBoundsException.class, () -> {
			list.insert("my_object", 4);
		});
	}
	
	@Test
	public void insertTest4() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add("temp");
		list.add("virus");
		list.add("karantena");
		list.insert("NOVI", 1);
		assertEquals("NOVI", list.get(1));
	}
	
	@Test
	public void insertTest5() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add("temp");
		list.add("virus");
		list.add("karantena");
		list.insert("NOVI", 1);
		assertEquals("virus", list.get(2));
	}
	
	@Test
	public void insertTest6() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add("temp");
		list.add("virus");
		list.add("karantena");
		list.insert("NOVI", 1);
		assertEquals("karantena", list.get(3));
	}
	
	@Test
	public void insertTest7() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add("temp");
		list.add("virus");
		list.add("karantena");
		list.insert("NOVI", 1);
		assertEquals("temp", list.get(0));
	}
	
	@Test
	public void insertTest8() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add("novo");
		list.insert("novo_novo", 1);
		assertEquals(2, list.size());
	}
	
	@Test
	public void insertTest9() {
		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.add("novo");
		list.insert("novo_novo", 0);
		assertEquals("novo_novo", list.get(0));
	}
	
	
	
	@Test
	public void indexTest1() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		assertEquals(-1,col.indexOf(null));
	}
	
	@Test
	public void indexTest2() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(1);
		col.add(1);
		col.add(1);
		assertEquals(0, col.indexOf(1));
	}
	
	@Test
	public void removeTest1() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(1);
		col.add(2);
		col.add(3);
		col.remove(0);
		assertEquals(2,col.get(0));
	}
	
	@Test
	public void removeTest2() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(1);
		col.add(2);
		col.add(3);
		col.remove(1);
		assertEquals(3,col.get(1));
	}
	
	@Test
	public void removeTest3() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(1);
		col.add(2);
		col.add(3);
		col.remove(2);
		assertEquals(2,col.get(1));
	}
	
	
}
