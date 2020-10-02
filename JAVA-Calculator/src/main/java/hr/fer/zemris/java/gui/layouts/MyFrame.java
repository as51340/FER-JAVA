package hr.fer.zemris.java.gui.layouts;

import java.awt.Color;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Test for {@linkplain CalcLayout}
 * @author Andi Škrgat
 * @version 1.0
 */
public class MyFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor that sets initial state 
	 */
	public MyFrame() {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			initGUI();
//			pack();
			setSize(200, 200);
	}
	
	/**
	 * Adds GUI component
	 */
	private void initGUI() {
		Container cp = this.getContentPane();
		cp.setLayout(new CalcLayout());
		cp.add(l("1, 1"), new RCPosition(1,1));
		cp.add(l("1, 6"), new RCPosition(1,6));
		cp.add(l("1, 7"), new RCPosition(1,7));
		cp.add(l("2, 1"), new RCPosition(2,1));
		cp.add(l("2, 2"), new RCPosition(2,2));
		cp.add(l("2, 3"), new RCPosition(2,3));
		cp.add(l("2, 4"), new RCPosition(2,4));
		cp.add(l("2, 5"), new RCPosition(2,5));
		cp.add(l("2, 6"), new RCPosition(2,6));
		cp.add(l("2, 7"), new RCPosition(2,7));
		cp.add(l("3, 1"), new RCPosition(3,1));
		cp.add(l("3, 2"), new RCPosition(3,2));
		cp.add(l("3, 3"), new RCPosition(3,3));
		cp.add(l("3, 4"), new RCPosition(3,4));
		cp.add(l("3, 5"), new RCPosition(3,5));
		cp.add(l("3, 6"), new RCPosition(3,6));
		cp.add(l("3, 7"), new RCPosition(3,7));
		cp.add(l("4 ,1"), new RCPosition(4,1));
		cp.add(l("4, 2"), new RCPosition(4,2));
		cp.add(l("4 3"), new RCPosition(4,3));
		cp.add(l("4, 4"), new RCPosition(4,4));
		cp.add(l("4 ,5"), new RCPosition(4,5));
		cp.add(l("4, 6"), new RCPosition(4,6));
		cp.add(l("4, 7"), new RCPosition(4,7));
		cp.add(l("5, 1"), new RCPosition(5,1));
		cp.add(l("5, 2"), new RCPosition(5,2));
		cp.add(l("5, 3"), new RCPosition(5,3));
		cp.add(l("5, 4"), new RCPosition(5,4));
		cp.add(l("5 ,5"), new RCPosition(5,5));
		cp.add(l("5 ,6"), new RCPosition(5,6));
		cp.add(l("5 ,7"), new RCPosition(5,7));
}
	/**
	 * Adjusts {@linkplain JLabel}
	 * @param text text of label
	 * @returns modified {@linkplain JLabel}
	 */
	private JLabel l(String text) {
		JLabel l = new JLabel(text);
		l.setBackground(Color.YELLOW);
		l.setOpaque(true);
		return l;
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new MyFrame().setVisible(true);
		});
	}
}

