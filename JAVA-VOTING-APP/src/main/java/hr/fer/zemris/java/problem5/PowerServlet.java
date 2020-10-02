package hr.fer.zemris.java.problem5;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Creates xls file from accepted parameters a,b and n in url path. In created xls file on  i-th page is calculated i-th power of number that is within interval.
 * @author Andi Škrgat
 * @version 1.0
 */
public class PowerServlet extends HttpServlet{
	
	private static final long serialVersionUID = 4229270464154855946L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String a = req.getParameter("a");
		String b = req.getParameter("b");
		String n = req.getParameter("n");
		if(a == null || b == null || n == null) {
			req.getRequestDispatcher("/error.jsp").forward(req, res);
			return;
		}
		int aNum = Integer.parseInt(a);
		int bNum = Integer.parseInt(b);
		int nNum = Integer.parseInt(n);
		if(aNum < -100 || bNum < -100 || nNum < 1 || aNum > 100 || bNum > 100 || nNum > 5) {
			req.getRequestDispatcher("/error.jsp").forward(req, res);
			return;
		}
		if(bNum < aNum) {
			int tmp = bNum;
			bNum = aNum;
			aNum = tmp;
		}
		res.setContentType("application/vnd.ms-excel");
		res.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");	
		try {
			HSSFWorkbook hwb=new HSSFWorkbook();
			for(int i = 1; i <= nNum; i++) {
				HSSFSheet sheet =  hwb.createSheet("page "+i);
				int k = 0;
				for(int j = aNum; j <= bNum; j++) {
					HSSFRow rowhead=   sheet.createRow((short) k++);
					rowhead.createCell((short) 0).setCellValue(j);
					rowhead.createCell((short) 1).setCellValue(Math.pow(j, i));
				}
			}
			hwb.write(res.getOutputStream());
			hwb.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
