package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.blog.crypto.Crypto;
import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMFProvider;
import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Main servlet that is used for logging into blog for existing user.
 * @author Andi Škrgat
 * @version 1.0
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		List<BlogUser> users = (List<BlogUser>) em.createNamedQuery("getAllUsers").getResultList();
		request.setAttribute("users", users);
		request.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String nick = request.getParameter("nickLogin");
		String password = request.getParameter("passLogin");
		EntityManager em = JPAEMProvider.getEntityManager();
		try {
			Object result = em.createNamedQuery("getUserWithNick").setParameter("nick", nick).getSingleResult();
			String hashPass = Crypto.checksha(password);
			BlogUser user =  (BlogUser) result;
			if(hashPass.equals(user.getPasswordHash()) == false) { //wrong password
				request.setAttribute("text", nick);
				doGet(request, response);
			} else {
				request.getSession().setAttribute("current.user.id", user.getId()); //send session parameters
				request.getSession().setAttribute("current.user.fn", user.getFirstName());
				request.getSession().setAttribute("current.user.ln", user.getLastName());
				request.getSession().setAttribute("current.user.nick", user.getNick());
				response.sendRedirect(request.getContextPath() + "/servleti/main");
			}
		} catch(NoResultException ex) { //no such user, username will not be saved
			doGet(request, response);
		}
	}
	
}
