package hr.fer.zemris.java.gui.layouts;

/**
 * Represents position of component in out parent container. It will be used as constraint.
 * @author Andi Škrgat
 * @version 1.0
 */
public class RCPosition {
	
	/**
	 * Row position
	 */
	private int row;
	
	/**
	 * Column position
	 */
	private int column;
	
	/**
	 * Conctructor that performs intialization of read-only properties: row and column
	 * @param row row position
	 * @param column column position
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result += prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof RCPosition))
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
	
	
	

}
