package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import hr.fer.zemris.java.calc.listeners.UnaryOperation;
import hr.fer.zemris.java.gui.calc.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.Calculator;

/**
 * Button that perform all unary operations
 * @author Andi Škrgat
 * @version 1.0
 */
public class UnaryButton extends JButton {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Reference to the unary operation
	 */
	private UnaryOperation unaryListener;

	/**
	 * 
	 * @param unaryListener Reference to the unary operation, for all unary operations there is only one object that encapsulates operation
	 * @param mark mark so we can know which operation to use
	 * @param model model of calculator
	 * @param inv check box so we can know which operation to execute(inverz or regular)
	 */
	public UnaryButton(UnaryOperation unaryListener,  String mark, CalcModelImpl model, JCheckBox inv) {
		this.setText(mark);
		this.unaryListener = unaryListener;
		this.setBackground(new Color(150,128,201));
		this.setFont(this.getFont().deriveFont(12f));
		this.addActionListener(e -> {
			if(mark.equals("sin") == true) {
				this.unaryListener.setFunctions(Math::sin, Math::asin);
			} else if(mark.equals("cos") == true) {
				this.unaryListener.setFunctions(Math::cos, Math::acos);
			} else if(mark.equals("log") == true) {
				this.unaryListener.setFunctions(Math::log10, Calculator::pow10);
			} else if(mark.equals("ln") == true) {
				this.unaryListener.setFunctions(Math::log, Math::exp);
			} else if(mark.equals("tan") == true) {
				this.unaryListener.setFunctions(Math::tan, Math::atan);
			} else if(mark.equals("ctg") == true) {
				this.unaryListener.setFunctions(Calculator::ctg, Calculator::actg);
			}
			if(inv.isSelected() == true) {
				this.unaryListener.setInverzSet(true);
			} else {
				this.unaryListener.setInverzSet(false);
			}
			this.unaryListener.performOperation(model);
			model.getListenerList().get(0).valueChanged(model);
	});

	}
}
