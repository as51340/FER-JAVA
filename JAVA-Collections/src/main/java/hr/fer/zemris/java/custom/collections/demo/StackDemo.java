package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Application that evaluates expression accepted by command-line in postfix notation. It only works with integers.
 * @author Andi Škrgat
 * @version 1.0
 */
public class StackDemo {
	
	/**
	 * Main method, application starts from here. 
	 * @param args single command-line argument which should be evaluated
	 */
	public static void main(String[] args) {
		ObjectStack os = new ObjectStack();
		if(args.length != 1) {
			System.out.println("Wrong input, exiting...");
			System.exit(0);
		}
		String[] arr = args[0].split(" ");
		for(String s: arr) {
			Integer num;
			try {
				
				try {
					num = Integer.parseInt(s);
					os.push(num);
				} catch(NumberFormatException ex) {
					
					if(s.equals("+") == true) {
						Object a1 = os.pop();
						Object a2 = os.pop();
						os.push((Integer)a1 + (Integer)a2);
					}
					else if(s.equals("-") == true) {
						Object a1 = os.pop();
						Object a2 = os.pop();
						os.push((Integer) a2 - (Integer)a1);
					}
					else if(s.equals("*") == true) {
						Object a1 = os.pop();
						Object a2 = os.pop();
						os.push((Integer)a1 *(Integer)a2);
					}
					else if(s.equals("/") == true) {
						Object a1 = os.pop();
						Object a2 = os.pop();
						int a = (Integer) a1;
						if(a == 0) {
							throw new IllegalArgumentException("Dividing with zero is not allowed");
						}
						os.push((Integer)a2 / (Integer) a1);
					}
					else if(s.equals("%") == true) {
						Object a1 = os.pop();
						Object a2 = os.pop();
						os.push((Integer)a2 % (Integer)a1);
					}
					else {
						throw new IllegalArgumentException("Operator not recognized.");
					}
				}
			} catch(IllegalArgumentException | EmptyStackException ex) {
				System.out.println(ex.getMessage());
				break;
			}
		}
		
		if(os.size() != 1) {
			System.out.println("Error occurred, evaluating not possible.");
		}
		else {
			System.out.println("Expression evaluated to " + os.pop() + ".");
		}
	}
}