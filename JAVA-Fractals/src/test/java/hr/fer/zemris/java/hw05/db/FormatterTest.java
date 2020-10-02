package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Does not assert anything just checks if output is okay
 * @author Andi Škrgat
 * @version 1.0
 */
public class FormatterTest {

	
	@Test
	public void test1() {
		List<StudentRecord> list = new ArrayList<StudentRecord>();
		list.add(new StudentRecord("0000000003", "Bosnić", "Andrea", 4));
		List<String> stringList = RecordFormatter.format(list);
		stringList.forEach(System.out::println);
	}
	
	
	@Test
	public void test2() {
		List<StudentRecord> list = new ArrayList<StudentRecord>();
		list.add(new StudentRecord("0000000002", "Bakamović", "Petra", 3));
		list.add(new StudentRecord("0000000003", "Bosnić", "Andrea", 4));
		list.add(new StudentRecord("0000000004", "Božić", "Marin", 5));
		list.add(new StudentRecord("0000000005", "Brezović", "Jusufadis", 2));
		List<String> stringList = RecordFormatter.format(list);
		stringList.forEach(System.out::println);
	}
	
	
	@Test
	public void test3() {
		List<StudentRecord> list = new ArrayList<StudentRecord>();
		list.add(new StudentRecord("0000000002", "Bakamović", "Petra", 3));
		list.add(new StudentRecord("0003", "Bosnić", "Andrea", 4));
		list.add(new StudentRecord("00003", "Božić", "Marin", 5));
		list.add(new StudentRecord("00000005", "Brezović", "Jusufadis", 2));
		List<String> stringList = RecordFormatter.format(list);
		stringList.forEach(System.out::println);
	}
	
	
	@Test
	public void test4() {
		List<StudentRecord> list = new ArrayList<StudentRecord>();
		List<String> stringList = RecordFormatter.format(list);
		stringList.forEach(System.out::println);
	}
}
