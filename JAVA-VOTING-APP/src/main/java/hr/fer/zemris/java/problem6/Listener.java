package hr.fer.zemris.java.problem6;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Stores application creation time as attribute in context map
 * @author Andi Škrgat
 * @version 1.0
 */
@WebListener
public class Listener implements ServletContextListener{

	/**
	 * {@inheritDoc}
	 * Stores creation time of this web app
	 */
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("time", System.currentTimeMillis());
	}

	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
