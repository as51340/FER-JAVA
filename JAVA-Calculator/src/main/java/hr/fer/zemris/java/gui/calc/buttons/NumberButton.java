package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color;

import javax.swing.JButton;
import hr.fer.zemris.java.calc.listeners.NumberOperation;
import hr.fer.zemris.java.gui.calc.CalcModelImpl;

/**
 * Button for digit inserting
 * @author Andi Škrgat
 * @version 1.0
 */
public class NumberButton extends JButton{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *  Reference to the operation that will be executed
	 */
	private NumberOperation numberListener;
	
	/**
	 * Constructor that sets all required parameters for this button
	 * @param numberListener Reference to the operation that will be executed
	 * @param digit digit that will be inserted
	 * @param model model of calculator
	 */
	public NumberButton(NumberOperation numberListener, int digit, CalcModelImpl model) {
		this.numberListener = numberListener;
		this.setText(Integer.toString(digit));
		this.setFont(this.getFont().deriveFont(30f));
		this.setBackground(new Color(150,128,201));
		this.addActionListener((e) -> {
			this.numberListener.setDigit(digit);
			this.numberListener.performOperation(model);
			model.getListenerList().get(0).valueChanged(model);	
		});
	}

}
