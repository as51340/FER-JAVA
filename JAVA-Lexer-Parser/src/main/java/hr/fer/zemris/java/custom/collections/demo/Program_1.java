package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;

/**
 * @author Andi Škrgat
 * Program for demonstration - task1 subtask1
 */
public class Program_1 {

	public static void main(String[] args) {
		Collection col1 = new LinkedListIndexedCollection();
		Collection col2 = new LinkedListIndexedCollection();
		col1.add("Ivo");
		col1.add("Ana");
		col1.add("Jasna");
		col2.add("Jasmina");
		col2.add("Štefanija");
		col2.add("Karmela");
	
		
		ElementsGetter getter1 = col1.createElementsGetter();
		ElementsGetter getter2 = col1.createElementsGetter();
		ElementsGetter getter3 = col2.createElementsGetter();
		col1.add("Ja");
		
		System.out.println(getter1.hasNextElement());
		System.out.println(getter1.getNextElement());
		System.out.println(getter1.hasNextElement());
		System.out.println(getter1.getNextElement());
		System.out.println(getter1.hasNextElement());
		System.out.println(getter1.getNextElement());
		System.out.println(getter1.hasNextElement());
		System.out.println(getter1.getNextElement());
		
		
		/*System.out.println(getter1.hasNextElement());
		System.out.println(getter1.hasNextElement());
		System.out.println(getter1.hasNextElement());
		System.out.println(getter1.hasNextElement());
		System.out.println(getter1.getNextElement());
		System.out.println(getter1.hasNextElement());
		System.out.println(getter1.hasNextElement());
		System.out.println(getter1.getNextElement());
		System.out.println(getter1.hasNextElement());
		System.out.println(getter1.hasNextElement());
		System.out.println(getter1.hasNextElement());
		System.out.println(getter1.getNextElement());
		System.out.println(getter1.hasNextElement());
		System.out.println(getter1.hasNextElement());*/
		
		/*System.out.println(getter1.getNextElement());
		System.out.println(getter1.getNextElement());
		System.out.println(getter2.getNextElement());
		System.out.println(getter3.getNextElement());
		System.out.println(getter3.getNextElement());*/
		
		/*Collection col1 = new ArrayIndexedCollection();
		Collection col2 = new ArrayIndexedCollection();
		col1.add("Ivo");
		col1.add("Ana");
		col1.add("Jasna");
		col2.add("Jasmina");
		col2.add("Štefanija");
		col2.add("Karmela");
	
		
		ElementsGetter getter1 = col1.createElementsGetter();
		ElementsGetter getter2 = col1.createElementsGetter();
		ElementsGetter getter3 = col2.createElementsGetter();
		col1.add("Ja");
		
		System.out.println(getter1.hasNextElement());
		System.out.println(getter1.getNextElement());
		System.out.println(getter1.hasNextElement());
		System.out.println(getter1.getNextElement());
		System.out.println(getter1.hasNextElement());
		System.out.println(getter1.getNextElement());
		System.out.println(getter1.hasNextElement());
		System.out.println(getter1.getNextElement());
		
		
		/*System.out.println(getter1.hasNextElement());
		System.out.println(getter1.hasNextElement());
		System.out.println(getter1.hasNextElement());
		System.out.println(getter1.hasNextElement());
		System.out.println(getter1.getNextElement());
		System.out.println(getter1.hasNextElement());
		System.out.println(getter1.hasNextElement());
		System.out.println(getter1.getNextElement());
		System.out.println(getter1.hasNextElement());
		System.out.println(getter1.hasNextElement());
		System.out.println(getter1.hasNextElement());
		System.out.println(getter1.getNextElement());
		System.out.println(getter1.hasNextElement());
		System.out.println(getter1.hasNextElement());*/
		
		/*System.out.println(getter1.getNextElement());
		System.out.println(getter1.getNextElement());
		System.out.println(getter2.getNextElement());
		System.out.println(getter3.getNextElement());
		System.out.println(getter3.getNextElement());*/
		

		
	}

}
