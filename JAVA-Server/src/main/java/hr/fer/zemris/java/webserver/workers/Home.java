package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker that works with background color and sets background as temporary parameter so home.smscr can execute this script. Default background color is gray
 * @author Andi Škrgat
 * @version 1.0
 */
public class Home implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) throws Exception {
		if(context.getPersistentParameter("bgcolor") != null) {
			context.setTemporaryParameter("background", context.getPersistentParameter("bgcolor"));
		} else {
			context.setTemporaryParameter("background", "7F7F7F");
		}
		context.getDispatcher().dispatchRequest("/private/pages/home.smscr");
	}

}
