package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of layout that will be used for our calculator model
 * @author Andi Škrgat
 * @version 1.0
 */
public class CalcLayout implements LayoutManager2{
	
	/**
	 * Map for checking if some component has already its positon and if some position is already occupied
	 */
	private Map<Component, RCPosition> positions = new HashMap<Component, RCPosition>();
	
	/**
	 * Vertical and horizontal gap
	 */
	private int gap;
	
	/**
	 *@param gap gap between rows and between columns
	 */
	public CalcLayout(int gap) {
		this.gap = gap;
	}
	
	/**
	 * Sets gap to 0
	 */
	public CalcLayout() {
		this(0);
	}
	 
	/**
	 * @return the gap
	 */
	public int getGap() {
		return gap;
	}
	
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException("Operation not allowed");
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		positions.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return calculateSize(parent, Component::getPreferredSize);
	}
	
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return calculateSize(parent, Component::getMinimumSize);
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets parentInsets = parent.getInsets();
		int windowWidth = Math.max(0,parent.getWidth() - parentInsets.left - parentInsets.right);
		int windowHeight = Math.max(0, parent.getHeight() - parentInsets.bottom - parentInsets.top);
		double widthPart = ((double)(windowWidth) - 6D*(double)(gap)) / 7.0;
		double heightPart = ((double)(windowHeight) - 4D*(double)(gap)) / 5.0;
		int n = parent.getComponentCount();
		for(int i = 0; i < n; i++) {
			Component c = parent.getComponent(i);
			RCPosition pos = positions.get(c);
			int row = pos.getRow();
			int column = pos.getColumn();
			if(!(row == 1 && column == 1)) {
				long x1Long = Math.round(parentInsets.left + (column -1)*widthPart + (column -1)*gap);
				int x1 = (int) x1Long;
				long y1Long = Math.round(parentInsets.top + (row -1)*heightPart + (row-1)*gap);
				int y1 = (int) y1Long;
				long x2Long = Math.round(parentInsets.left + column*widthPart + column*gap);
				int x2 = (int) x2Long;
				long y2Long = Math.round(parentInsets.top + row*heightPart + row*gap);
				int y2 = (int) y2Long;
				c.setBounds(x1,y1, x2-x1-gap, y2-y1-gap);
			} else {
				int x1 = parentInsets.left;
				int y1 = parentInsets.top;
				long x2Long = Math.round(parentInsets.left + 5*widthPart + 5*gap);
				int x2 = (int) x2Long;
				long y2Long = Math.round(parentInsets.top + row*heightPart + row*gap);
				int y2 = (int) y2Long;
				c.setBounds(x1,y1, x2-x1-gap, y2-y1-gap);
			}
		}		
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(comp == null) {
			throw new NullPointerException("Component is null");
		}
		if(constraints == null)  {
			throw new NullPointerException("Constraints are null");
		}
		if(!(constraints instanceof RCPosition) && !(constraints instanceof String)) {
			throw new IllegalArgumentException("Constraints are not String and are not RCPosition");
		}
		RCPosition position = null;
		if((constraints instanceof String) == true) {
			 position = Util.parse((String)constraints);
		} else {
			position = (RCPosition) constraints;
		}
		if(positions.containsValue(position) == true) {
			throw new CalcLayoutException("That position is already occupied");
		}
		if(positions.get(comp) != null) {
			throw new CalcLayoutException("Cannot use more positions with same component");
		}
		check(position);
		positions.put(comp, position);
	}
	
	/**
	 * @return the positions
	 */
	public Map<Component, RCPosition> getPositions() {
		return positions;
	}
	/**
	 * Checks if {@linkplain RCPosition} satisfies all conditions that are specified for this layout
	 * @param position {@linkplain RCPosition} whose parameters are checked
	 */
	private void check(RCPosition position) {
		int r = position.getRow();
		int s = position.getColumn();
		if(r < 1 || r > 5 || s < 1 || s > 7) {
			throw new CalcLayoutException("Number of rows or columns does not satisfie expected conditions");
		}
		if(r == 1 && (s > 1 && s < 6)) {
			throw new CalcLayoutException("Cannot put this position in first row");
		}
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return calculateSize(target, Component::getMaximumSize);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
	}
	
	/**
	 * Interface used for calculating preferred, minimum and maximum layout size
	 * @author Andi Škrgat
	 * @version 1.0
	 */
	private interface MyInter {
		Dimension getSize(Component comp);
	}
	
	/**
	 * Method that provides us functionality to calculate minimum, maximum and preferred layout size 
	 * @param parent {@linkplain Container} where components are stored
	 * @param getter {@linkplain MyInter} assistant interface
	 * @returns {@linkplain Dimension} of layout window
	 */
	private Dimension calculateSize(Container parent, MyInter getter) {
		int n = parent.getComponentCount();
		Dimension dim = new Dimension(0, 0);
		double w = 0, h = 0;
		for(int i = 0; i < n; i++) {
			Component c = parent.getComponent(i);
			Dimension cdim = getter.getSize(c);
			if(c == null) {
				continue;
			}
			if(positions.get(c).getRow() == 1 && positions.get(c).getColumn() == 1) {
				w = Math.max(w, (cdim.width - 4*gap) / 5);
			} else {
				w = Math.max(w, cdim.width);
			}
			h = Math.max(h, cdim.height);
		}
		w = 7 *w + 6*gap;
		h = 5 *h + 4 *gap;
		w += parent.getInsets().left + parent.getInsets().right;
		h += parent.getInsets().top + parent.getInsets().bottom;
		dim.width = (int)w;
		dim.height = (int)h;
		return dim;
	}

}
