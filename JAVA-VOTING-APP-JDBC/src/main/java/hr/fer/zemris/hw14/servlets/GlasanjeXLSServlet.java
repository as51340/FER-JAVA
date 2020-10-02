package hr.fer.zemris.hw14.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.hw14.model.Data;

/**
 * Creates xls file for downloading. In file is stored information about how many votes each item(here are informations about poll for voting for the best 
 * football player and poll for best rock bands)
 * @author Andi Škrgat
 * @version 1.0
 */
@WebServlet("/servleti/glasanje-xls")
public class GlasanjeXLSServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=\"rezultati.xls\"");
		List<Data> allData = (List<Data>)request.getSession().getAttribute("allData");
		try {
			HSSFWorkbook hwb=new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet();
			HSSFRow row0 = sheet.createRow(0);
			HSSFCell cell1 = row0.createCell(0);
			cell1.setCellValue("Title");
			HSSFCell cell2 = row0.createCell(1);
			cell2.setCellValue("Number of votes");
			int i = 1;
			for(Data data: allData) {
				HSSFRow row = sheet.createRow(i++);
				HSSFCell data1 = row.createCell(0);
				HSSFCell votes = row.createCell(1);
				data1.setCellValue(data.getName());
				votes.setCellValue(data.getVotes());
			}
			hwb.write(response.getOutputStream());
			hwb.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
