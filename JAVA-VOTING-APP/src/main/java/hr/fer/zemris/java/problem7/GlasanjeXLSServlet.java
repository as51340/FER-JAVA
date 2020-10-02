package hr.fer.zemris.java.problem7;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Creates xls file for downloading. In file is stored information about how many votes each band has.
 * @author Andi Škrgat
 * @version 1.0
 */
public class GlasanjeXLSServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=\"rezultati.xls\"");	
		try {
			HSSFWorkbook hwb=new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet();
			Map<String, Integer> idVotes = (Map<String, Integer>) request.getSession().getAttribute("idVotes");
			Map<String, String> idNameLink = (Map<String, String>) request.getSession().getAttribute("idNameLink");
			HSSFRow row0 = sheet.createRow(0);
			HSSFCell cell1 = row0.createCell(0);
			cell1.setCellValue("Bend");
			HSSFCell cell2 = row0.createCell(1);
			cell2.setCellValue("Broj glasova");
			int i = 1;
			for(String id: idNameLink.keySet()) {
				HSSFRow row = sheet.createRow(i++);
				HSSFCell bend = row.createCell(0);
				HSSFCell votes = row.createCell(1);
				bend.setCellValue(idNameLink.get(id).split("\t")[0]);
				votes.setCellValue(idVotes.get(id) == null ? 0: idVotes.get(id));
			}
			hwb.write(response.getOutputStream());
			hwb.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
