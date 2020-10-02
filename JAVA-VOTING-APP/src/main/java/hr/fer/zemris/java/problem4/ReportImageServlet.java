package hr.fer.zemris.java.problem4;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.ChartUtils;

/**
 * Servlet used for rendering {@linkplain JFreeChart} chart and sending it to back {@link report.jsp} file.
 * @author Andi Škrgat
 * @version 1.0
 */
public class ReportImageServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		res.setContentType("image/png");
		OutputStream os = res.getOutputStream();
		DefaultPieDataset result = new DefaultPieDataset();
		result.setValue("MAC", 20);
		result.setValue("Linux", 30);
		result.setValue("Windows", 50);
		JFreeChart mychart = ChartFactory.createPieChart3D("OS usage",result,true,true,false);
		PiePlot3D plot = (PiePlot3D) mychart.getPlot();
	    plot.setStartAngle(290);
	    plot.setDirection(Rotation.CLOCKWISE);
	    plot.setForegroundAlpha(0.5f);
		ChartUtils.writeChartAsPNG(os, mychart, 640, 480);
		os.close();
	}
}
