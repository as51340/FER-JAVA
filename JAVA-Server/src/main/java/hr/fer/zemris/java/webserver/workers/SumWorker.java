package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Worker that accepts two parameters, calcualates their sum and places it as temporary parameter into {@linkplain RequestContext},
 * If user did not provide values for these 2 parameters 1 and 2 as default will be used. It also creates temporary parameters varA and varB->
 * parameters that are used as well as imgName. Then calls dispatcher for {@linkplain RequestContext} and dispatcher generates
 * required HTML
 * @author Andi Škrgat
 * @version 1.0
 */
public class SumWorker implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) throws Exception {
		int varA, varB, zbrojInt;
		varA = returnValue("a", context);
		varB = returnValue("b", context);
		zbrojInt = varA + varB;
		String zbroj = Integer.toString(zbrojInt);
		context.setTemporaryParameter("varA",Integer.toString(varA));
		context.setTemporaryParameter("varB",Integer.toString(varB));
		context.setTemporaryParameter("zbroj", zbroj);
		if(zbrojInt % 2 == 0) {
			context.setTemporaryParameter("imgName", "images/kobe.png");
			context.setTemporaryParameter("link", "\"https://www.biography.com/athlete/kobe-bryant\"");
		} else {
			context.setTemporaryParameter("imgName", "images/lebron.gif");
			context.setTemporaryParameter("link", "http://www.lebronjames.com/");
		}
		context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
	}
	
	
	/**
	 * Private method used for calculating what values are going to be used for arguments in sum
	 * @param name a or b
	 * @param context server's response
	 * @return value: 1 if user didn't provide value for arg a or value user provided
	 */
	private int returnValue(String name, RequestContext context) {
		if(context.getParameter(name) == null) {
			return 1;
		} else {
			try {
				int varA = Integer.parseInt(context.getParameter(name));
				return varA;
			} catch(NumberFormatException ex) {
				return 1;
			}
		}
	}

}
