package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color;

import javax.swing.JButton;
import hr.fer.zemris.java.calc.listeners.ResetOperation;
import hr.fer.zemris.java.gui.calc.CalcModelImpl;

/**
 * Button that resets calculator
 * @author Andi Škrgat
 * @version 1.0
 */
public class ResetButton extends JButton{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Reference to the reset operation that will be executed
	 */
	private ResetOperation rst;
	
	/**
	 * Initializes reset operation and model of calculator
	 * @param rst Reference to the operation that will be executed
	 * @param model calculator's model
	 */
	public ResetButton(ResetOperation rst, CalcModelImpl model) {
		this.rst = rst;
		this.setText("res");
		this.setBackground(new Color(150,128,201));
		this.addActionListener((e) -> {
			this.rst.performOperation(model);
			model.getListenerList().get(0).valueChanged(model);
		});
	}

}
