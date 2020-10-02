package hr.fer.zemris.java.custom.collections;

/**
 * Class processor is a model of an object capable of performing some operation, it also represents a conceptual contract between clients and concrete processor.
 * @author Andi Škrgat
 * @version 2.0
 */
public interface Processor {
	
	/**
	 * Method does not do anything.
	 * @param value processor will process object value in some way
	 */
	public void process(Object value);
}