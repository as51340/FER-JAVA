package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.SmartHttpServer;

/**
 * First function resolvePath from {@linkplain SmartHttpServer} stores parameters in parameters map then this worker is called which sets persistent parameter
 * bgcolor so ./index2.html page's background color will be updated if everything goes as expected.
 * @author Andi Škrgat
 * @version 1.0
 */
public class BgColorWorker implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) throws Exception {
		context.write("<!DOCTYPE html> "
				+ "<html>"
				+ "<body>"
				+ "		<a href=\"../index2.html\"> Link to index2 </a>");
		if(context.getParameter("bgcolor") != null) {
			String param = context.getParameter("bgcolor");
			if(checkValidParam(param) == false) {
				context.write("<h4>Color is not updated</h4>");
			} else {
				context.setPersistentParameter("bgcolor", context.getParameter("bgcolor"));
				context.write("<h4>Color is updated</h4>");
			}
		}
		context.write("</body> </html>");
	}
	
	/**
	 * Checks if parameter obtained from parameters map is valid
	 * @param param
	 * @return
	 */
	private boolean checkValidParam(String param) {
		if(param.length() != 6) {
			return false;
		}
		char[] digits = param.toCharArray();
		for(int i = 0; i < digits.length; i++) {
			if(Character.digit(digits[i],16) == -1) {
				return false;
			}
		}
		return true;
	}

}
