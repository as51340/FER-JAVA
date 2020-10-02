package hr.fer.zemris.hw14.servlets;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import java.util.List;
import hr.fer.zemris.java.hw14.model.Data;

/**
 * Creates chart from the voting results and forwards chart image back to {@link glasanjeRez.jsp}
 * @author Andi Škrgat
 * @version 1.0
 */
@WebServlet("/servleti/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("image/png");
		OutputStream os = response.getOutputStream();
		DefaultPieDataset result = new DefaultPieDataset();
		List<Data> allData = (List<Data>)request.getSession().getAttribute("allData");
		for(Data data: allData) {
			if(data.getVotes() != 0) {
				result.setValue(data.getName(), data.getVotes());	
			}
		}
		JFreeChart mychart = ChartFactory.createPieChart3D("Voting results",result,true,true,false);
		PiePlot3D plot = (PiePlot3D) mychart.getPlot();
	    plot.setStartAngle(290);
	    plot.setDirection(Rotation.CLOCKWISE);
	    plot.setForegroundAlpha(0.5f);
		ChartUtils.writeChartAsPNG(os, mychart, 640, 480);
		os.close();
	}
}
