package hr.fer.zemris.hw14.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll;

/**
 * Retrieves all polls from votingDB database and forwards request to {@link polls.jsp} for rendering to user 
 * @author Andi Škrgat
 * @version 1.0
 */
@WebServlet("/servleti/index.html")
public class PollsServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		List<Poll> polls = DAOProvider.getDao().getPolls();
		req.getSession().setAttribute("polls", polls);
		req.getRequestDispatcher("/WEB-INF/pages/polls.jsp").forward(req, res);
	}

	
}
