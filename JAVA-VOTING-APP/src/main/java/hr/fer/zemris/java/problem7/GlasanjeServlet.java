package hr.fer.zemris.java.problem7;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Reads bands' names from {@link glasanje-definicija.txt} and sends them to {@link glasanjeIndex.jsp}
 * @author Andi Škrgat
 * @version 1.0
 */
public class GlasanjeServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String fileName = request.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		BufferedReader br = Files.newBufferedReader(Paths.get(fileName), Charset.forName("UTF-8"));	
		String line = null;
		Map<String, String> idNameLink = new HashMap<String, String>();
		while((line = br.readLine()) != null) {
			String[] arr = line.split("\t");
			try {
				idNameLink.put(arr[0], arr[1] + "\t" + arr[2]);	
			} catch(ArrayIndexOutOfBoundsException ex) {
				
			}
		}
		request.getSession().setAttribute("idNameLink", idNameLink);
		request.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(request, response);
	}

}
