package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.util.EmptyStackException;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.calc.listeners.BinaryOperation;
import hr.fer.zemris.java.calc.listeners.ClearOperation;
import hr.fer.zemris.java.calc.listeners.EchoOperation;
import hr.fer.zemris.java.calc.listeners.NumberOperation;
import hr.fer.zemris.java.calc.listeners.PointOperation;
import hr.fer.zemris.java.calc.listeners.ResetOperation;
import hr.fer.zemris.java.calc.listeners.SignOperation;
import hr.fer.zemris.java.calc.listeners.UnaryOperation;
import hr.fer.zemris.java.gui.calc.buttons.BinaryButton;
import hr.fer.zemris.java.gui.calc.buttons.ClearButton;
import hr.fer.zemris.java.gui.calc.buttons.DecimalPointButton;
import hr.fer.zemris.java.gui.calc.buttons.EchoButton;
import hr.fer.zemris.java.gui.calc.buttons.NumberButton;
import hr.fer.zemris.java.gui.calc.buttons.ResetButton;
import hr.fer.zemris.java.gui.calc.buttons.SignSwapButton;
import hr.fer.zemris.java.gui.calc.buttons.UnaryButton;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Implementation of calculator with functions for sin, cos, asin, acos, tan, atan, inverz, log, ln, exponentiation, ctg, addition,
 * subtraction, multiplication, dividing, and stack using.
 * @author Andi Škrgat
 * @version 1.0
 */
public class Calculator extends JFrame{
	
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Reference to the {@linkplain CalcModelImpl}
	 */
	private CalcModelImpl model;
	
	/**
	 * Stack that is internally used in implementation of calculator
	 */
	private Stack<Double> stog;
	
	/**
	 * Reference to the operation of digit inserting
	 */
	private NumberOperation numberListener;
	
	/**
	 * Reference to the object that will be used as binary operation
	 */
	private BinaryOperation binaryListener;
	
	/**
	 * Reference to the object that will be used as unary operation
	 */
	private UnaryOperation unaryListener;
	
	/**
	 * Reference to the clear operation 
	 */
	private ClearOperation clearListener;
	
	/**
	 * Reference to the reset operation 
	 */
	private ResetOperation resetListener;
	
	/**
	 * Reference to the operation for decimal point inserting
	 */
	private PointOperation pointListener;
	
	/**
	 * Operation for sign swapping
	 */
	private SignOperation signListener;
	
	/**
	 * Operation that will be executed when "=" is clicked
	 */
	private EchoOperation echoListener;
	
	/**
	 * Display of calculator
	 */
	private JLabel display;
	
