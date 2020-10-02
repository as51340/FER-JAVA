package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class SimpleHashtableTest {

	
	@Test
	public void testForBasicWorking() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		assertEquals(2,examMarks.get("Ivana"));
		examMarks.put("Ante", 2);
		assertEquals(2,examMarks.get("Ante"));
		examMarks.put("Jasna", 2);
		assertEquals(2,examMarks.get("Jasna"));
	}
	
	
	@Test
	public void testForGetAndSize() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Kristina", 5);
		assertEquals(5,examMarks.get("Kristina"));
		examMarks.put("Ivana", 5);
		assertEquals(5,examMarks.get("Ivana"));
		assertEquals(2, examMarks.size());
	}
	
	
	@Test
	public void testForIsEmpty() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<>(5);
		assertEquals(true, map.isEmpty());
		map.put("prvi", 4);
		map.put("prvi", 5);
		assertEquals(false, map.isEmpty());
	}
	
	
	@Test
	public void testGeneralPutInGetOut() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<>(5);
		map.put("prvi", 4);
		map.put("druga", 5);
		assertEquals(4, map.get("prvi"));
		assertEquals(2, map.size());
		assertEquals(5,map.get("druga"));
		map.remove("prvi");
		assertEquals(1, map.size());
		map.remove("druga");
		assertEquals(0, map.size());
		map.put("prvi", 4);
		assertEquals(4, map.get("prvi"));
		assertEquals(1, map.size());
		assertEquals(true, map.containsValue(4));
	}
	
	
	@Test
	public void testKeyNoNull() { //null game
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>(5);
		assertThrows(NullPointerException.class, () -> {
			map.put(null, 99);
		});
	}
	
	@Test
	public void testGetNull() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>(5);
		map.put(2, null);
		assertEquals(null, map.get(2));
	}
	
	@Test
	public void testForConstainsKeyTrue() {
		SimpleHashtable<String, String> map = new SimpleHashtable<>(5);
		map.put("ključ", "brava");
		assertEquals(true, map.containsKey("ključ"));
		map.remove("ključ");
		assertEquals(false, map.containsKey("ključ"));
	}
	
	
	@Test
	public void testForRemovingNull() { //remove can be null
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>(5);
		map.put(2,2);
		map.remove(null);
	}
	
	
	@Test
	public void testForNoSuchKey() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>(5);
		assertEquals(null, map.get("nijedobroakovratinenull"));
	}
	
	@Test
	public void testForGetNull() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>(5);
		assertEquals(null, map.get(null)); //legalno nelegalno
	}
	
	
	@Test
	public void testForContainsKey() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>(5);
		assertEquals(false, map.containsKey(null));
	}
	
	
	@Test
	public void testForContainsValue() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>(5);
		assertEquals(false, map.containsValue(null));
	}
	
	
	@Test
	public void testForTestingMapToString() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(5);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 1);
		assertEquals("[Ante=2, Ivana=2, Jasna=2, Kristina=1]", examMarks.toString());
	}
	
	
	@Test
	public void testForTestingToStringForEntries() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		String[] arr = {("Ante => 2") , ("Ivana => 5"), ("Jasna => 2"), ("Kristina => 5")};
		int i = 0;
		for(SimpleHashtable.TableEntry<String,Integer> pair : examMarks) {
			StringBuilder sb = new StringBuilder(pair.getKey() + " => " + pair.getValue());
			assertEquals(arr[i++], sb.toString());
		}
		
	}
	
	
	@Test
	public void testForCartesianProduct() { 
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Ivana", 5);
		String[] arr ={("(Ante => 2) - (Ante => 2)"), ("(Ante => 2) - (Ivana => 5)"), ("(Ivana => 5) - (Ante => 2)"), ("(Ivana => 5) - (Ivana => 5)")};
		int i = 0;
		for(SimpleHashtable.TableEntry<String,Integer> pair : examMarks) {
			for(SimpleHashtable.TableEntry<String, Integer> pair2: examMarks) {
				StringBuilder sb = new StringBuilder();
				sb.append("(" +pair.getKey() + " => " + pair.getValue() +") - (" + pair2.getKey() + " => " + pair2.getValue() + ")");
				assertEquals(arr[i++], sb.toString());
				//System.out.println(pair);
				//System.out.println(pair2);
			}
			
		}
	}
	
	
	@Test
	public void testForRemovingIterator() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			//System.out.println(pair);
			if(pair.getKey().equals("Ivana")) {
				iter.remove();
			}
		}
		assertEquals(3, examMarks.size());
		/*Iterator<SimpleHashtable.TableEntry<String,Integer>> iter1 = examMarks.iterator();
		while(iter1.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair1 = iter1.next();
			//System.out.println(pair1);
			/*if(pair.getKey().equals("Ivana")) {
				iter.remove();
			}
		}*/
	}
	
	
	@Test
	public void testForDoubleRemovingWhileIterating() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		assertThrows(IllegalStateException.class, () -> {
			while(iter.hasNext()) {
				SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
				if(pair.getKey().equals("Ivana")) {
						iter.remove();
						iter.remove();
				}
			}
		});
	}
	
	
	@Test
	public void testForToStringAndRemovingFromIterator() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		String[] arr = {("Ante => 2") , ("Ivana => 5"), ("Jasna => 2"), ("Kristina => 5")};
		int i = 0;
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			StringBuilder sb = new StringBuilder();
			sb.append(pair.getKey() + " => " + pair.getValue());
			assertEquals(arr[i++], sb.toString());
			//System.out.println(pair);
			iter.remove();
		}
		assertEquals(0, examMarks.size());
	}
	
	
	@Test
	public void testForMapRemovingWhileIterating() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); 	
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator();
		assertThrows(ConcurrentModificationException.class, () -> {
			while(iter.hasNext()) {
				SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
				if(pair.getKey().equals("Ivana")) {
					examMarks.remove("Ivana");
				}
			}
		});
	}

	
	@Test
	public void testForTwoIteratorsAndOneRemoving17() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); 	
		examMarks.put("Marko", 1);
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter1 = examMarks.iterator();
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter2 = examMarks.iterator();
		assertThrows(ConcurrentModificationException.class, () -> {
			while(iter1.hasNext() == true) {
				while(iter2.hasNext() == true) {
					SimpleHashtable.TableEntry<String,Integer> pair1 = iter1.next();
					//SimpleHashtable.TableEntry<String,Integer> pair2 = iter2.next();
					if(pair1.getKey().equals("Ivana")) {
						iter1.remove();
					}
				}
			}

		});
		assertEquals("[Marko=1, Ante=2, Jasna=2, Kristina=5]", examMarks.toString());
	}
	
	
	@Test
	public void testForResizing18() { //here is no actual test, it's only used to see if everything will go without errors or infinite loops
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(4);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
//		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter1 = examMarks.iterator();
//		while(iter1.hasNext() == true) {
//			System.out.println(iter1.next());
//		} 
		//here is first time resized 3/4 = 0.75
		examMarks.clear();
		assertEquals(0, examMarks.size());
		examMarks.put("Valnea", 5);
		examMarks.put("Giovanni", 6);
		examMarks.put("Halid", 1);
		//here is second time resized 7/8 > 0.7
		examMarks.put("Italija", 99);
	}
	
	
	@Test
	public void testForInitialization() {
		assertThrows(IllegalArgumentException.class, () -> {
			SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(-2);
		});
	}
	
	
	@Test
	public void testForRemove() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<>(100);
		map.put("Indija", 11);
		map.put("Kina", 12);
		map.put("zemlja", 6);
		map.put("vrijeme", 9);
		map.put("karantena", null);
		map.remove("Indija");
		assertEquals(null, map.get("Indija"));
	}
	
	@Test
	public void testForNoSuchElementException() {
		SimpleHashtable<String, Integer> map = new SimpleHashtable<>(100);
		map.put("Indija", 11);
		map.put("Kina", 12);
		map.remove("Indija");
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter1 = map.iterator();
		iter1.next();
		assertThrows(NoSuchElementException.class, () -> {
			iter1.next();
		});
	}
	

}
