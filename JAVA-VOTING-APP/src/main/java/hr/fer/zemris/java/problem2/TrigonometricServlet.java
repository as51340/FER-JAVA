package hr.fer.zemris.java.problem2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet calculates sinus and cosinus for all values between specified url paramaters a and b. Rendering is then forwarded to jsp file.
 * @author Andi Škrgat
 * @version 1.0
 */
public class TrigonometricServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int a = req.getParameter("a") == null ? 0 : Integer.parseInt(req.getParameter("a"));
		int b = req.getParameter("b") == null ? 360 : Integer.parseInt(req.getParameter("b"));
		if(a > b) {
			int tmp = a;
			a = b;
			b = tmp;
		}
		if(b > a + 720) {
			b = a + 720;
		}
		List<Double> listTrigSin = new ArrayList<Double>();
		List<Double> listTrigCos = new ArrayList<Double>();
		List<Integer> values = new ArrayList<Integer>();
		for(int i = a; i <= b; i++) {
			double deg = ((double) i / (double) 360) * (2*Math.PI);
			listTrigSin.add(Math.sin(deg));
			listTrigCos.add(Math.cos(deg));
			values.add(i);
		}
		HttpSession session = req.getSession();
		session.setAttribute("dataSin", listTrigSin);
		session.setAttribute("dataCos", listTrigCos);
		session.setAttribute("dataValues", values);
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, res);
	}

}
