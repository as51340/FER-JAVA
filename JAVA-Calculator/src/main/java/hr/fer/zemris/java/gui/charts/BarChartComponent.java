package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComponent;

/**
 * Component that is used for drawing charts from data provided from {@linkplain BarChart}
 * @author Andi Škrgat
 * @version 1.0
 */
public class BarChartComponent extends JComponent{

	private static final long serialVersionUID = 1L;
	
	/**
	 * {@linkplain BarChart} data for creating one chart
	 */
	private BarChart data;
	
	/**
	 * Fixed constant that is used for making filled arrows on the top of each axis
	 */
	private static final int arrowConst = 5;

	/**
	 * Some fixed constant
	 */
	private static final int fix = 5;
	
	/**
	 * Initializes {@linkplain BarChart} variable
	 * @param data reference to the already existing data
	 */
	public BarChartComponent(BarChart data) {
		this.data = data;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Insets ins = getInsets();
		int xAvailable = getWidth() - ins.left - ins.right;
		int yAvailable = getHeight() -ins.top - ins.bottom;
		FontMetrics fm = g2.getFontMetrics();
		//solve y axis
		String yDes = data.getyDes();
		int fontSize = g2.getFontMetrics().getHeight();
		AffineTransform currentAff = g2.getTransform();
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2.setTransform(at);
		int y1 = ins.left;
		y1 += fontSize;
 		int tekstDownStart = yAvailable - fontSize;
 		int yIsh = tekstDownStart - fix - fontSize - fix;
 		int x1 = -(int)Math.round((double)(yAvailable) / (double)2  + fm.stringWidth(yDes));
 		g2.drawString(yDes, x1, y1);
 		g2.setTransform(currentAff);
 		int currX = y1;
 		currX += fix;
 		int yMax = data.getyMax();
 		int maxWidthNumber = fm.stringWidth(Integer.toString(yMax));
 		int xEnding = currX + maxWidthNumber;
 		int yMin = data.getyMin();
 		int i = yMin;
 		int off = data.getyOff(); 		
 		double num = Math.round(((double)(yMax -yMin) / (double)off));
 		double heightPart = (double) (yIsh -arrowConst - fontSize) / num;
 		double currY = yIsh;
 		while(i <= yMax) {
 			int xBegin = xEnding - fm.stringWidth(Integer.toString(i));
 			double tmp = ((double)fm.getFont().getSize() / (double)2);
 			int yS = (int)(currY + tmp);
 			g2.drawString(Integer.toString(i), xBegin, yS);
 			i += off;
 			currY -= heightPart;
 		}
 		xEnding += fix;
 		drawYAxis(g2, new Point(xEnding, yIsh));
 		drawXAxis(g2, new Point(xEnding, yIsh), xAvailable -xEnding);
 		g2.drawString(data.getxDes(), (xAvailable - fm.stringWidth(data.getxDes())) / 2, tekstDownStart);
 		int max = -1;
 		Set<Integer> set = new HashSet<Integer>();
 		for(XYValue value: data.getValues()) {
 			max = Math.max(max, value.getX());
 			set.add(value.getX());
 		}
 		int nextX;
 		int x0 = xEnding;
 		int y0 = yIsh - fix + fontSize;
 		i = 1;
 		int widthPart = (int)Math.round((double)(xAvailable - xEnding) / (double)max);
 		while(i <= max) {
 			if(set.contains(i) == false) {
 				i++;
 				continue;
 			}
 			nextX = x0 + widthPart;
 			g2.drawLine(nextX,yIsh + 2 , nextX, yIsh- 2);
 			int halfX = x0 + (widthPart - fm.stringWidth(Integer.toString(i))) / 2;
 			g2.drawString(Integer.toString(i), halfX, y0);
 			i++;
 			x0= nextX;
 		}
 		double min1 = yIsh;
 		int min = (int)min1;
 		g2.setColor(Color.RED);
 		for(XYValue value: data.getValues()) {
 			int xF = value.getX();
 			int yF = value.getY() -yMin;
 			int xF2 = xEnding + (xF -1)*widthPart;
 			if(xF == max) {
 				xF2 = xAvailable - arrowConst -widthPart;
 			}
 			double d = -(double)yF / (double)off;
 			d = d*heightPart + yIsh;
 			int yF2 = (int)d;
 			double tmp = -yF2 + min;
			g2.fillRect(xF2, yF2, widthPart, (int)(tmp));
 		}
 		g2.setColor(Color.BLACK);
 		paintArrowTop(g2, new Point(xAvailable, yIsh),new Point(xAvailable - arrowConst, yIsh - arrowConst),
 				new Point(xAvailable - arrowConst, yIsh + arrowConst));
 		paintArrowTop(g2, new Point(xEnding, 0),new Point(xEnding - arrowConst, arrowConst),
 				new Point(xEnding + arrowConst, arrowConst));
	}
	
	/**
	 * Private method for drawing yAxis
	 * @param g {@linkplain Graphics2D}
	 * @param start start point for drawing axis
	 */
	private void drawYAxis(Graphics2D g, Point start) {
		g.drawLine(start.x, start.y, start.x, 0);
	}
	
	/**
	 * Private method for drawing xAxis
	 * @param g {@linkplain Graphics2D}
	 * @param start start point for drawing axis
	 * @param length length of line
	 */
	private void drawXAxis(Graphics2D g, Point start, int length) {
		g.drawLine(start.x, start.y, start.x + length, start.y);
	}
	
	/**
	 * Method used for drawing arrow top on some line
	 * @param g {@linkplain Graphics2D}
	 * @param top peek point
	 * @param a left/top point 
	 * @param b down/right point
	 */
	private void paintArrowTop(Graphics2D g, Point top, Point a, Point b) {
		int xpoints[] = {a.x, b.x, top.x};
		int ypoints[] = {a.y, b.y, top.y};
		g.fillPolygon(new Polygon(xpoints, ypoints, 3));
	}
}
