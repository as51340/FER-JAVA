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
import hr.fer.zemris.java.hw14.model.Poll;

/**
 * Reads item's name from votingDB database and sends them to {@link glasanjeIndex.jsp}
 * @author Andi Škrgat
 * @version 1.0
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		long pollID = Long.parseLong(request.getParameter("pollID"));
		Poll poll = (Poll) DAOProvider.getDao().getPoll(pollID);
		request.getSession().setAttribute("poll", poll);
		List<Data> allData = DAOProvider.getDao().getAllData(pollID, false);
		request.getSession().setAttribute("allData", allData);
		request.getSession().setAttribute("pollID", pollID);
		request.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(request, response);
	}

}
