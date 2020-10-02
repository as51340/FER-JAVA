package hr.fer.zemris.java.problem7;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Creates chart from the voting results and forwards chart image back to {@link glasanjeRez.jsp}
 * @author Andi Škrgat
 * @version 1.0
 */
public class GlasanjeGrafikaServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("image/png");
		OutputStream os = response.getOutputStream();
		DefaultPieDataset result = new DefaultPieDataset();
		Map<String, String> idNameLink = (Map<String, String>) request.getSession().getAttribute("idNameLink");
		Map<String, Integer> idVotes = (Map<String, Integer>) request.getSession().getAttribute("idVotes");
		for(String id: idVotes.keySet()) {
			String[] arr = idNameLink.get(id).split("\t");
			result.setValue(arr[0], idVotes.get(id));
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
