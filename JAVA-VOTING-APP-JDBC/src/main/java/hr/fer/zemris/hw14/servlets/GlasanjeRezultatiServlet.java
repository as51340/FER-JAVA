package hr.fer.zemris.hw14.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Data;

/**
 * Gets all votes from votingDB and forwards request to {@link glasanjeRez.jsp} for rendering results.
 * @author Andi Škrgat
 * @version 1.0
 */
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.getSession().setAttribute("allData", DAOProvider.getDao().getAllData((Long)request.getSession().getAttribute("pollID"), true));
		List<Data> winners = DAOProvider.getDao().getWinners((Long)request.getSession().getAttribute("pollID"));
		request.getSession().setAttribute("winners",  winners);
		request.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(request, response);
	}

}
