package hr.fer.zemris.java.webserver;

/**
 * An interface toward any object that can process current request: it gets RequestContext as parameter and it is expected to create a content for client.
 * @author Andi Škrgat
 * @version 1.0
 */
public interface IWebWorker {
	
	/**
	 * Creates content for client
	 * @param context reference to the {@linkplain RequestContext}
	 * @throws Exception
	 */
	public void processRequest(RequestContext context) throws Exception;

}
