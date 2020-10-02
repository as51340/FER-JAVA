package hr.fer.zemris.java.gui.prim;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Frame that has two {@linkplain JScrollPane} and one {@linkplain JButton}. Every time when user will click on button, 
 * new prim number will be added into both lists.
 * @author Andi Škrgat
 * @version 1.0
 */
public class PrimDemo extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Reference to the {@linkplain PrimListModel}
	 */
	private PrimListModel prim;
	
	/**
	 * Constructor that initializes parameters for frame opening.
	 */
	public PrimDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		setTitle("Prim number generator");
		prim = new PrimListModel();
		initGUI();
	}
	
	/**
	 * Method used for initializing components of this frame
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
		cp.setBackground(new Color(255, 127, 94));
		JList<Long> list1 = new JList<Long>(prim);
		JList<Long> list2 = new JList<Long>(prim);
		list1.setBackground(new Color(123, 164, 242));
		list2.setBackground(new Color(123, 238, 11));
		JScrollPane pane1 = new JScrollPane(list1);
		JScrollPane pane2 = new JScrollPane(list2);
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.LINE_AXIS));
		panel1.add(pane1);
		repaint();
		panel1.add(pane2);
		cp.add(panel1);
		
		JButton next = new JButton("NEXT");
		cp.add(next);
		next.setAlignmentX(Component.CENTER_ALIGNMENT);
		next.addActionListener((l) -> {
			prim.add();
		});
	}
	
	/**
	 * Main method from where program starts
	 * @param args no arguments should be provided
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PrimDemo().setVisible(true);
		});
	}

}
