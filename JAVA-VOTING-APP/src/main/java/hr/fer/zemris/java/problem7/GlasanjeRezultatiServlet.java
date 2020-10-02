package hr.fer.zemris.java.problem7;

import java.io.BufferedReader;
import java.io.File;
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
 * Gets all votes from file <{@link glasanje-rezultati.txt} and forwards request to {@link glasanjeRez.jsp} for rendering results.
 * @author Andi Škrgat
 * @version 1.0
 */
public class GlasanjeRezultatiServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String fileName = request.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		File file = new File(fileName);
		if(file.exists() == false) {
			if(file.createNewFile() == false) {
				throw new RuntimeException("File creation failed");
			}
		}
		BufferedReader br = Files.newBufferedReader(Paths.get(fileName), Charset.forName("UTF-8"));
		Map<String, Integer> idVotes = new HashMap<String, Integer>();
		String line = null;
		while((line = br.readLine()) != null) {
			String[] arr = line.split("\t");
			idVotes.put(arr[0],  Integer.parseInt(arr[1]));
		}
		br.close();
		request.getSession().setAttribute("idVotes", idVotes);
		request.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(request, response);
	}

}
