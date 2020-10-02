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
public class DemoFrame1 extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor that sets initial state 
	 */
	public DemoFrame1() {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			setSize(500, 500);
			initGUI();
//			setResizable(false);
//			pack();
	}
	
	/**
	 * Adds GUI component
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(2));
		cp.add(l("tekst 1"), new RCPosition(1,1));
		cp.add(l("tekst 2"), new RCPosition(2,3));
		cp.add(l("tekst stvarno najdulji"), new RCPosition(2,7));
		cp.add(l("tekst kraći"), new RCPosition(4,2));
		cp.add(l("tekst srednji"), new RCPosition(4,5));
		cp.add(l("tekst"), new RCPosition(4,7));
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
	
	/**
	 * Method from where program starts
	 * @param args no arguments should be provided
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new DemoFrame1().setVisible(true);
		});
	}
}

