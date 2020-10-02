package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Redirects page to {@linkplain MainServlet}
 * @author Andi Škrgat
 * @version 1.0
 */
@WebServlet("/index.jsp")
public class IndexServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse res) throws IOException {
		res.sendRedirect(request.getContextPath() + "/servleti/main");
	}

}
