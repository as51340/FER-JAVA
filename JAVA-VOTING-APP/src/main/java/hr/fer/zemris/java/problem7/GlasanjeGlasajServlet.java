package hr.fer.zemris.java.problem7;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Updates number of votes for desired band in file {@link glasanje-rezultati.txt}
 * @author Andi Škrgat
 * @version 1.0
 */
public class GlasanjeGlasajServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fileName = request.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");	
		File file = new File(fileName);
		if(file.exists() == false) {
			if(file.createNewFile() == false) {
				throw new RuntimeException("File creation failed");
			}
		}
		BufferedReader br = Files.newBufferedReader(Paths.get(fileName));
		RandomAccessFile raf = new RandomAccessFile(file,"rw");
		String id = request.getParameter("id");
		if(id == null) {
			raf.close();
			throw new RuntimeException("No id parameter sent");
		}
		String line = null;
		boolean founded = false;
		while((line = br.readLine()) != null) {
			String[] arr = line.split("\t");
			if(arr[0].equals(id) == true) {
				founded = true;
				int curr = Integer.parseInt(arr[1]) +1;
				line = id + "\t" + Integer.toString(curr) + "\r\n";
				raf.write(line.getBytes());
				break;
			}
			raf.seek(raf.getFilePointer() + line.length() +2);
		}
		if(founded == false) {
			line = id + "\t" + 1 + "\r\n";
			raf.write(line.getBytes(Charset.forName("UTF-8")));
		}
		raf.close();
		br.close();
		response.sendRedirect(request.getContextPath() + "/glasanje-rezultati");
	}
	
}
