package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Class that contains all relevant data for creating {@linkplain BarChartComponent}
 * @author Andi Škrgat
 * @version 1.0
 */
public class BarChart {
	
	/**
	 * List of {@linkplain XYValue}, data for Chart
	 */
	private List<XYValue> values;
	
	/**
	 * Description of xAxis
	 */
	private String xDes;
	
	/**
	 * Description of yAxis
	 */
	private String yDes;
	
	/**
	 * Minimum value on yAxis
	 */
	private int yMin;
	
	/**
	 * Maximum value on yAxis
	 */
	private int yMax;
	
	/**
	 * Gap between 2 values on yAxis
	 */
	private int yOff;
	
	/**
	 * Initialization of all parameters
	 * @param values list of {@linkplain XYValue}
	 * @param xDes description of xAxis
	 * @param yDes description of yAxis
	 * @param yMin minimum value on yAxis
	 * @param yMax maximum value on yAxis
	 * @param yOff gap between 2 values on yAxis
	 */
	public BarChart(List<XYValue> values, String xDes, String yDes, int yMin, int yMax, int yOff) {
		this.values = values;
		this.xDes = xDes;
		this.yDes = yDes;
		this.yMin = yMin;
		this.yMax = yMax;
		this.yOff = yOff;
		check(values, yMin);
	}
	
	/**
	 * Private method used for checking if y-coordinates of all values are larger than than yMin
	 * @param values
	 * @param yMin
	 */
	private void check(List<XYValue> values, int yMin) {
		for(XYValue value: values) {
			if(value.getY() < yMin) {
				throw new IllegalArgumentException("Y value is smaller than minimum");
			}
		}
	}

	/**
	 * @return the values
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * @return the xDes
	 */
	public String getxDes() {
		return xDes;
	}

	/**
	 * @return the yDes
	 */
	public String getyDes() {
		return yDes;
	}

	/**
	 * @return the yMin
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * @return the yMax
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * @return the yOff
	 */
	public int getyOff() {
		return yOff;
	}

}
