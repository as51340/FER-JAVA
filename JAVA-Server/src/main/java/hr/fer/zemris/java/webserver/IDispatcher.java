package hr.fer.zemris.java.webserver;

/**
 * Dispatchs web requests from client
 * @author Andi Škrgat
 * @version 1.0
 */
public interface IDispatcher {
	
	/**
	 * Performs request dispatching
	 * @param urlPath url of request
	 * @throws Exception 
	 */
	void dispatchRequest(String urlPath) throws Exception;
}
