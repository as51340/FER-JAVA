package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color;

import javax.swing.JButton;
import hr.fer.zemris.java.calc.listeners.PointOperation;
import hr.fer.zemris.java.gui.calc.CalcModelImpl;

/**
 * Performs insertion of decimal point
 * @author Andi Škrgat
 * @version 1.0
 */
public class DecimalPointButton extends JButton{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Reference to the operation that will be executed
	 */
	private PointOperation pointListener;
	
	/**
	 * Constructor that sets all required parameters for this button
	 * @param pointListener Reference to the operation that will be executed
	 * @param model model of calculator
	 */
	public DecimalPointButton(PointOperation pointListener, CalcModelImpl model) {
		this.pointListener = pointListener;
		this.setText(".");
		this.setBackground(new Color(150,128,201));
		this.addActionListener((l)-> {
			this.pointListener.performOperation(model);
			model.getListenerList().get(0).valueChanged(model);
		});
	}

}
