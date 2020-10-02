package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
/**
 * Class that parses data from given file and from that data calls {@linkplain BarChartComponent} for opening new {@linkplain JfFrame} 
 * @author Andi Škrgat
 * @version 1.0
 */
public class BarChartDemo extends JFrame{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor that sets initial state for this frame 
	 */
	public BarChartDemo(BarChart chart, String text) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(700, 700);
		setTitle("Java graphics");
		BarChartComponent comp = new BarChartComponent(chart);
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(comp,BorderLayout.CENTER);
		JLabel label = new JLabel(text);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		cp.add(label, BorderLayout.PAGE_START);
//		pack();
	}
	
	/**
	 * Main method from where program starts. It parses data from given file and creates {@linkplain BarChartComponent}
	 * @param args one argument should be provided from user and that's path to file
	 * @throws IOException if opening or reading file failed
	 */
	public static void main(String[] args) throws IOException {
		if(args.length != 1) {
			throw new IllegalArgumentException("Illegal number of arguments provided by user");
		}
		String fileName = args[0];
//		String fileName = "C:\\Users\\Korisnik\\Desktop\\eclipse-workspaces\\dz\\hw10-0036513403\\testInput.txt";
		BufferedReader br = null;
		Path path = Paths.get(fileName);
		try {
		 br = Files.newBufferedReader(path);
		} catch (IOException e) {
			System.out.println("File reading failed, program ending");
			return;
		}
		String xDes, yDes;
		xDes = br.readLine();
		yDes = br.readLine();
		String valuesInput = null;
		valuesInput = br.readLine();
		String[] values = valuesInput.split(" ");
		List<XYValue> list = new ArrayList<XYValue>();
		for(String value: values) {
//			System.out.println(value);
			value = value.trim();
			String[] tmpArr = value.split(",");
			try {
				int x = Integer.parseInt(tmpArr[0]);
				int y = Integer.parseInt(tmpArr[1]);
				list.add(new XYValue(x, y));
			} catch(NumberFormatException ex) {
				System.out.println("Wrong data input, ending...");
				return;
			}
 		}
		int yMin, yMax, yOff;
		try {
			yMin = Integer.parseInt(br.readLine());
			yMax = Integer.parseInt(br.readLine());
			yOff = Integer.parseInt(br.readLine());
		} catch(NumberFormatException ex) {
			System.out.println("Wrong data input when setting parameters for yAxis");
			return;
		}
		BarChart chart = new BarChart(list, xDes, yDes, yMin, yMax, yOff);
//		testOutput(chart);
		SwingUtilities.invokeLater(() -> {
			BarChartDemo frame = new BarChartDemo(chart, fileName);
			frame.setVisible(true);
			
		});
		br.close();
	}
	
	/**
	 * Private method used for testing if parsing from file went as expected
	 * @param chart reference to the {@linkplain BarChart} where data are stored
	 */
	private static void testOutput(BarChart chart) {
		System.out.println(chart.getxDes());
		System.out.println(chart.getyDes());
		System.out.println(chart.getyMin());
		System.out.println(chart.getyMax());
		System.out.println(chart.getyOff());
		for(XYValue val: chart.getValues()) {
			System.out.println(val.getX() + " " + val.getY());
		}
	}
	

}
