package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color;

import javax.swing.JButton;
import hr.fer.zemris.java.calc.listeners.SignOperation;
import hr.fer.zemris.java.gui.calc.CalcModelImpl;

/**
 * Sign swap button
 * @author Andi Škrgat
 * @version 1.0
 */
public class SignSwapButton extends JButton{
	
	/**
	 * Reference to the operation that will be executed
	 */
	private SignOperation signListener;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Initializes reset operation and model of calculator
	 * @param signListener Reference to the operation that will be executed
	 * @param model calculator's model
	 */
	public SignSwapButton(SignOperation signListener, CalcModelImpl model) {
		this.signListener = signListener;
		this.setText("+/-");
		this.setBackground(new Color(150,128,201));
		this.addActionListener((l)-> {
			this.signListener.performOperation(model);
			model.getListenerList().get(0).valueChanged(model);
		});
	}

}