	/**
	 * Default constructor from where GUI initialization is called 
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Java Calculator v1.0");
		stog = new Stack<>();
		model = new CalcModelImpl();
		display = new JLabel("");
		display.setOpaque(true);
		display.setBackground(Color.YELLOW);
		numberListener = new NumberOperation();
		binaryListener = new BinaryOperation();
		unaryListener = new UnaryOperation();
		clearListener = new ClearOperation();
		resetListener = new ResetOperation();
		pointListener = new PointOperation();
		signListener = new SignOperation();
		echoListener = new EchoOperation();
		model.addCalcValueListener(e -> {
			display.setText(model.toString());
		});
		initGUI();
//		pack();
		setSize(600, 600);
	}

	/**
	 * Method from where program starts
	 * @param args no arguments should be provided
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}
	
	/**
	 * Initalizes all buttons for calculator
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(2));
		//Display start
		display.setHorizontalAlignment(SwingConstants.RIGHT);
		display.setText(model.toString());
		display.setFont(display.getFont().deriveFont(30f));
		cp.add(display, new RCPosition(1,1));
		//Display end
		//Numbers
		NumberButton seven = new NumberButton(numberListener, 7,model);
		cp.add(seven, new RCPosition(2, 3));
		NumberButton eight = new NumberButton(numberListener, 8,  model);
		cp.add(eight, new RCPosition(2, 4));
		NumberButton nine = new NumberButton(numberListener, 9, model);
		cp.add(nine, new RCPosition(2, 5));
		NumberButton four = new NumberButton(numberListener, 4,  model);
		cp.add(four, new RCPosition(3, 3));
		NumberButton five = new NumberButton(numberListener, 5, model);
		cp.add(five, new RCPosition(3, 4));
		NumberButton six = new NumberButton(numberListener, 6, model);
		cp.add(six, new RCPosition(3, 5));
		NumberButton one = new NumberButton(numberListener, 1,  model);
		cp.add(one, new RCPosition(4, 3));
		NumberButton two = new NumberButton(numberListener, 2, model);
		cp.add(two, new RCPosition(4, 4));
		NumberButton three = new NumberButton(numberListener, 3, model);
		cp.add(three, new RCPosition(4, 5));
		NumberButton zero = new NumberButton(numberListener, 0,  model);
		cp.add(zero, new RCPosition(5,3));
		//Numbers end
		//inv start
		JCheckBox inv = new JCheckBox("Inv");
		//inv end
		//Binary operation begin
		BinaryButton divide = new BinaryButton(binaryListener, "/", model,inv);
		BinaryButton mult = new BinaryButton(binaryListener,  "*", model,inv);
		BinaryButton plus = new BinaryButton(binaryListener,  "+", model,inv);
		BinaryButton minus = new BinaryButton(binaryListener, "-", model,inv);
		BinaryButton pow = new BinaryButton(binaryListener, "x^n", model,inv);
		cp.add(divide, new RCPosition(2, 6));
		cp.add(mult, new RCPosition(3, 6));
		cp.add(minus, new RCPosition(4, 6));
		cp.add(plus, new RCPosition(5, 6));
		cp.add(pow, new RCPosition(5,1));
		//Binary operation end
		//Stack operations start
		JButton push = new JButton("push");
		push.setBackground(new Color(150,128,201));
		push.addActionListener((e) -> {
			stog.push(model.getValue());
		});
		cp.add(push, new RCPosition(3,7));
		JButton pop = new JButton("pop");
		pop.setBackground(new Color(150,128,201));
		pop.addActionListener((e) -> {
			try {
				double peek = stog.pop();
				model.setValue(peek);
				display.setText(model.toString());
			} catch(EmptyStackException ex) {
				throw new CalculatorInputException("Stack is empty");
			}
		});
		cp.add(pop, new RCPosition(4,7));
		//stack operations end
		//decimal point start
		DecimalPointButton point = new DecimalPointButton(pointListener, model);
		cp.add(point, new RCPosition(5, 5));
		//decimal point end
		//plusMinus start
		SignSwapButton plusMinus = new SignSwapButton(signListener, model);
		cp.add(plusMinus, new RCPosition(5, 4));
		//plusMinus end
		//clear operations start
		ClearButton clr = new ClearButton(clearListener, model);
		cp.add(clr, new RCPosition(1, 7));
		ResetButton res = new ResetButton(resetListener, model);
		cp.add(res, new RCPosition(2, 7));
		//clear operations end
		//echo start
		EchoButton echo = new EchoButton(echoListener, model);
		cp.add(echo, new RCPosition(1, 6));
		//echo end
		JButton inverz = new JButton("1/x");
		inverz.setBackground(new Color(150,128,201));
		inverz.addActionListener((e) -> {
			model.setValue(1.0 / model.getValue());
			model.getListenerList().get(0).valueChanged(model);
		});
		cp.add(inverz, new RCPosition(2, 1));
		//unary operations start
		UnaryButton sin = new UnaryButton(unaryListener, "sin", model, inv);
		cp.add(sin, new RCPosition(2, 2));
		//unary operations end
		UnaryButton log = new UnaryButton(unaryListener, "log", model, inv);
		cp.add(log, new RCPosition(3, 1));
		UnaryButton cos = new UnaryButton(unaryListener, "cos", model, inv);
		cp.add(cos, new RCPosition(3,2));
		UnaryButton ln = new UnaryButton(unaryListener, "ln", model, inv);
		cp.add(ln, new RCPosition(4,1));
		UnaryButton tan = new UnaryButton(unaryListener, "tan", model, inv);
		cp.add(tan, new RCPosition(4,2));
		UnaryButton ctg = new UnaryButton(unaryListener, "ctg", model, inv);
		cp.add(ctg, new RCPosition(5,2));
		cp.add(inv, new RCPosition(5,  7));
		inv.addActionListener((e) -> {
			if(inv.isSelected() == true) {
				sin.setText("arcsin");
				cos.setText("arccos");
				tan.setText("arctan");
				ctg.setText("arcctg");
				log.setText("10^x");
				ln.setText("e^x");
				pow.setText("x^(1/n)");
			} else {
				sin.setText("sin");
				cos.setText("cos");
				tan.setText("tan");
				ctg.setText("ctg");
				log.setText("log");
				ln.setText("ln");
				pow.setText("x^n");
			}
		});
	}
	
	/**
	 * Perform operation of a^(1/b)
	 * @param a the base
	 * @param b the exponent
	 * @returns result of performed operation
	 */
	public static double inverzPow(double a, double b) {
		return Math.pow(a, 1.0 / b);
	}
	
	/**
	 * Calculates cotangens for some angle in radian
	 * @param a angle in radians
	 * @returns ctg(angle)
	 */
	public static double ctg(double a) {
		return 1.0 / Math.tan(a);
	}
	
	/**
	 * Calculates actg for specific value
	 * @param a value for which actg will be calculated
	 * @returns angle in radians
	 */
	public static double actg(double a) {
		return (Math.PI / 2.0) - Math.atan(a);
	}

	/**
	 * Performs operation of powering with base 10
	 * @param a exponent
	 * @returns result of exponentiation with base 10
	 */
	public static double pow10(double a) {
		return Math.pow(10, a);
	}

	/**
	 * Divides two number
	 * @param a dividendr
	 * @param b divisor
	 * @returns result of division
	 */
	public static double divide(double a, double b) {
		return a/b;
	}
	
	/**
	 * Performs multiplication of two numbers
	 * @param a mutliplier
	 * @param b multiplicands
	 * @returns result of multiplication
	 */
	public static double multiply(double a, double b) {
		return a*b;
	}
	
	/**
	 * Performs subtraction of 2 numbers
	 * @param a minuend
	 * @param b subtrahend
	 * @returns reuslt of subtraction
	 */
	public static double sub(double a, double b) {
		return a-b;
	}
	
	
	

}
