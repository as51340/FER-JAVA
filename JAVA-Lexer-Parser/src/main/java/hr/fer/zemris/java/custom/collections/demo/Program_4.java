package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;

/**
 * @author Andi Škrgat
 * Program for demonstration task1 subtask4
 */
public class Program_4 {
	
	public static void main(String[] args) {
		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		
		getter.getNextElement();
		getter.processRemaining(System.out::println);
		
		Collection col1 = new ArrayIndexedCollection();
		col1.add("Ivo");
		col1.add("Ana");
		col1.add("Jasna");
		ElementsGetter getter1 = col1.createElementsGetter();
		
		getter1.getNextElement();
		getter1.processRemaining(System.out::println);
	}
}
