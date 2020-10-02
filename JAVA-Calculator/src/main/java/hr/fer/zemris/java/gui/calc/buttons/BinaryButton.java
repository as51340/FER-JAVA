package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import hr.fer.zemris.java.calc.listeners.BinaryOperation;
import hr.fer.zemris.java.gui.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * Concrete button that performs digit inserting into calculator.
 * @author Andi Škrgat
 * @version 1.0
 */
public class BinaryButton extends JButton{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Reference to the operation that will be executed
	 */
	private BinaryOperation binaryListener;
	
	/**
	 * Constructor that sets all required parameters for this button
	 * @param binaryListener Reference to the operation that will be executed
	 * @param display reference to the calculator's display
	 * @param mark string that identifies operation
	 * @param model model of calculator
	 */
	public BinaryButton(BinaryOperation binaryListener, String mark, CalcModelImpl model, JCheckBox box) {
		this.setText(mark);
		this.binaryListener = binaryListener;
		this.setBackground(new Color(150,128,201));
		this.setFont(this.getFont().deriveFont(20f));
		this.addActionListener(e -> {
			if(mark.equals("/") == true) {
				this.binaryListener.setOp(Calculator::divide);
			} else if(mark.equals("*") == true) {
				this.binaryListener.setOp(Calculator::multiply);
			} else if(mark.equals("+") == true) {
				this.binaryListener.setOp(Double::sum);
			} else if(mark.equals("-") == true) {
				this.binaryListener.setOp(Calculator::sub);
			} else if(mark.equals("x^n") == true) {
				if(box.isSelected() == false) {
					this.binaryListener.setOp(Math::pow);	
				} else {
					this.binaryListener.setOp(Calculator::inverzPow);
				}
				
			}
			this.binaryListener.performOperation(model);
			model.getListenerList().get(0).valueChanged(model);
		});
		
	}

}
