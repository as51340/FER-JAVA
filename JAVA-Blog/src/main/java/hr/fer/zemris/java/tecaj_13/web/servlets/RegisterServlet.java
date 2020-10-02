package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

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
 * Performs user registration
 * @author Andi Škrgat
 * @version 1.0
 */
@WebServlet(urlPatterns = {"/servleti/register"})
public class RegisterServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String name = request.getParameter("nameRegister");
		String surname = request.getParameter("surnameRegister");
		String email = request.getParameter("emailRegister");
		String nick = request.getParameter("nickRegister");
		String password = request.getParameter("passRegister");
		String passHash = Crypto.checksha(password);
		EntityManager em1 = JPAEMProvider.getEntityManager();
		try {
			Object result = em1.createNamedQuery("getUserWithNick").setParameter("nick", nick).getSingleResult();	
			response.sendRedirect(request.getContextPath() + "/servleti/main");
		} catch(NoResultException ex) {
			EntityManager em2 = JPAEMProvider.getEntityManager();
			BlogUser newUser = new BlogUser(name, surname, nick, email, passHash);
			em2.persist(newUser);
			response.sendRedirect(request.getContextPath() + "/servleti/main");
		}
	}
}
