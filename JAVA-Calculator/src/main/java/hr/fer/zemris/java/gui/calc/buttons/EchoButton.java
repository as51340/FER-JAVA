package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color;

import javax.swing.JButton;
import hr.fer.zemris.java.calc.listeners.EchoOperation;
import hr.fer.zemris.java.gui.calc.CalcModelImpl;

/**
 * Button when "=" is clicked
 * @author Andi Škrgat
 * @version 1.0
 */
public class EchoButton extends JButton{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Reference to the operation that will be executed
	 */
	private EchoOperation echoListener;
	
	/**
	 * Constructor that sets all required parameters for this button
	 * @param echoListener Reference to the operation that will be executed
	 * @param model model of calculator
	 */
	public EchoButton(EchoOperation echoListener, CalcModelImpl model) {
		this.echoListener = echoListener;
		this.setText("=");
		this.setBackground(new Color(150,128,201));
		this.addActionListener((e) -> {
			this.echoListener.performOperation(model);
			model.getListenerList().get(0).valueChanged(model);
		});
	}

}
