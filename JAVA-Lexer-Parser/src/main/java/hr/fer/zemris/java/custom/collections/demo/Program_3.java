package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;

/**
 * @author Andi Škrgat
 * Program for demonstration task1 subtask3
 */
public class Program_3 {

	public static void main(String[] args) {
		Collection col = new LinkedListIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		
		System.out.println(getter.getNextElement());
		System.out.println(getter.getNextElement());
		
		col.clear();
		System.out.println(getter.getNextElement());
		
		Collection col1 = new ArrayIndexedCollection();
		col1.add("Ivo");
		col1.add("Ana");
		col1.add("Jasna");
		ElementsGetter getter1 = col1.createElementsGetter();
		
		System.out.println(getter1.getNextElement());
		System.out.println(getter1.getNextElement());
		
		col1.clear();
		System.out.println(getter1.getNextElement());

	}

}
