package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMFProvider;
import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet adds new comments, entries and edits already existing.
 * @author Andi Škrgat
 * @version 1.0
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1l;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String[] arrURI = uri.split("/");
		int length = arrURI.length;
		if(length == 4 && arrURI[3].equals("author") == true) {
			response.sendRedirect(request.getContextPath() + "/servleti/main");
		} else {
			EntityManager em1 = null;
			BlogUser user = null;
			try { //first check if nick is valid
				em1 = JPAEMProvider.getEntityManager();
				user = (BlogUser) em1.createNamedQuery("getUserWithNick").setParameter("nick", arrURI[4]).getSingleResult();
			} catch(Exception ex) { // if not send redirect	
				response.sendRedirect(request.getContextPath() + "/servleti/main");
				return;
			}
			if(length == 6) {
				if(arrURI[5].equals("new") == true) { //path will be something like /servleti/author/NICK/new
					request.getRequestDispatcher("/WEB-INF/pages/new.jsp").forward(request, response); //create new entry only if user is logged in, that has to be okey
					//because we'll get here from link on author.jsp and there is checked if user is logged
				} else if(arrURI[5].equals("edit") == true) { //path will be something like /servleti/author/NICK/edit
					request.getRequestDispatcher("/WEB-INF/pages/edit.jsp").forward(request, response);
				}else {
					try {
						long eid = Long.parseLong(arrURI[5]);
						EntityManager em = JPAEMProvider.getEntityManager();
						BlogEntry entry = (BlogEntry) em.createNamedQuery("getEntryWithID").setParameter("id", eid).getSingleResult();
						EntityManager em0 = JPAEMProvider.getEntityManager();
						List<BlogComment> comments = (List<BlogComment>) em0.createNamedQuery("getAllCommentsWithEntry").setParameter("entry", entry).getResultList();
						request.setAttribute("comments", comments);
						request.getSession().setAttribute("entry", entry);
						request.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(request, response);
					} catch(NumberFormatException ex) { //path not familiar redirect to the main page
						response.sendRedirect(request.getContextPath() + "/servleti/main");
					}
				}
			}
			 else { //path will be something like /servleti/author/NICK	
				EntityManager em2 = JPAEMProvider.getEntityManager();
				List<BlogEntry> entries = (List<BlogEntry>) em2.createNamedQuery("getEntriesUser").setParameter("user", user).getResultList();
				request.setAttribute("entries", entries);
				request.setAttribute("nick", arrURI[4]); //this is because and annonymous users can also see its entries
				request.getRequestDispatcher("/WEB-INF/pages/author.jsp").forward(request, response);
			}
		}
	}
	
	@Override 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String valuePage = request.getParameter("pageName");
		String entryTitle = request.getParameter("entryTitle");
		String entryText = request.getParameter("entryText");
		BlogUser creator = null;
		String loggedInNickname = (String)request.getSession().getAttribute("current.user.nick"); //it has to be logged in
		EntityManager em1 = JPAEMProvider.getEntityManager();
		creator = (BlogUser)em1.createNamedQuery("getUserWithNick").setParameter("nick", loggedInNickname).getSingleResult();
		BlogEntry entry = null;
		Date date = new Date();
		if(valuePage.equals("comment") == true) {
			EntityManager em = JPAEMProvider.getEntityManager();
			BlogEntry blogEntry = (BlogEntry)request.getSession().getAttribute("entry");
			BlogComment comment = new BlogComment();
			comment.setBlogEntry(blogEntry);
			comment.setPostedOn(new Date());
			comment.setUsersEMail(creator.getEmail());
			String mess = request.getParameter("comment");
			comment.setMessage(mess);
			em.persist(comment);
			response.sendRedirect(request.getContextPath() + "/servleti/author/" + creator.getNick());
		} else if(valuePage.equals("editEntryPage") == true) {
			EntityManager em = JPAEMProvider.getEntityManager();
			entry = (BlogEntry) em.createNamedQuery("getEntryWithID").setParameter("id", Long.parseLong(request.getParameter("hiddenID"))).getSingleResult();
			entry.setText(entryText);
			entry.setTitle(entryTitle);
			entry.setLastModifiedAt(date);
			entry.setCreator(creator);
			response.sendRedirect(request.getContextPath() + "/servleti/author/" + creator.getNick());
		} else {
			entry = new BlogEntry();
			entry.setCreatedAt(date);
			entry.setCreator(creator);
			entry.setText(entryText);
			entry.setTitle(entryTitle);
			entry.setLastModifiedAt(date);
			EntityManager em2 = JPAEMProvider.getEntityManager();
			em2.persist(entry);
			response.sendRedirect(request.getContextPath() + "/servleti/author/" + creator.getNick());
		}
	}
}
