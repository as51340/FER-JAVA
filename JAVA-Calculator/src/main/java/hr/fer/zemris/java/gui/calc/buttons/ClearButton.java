package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color;

import javax.swing.JButton;
import hr.fer.zemris.java.calc.listeners.ClearOperation;
import hr.fer.zemris.java.gui.calc.CalcModelImpl;

/**
 * Button that will be used to delete current number
 * @author Andi Škrgat
 * @version 1.0
 */
public class ClearButton extends JButton{
	
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Reference to the operation that will be executed
	 */
	private ClearOperation clearListener;
	
	/**
	 * Constructor that sets all required parameters for this button
	 * @param clearListener Reference to the operation that will be executed
	 * @param model reference to the model of calculator
	 */
	public ClearButton(ClearOperation clearListener, CalcModelImpl model) {
		this.clearListener = clearListener;
		this.setText("clr");
		this.setBackground(new Color(150,128,201));
		this.addActionListener((l) -> {
			this.clearListener.performOperation(model);
			model.getListenerList().get(0).valueChanged(model);
		});
	}

}
