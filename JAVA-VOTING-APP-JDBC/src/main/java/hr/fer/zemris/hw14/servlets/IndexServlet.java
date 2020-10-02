package hr.fer.zemris.hw14.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Redirect's response to /servleti/index.html
 * @author Andi Škrgat
 * @version 1.0
 */
@WebServlet("/index.html")
public class IndexServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.sendRedirect(req.getContextPath() + "/servleti/index.html");
	}
	
}
