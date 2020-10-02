package hr.fer.zemris.java.gui.charts;

/**
 * Represents data for our {@linkplain BarChart}
 * @author Andi Škrgat
 * @version 1.0
 */
public class XYValue {
	
	/**
	 * Read only properties
	 */
	private int x, y;
	
	/**
	 * Initializes properties for this object 
	 * @param x data for x axis
	 * @param y data for y axis
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y =y;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	

}
