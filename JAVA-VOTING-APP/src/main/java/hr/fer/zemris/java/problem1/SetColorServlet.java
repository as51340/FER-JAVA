package hr.fer.zemris.java.problem1;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Sets background color on all web pages.
 * @author Andi Škrgat
 * @version 1.0
 */
public class SetColorServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession();
		session.setAttribute("pickedBgCol", req.getParameter("pickedBgCol"));
		req.getRequestDispatcher("/colors.jsp").forward(req, res);
	}

}
