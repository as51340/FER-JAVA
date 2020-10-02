package hr.fer.zemris.hw14.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;

/**
 * Updates number of votes for selected item in database votingDB.
 * @author Andi Škrgat
 * @version 1.0
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		long id = Long.parseLong(request.getParameter("id"));
		long pollID = (Long)request.getSession().getAttribute("pollID");
		DAOProvider.getDao().updateData(id, pollID);
		response.sendRedirect(request.getContextPath() + "/servleti/glasanje-rezultati");
	}
	
}
