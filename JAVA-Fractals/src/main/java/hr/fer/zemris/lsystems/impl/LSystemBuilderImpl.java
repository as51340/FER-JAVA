package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.NoSuchElementException;
import java.util.Scanner;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * Represents concrete implementation of LSystemBuilder. LSystemBuilder is used to model objects we are able to configure and then return concrete Lindermayer's system based on our configuration. 
 * @author Andi Škrgat
 * @version 1.0
 */
public class LSystemBuilderImpl implements LSystemBuilder{
	
	/**
	 * Maps sequence to the symbol which will replace.
	 */
	private Dictionary<Character, String> productions;
	
	/**
	 * Maps job, that this system will do, to the specified symbol 
	 */
	private Dictionary<Character, Command> actions;
	
	/**
	 * Length of one step of turtle
	 */
	private double unitLength = 0.1;
	
	/**
	 * This variable is used so dimensions of shown fractal could be more-less constant
	 */
	private double unitLengthDegreeScaler = 1;
	
	/**
	 * Sets point from where turtle starts; represents origin for virtual coordinate system
	 */
	private Vector2D origin = new Vector2D(0, 0);
	
	/**
	 * Angle towards the plus side of x-axis,
	 */
	private double angle = 0;
	
	/**
	 * Initial sequence from where development of system starts; it can be only 1 char, but there can also be sequence of chars
	 */
	private String axiom = "";
	
	
	/**
	 * Default constructor that sets initial values for properties of this system.
	 */
	public LSystemBuilderImpl() {
		productions = new Dictionary<Character, String>();
		actions = new Dictionary<Character, Command>();
	}

	/**
	 * @returns concrete implementation of Lindermayer's system
	 */
	@Override
	public LSystem build() {
		return new LSystemImpl();
	}

	/**
	 * @returns LSystemBuilder with configuration that is set from string array
	 */
	@Override
	public LSystemBuilder configureFromText(String[] arg0) {
		for(String line: arg0) {
			if(line.isEmpty() == true) {
				continue;
			} else {
				Scanner sc = new Scanner(line);
				try {
					String token = sc.next();
					if(token.equals("origin") == true) {
						try {
							Double xComp = Double.parseDouble(sc.next());
							Double yComp = Double.parseDouble(sc.next());
							setOrigin(xComp, yComp);
						} catch(NoSuchElementException ex) {
						System.out.println("Origin line failed");
						}
					} else if(token.equals("angle") == true) {
						try {
							Double angle = Double.parseDouble(sc.next());
							setAngle(angle);
						} catch(NoSuchElementException ex) {
							System.out.println("Angle line failed");
						}
					} else if(token.equals("unitLength") == true) {
						try {
							Double unit = Double.parseDouble(sc.next());
							setUnitLength(unit);
						} catch(NoSuchElementException ex) {
							System.out.println("UnitLength line failed");
						}
					} else if(token.equals("axiom") == true) {
						try {
							String s = sc.next();
							setAxiom(s);
						} catch(NoSuchElementException ex) {
							System.out.println("Axiom line failed");
						}
					} else if(token.equals("command") == true) {
						try {
							String symbol = sc.next();
							String com = sc.next();
							if(com.equals("push") == true || com.equals("pop") == true) {
								registerCommand(symbol.charAt(0), com);
							} else if(com.equals("draw") == true || com.equals("skip") == true || com.equals("scale") == true ||
								com.equals("rotate") == true || com.equals("color") == true) {
								StringBuilder sb = new StringBuilder(com);
								sb.append(" ");
								sb.append(sc.next());
								registerCommand(symbol.charAt(0), sb.toString());
							} 
						} catch(NoSuchElementException ex) {
							System.out.println("Command line failed");
						}
					} else if(token.equals("production") == true) {
						try {
							String symbol = sc.next();
							String prod = sc.next();
							registerProduction(symbol.charAt(0), prod);
						} catch(NoSuchElementException ex) {
							System.out.println("Production line failed");
						}
					} else if(token.equals("unitLengthDegreeScaler") == true) {
						try {
							String f1 = sc.next();
							if(sc.hasNext() == true) {
								String f2 = sc.next();
								if(sc.hasNext() == true) {
									String f3 = sc.next();
									try {
										Double f1n = Double.parseDouble(f1);
										Double f2n = Double.parseDouble(f3);
										setUnitLengthDegreeScaler(f1n / f2n);
									} catch(NumberFormatException ex) {
										System.out.println("Degree scaler failer");
									}
								} else {
									if(f1.charAt(f1.length() -1) == '/') {
										f1 = f1.substring(0, f1.length() -1);
									} else if(f2.charAt(0) == '/') {
										f2 = f2.substring(1);
									} else {
										sc.close();
										throw new IllegalArgumentException("Degree scaler failed");
									}
									try {
										Double f1n = Double.parseDouble(f1);
										Double f2n = Double.parseDouble(f2);
										setUnitLengthDegreeScaler(f1n / f2n);
									} catch(NumberFormatException ex) {
										System.out.println("Degree scaler failed");
									}
								}
							} else {
								String[] data = f1.split("/");
								try {
									Double f1n = Double.parseDouble(data[0]);
									Double f2n = Double.parseDouble(data[1]);
									setUnitLengthDegreeScaler(f1n / f2n);
								} catch(NumberFormatException ex) {
									System.out.println("Degree scaler failed");
								}
							}
						} catch(NoSuchElementException ex) {
							System.out.println("Degree scaler failed");
						}
					}
				} catch(NoSuchElementException ex) {
					System.out.println("Degree scaler failed");
				}
				sc.close();
			}
		}
		return this;
	}
	
	/**
	 * Maps specific command to symbol arg0 in map actions.
	 */
	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		arg1.trim();
		Scanner sc = new Scanner(arg1);
		String text = sc.next();
		if(text.contains("draw") == true) {
			try {
				Double step = Double.parseDouble(sc.next());
				//System.out.println(step * unitLength);
				Command drawCommand = new DrawCommand(step);
				//System.out.printf(arg0 + " " + step.toString());
				//System.out.println();
				actions.put(arg0, drawCommand);
			} catch(NumberFormatException ex) {
				System.out.println("Wrong format of draw command");
			}
		} else if(text.contains("skip") == true) {
			try {
				Double step = Double.parseDouble(sc.next());
				Command skipCommand = new SkipCommand(step);
				//System.out.printf(arg0 + " " + step.toString());
				//System.out.println();
				actions.put(arg0, skipCommand);
			} catch(NumberFormatException ex) {
				System.out.println("Wrong format of skip command");
			}
		} else if(text.contains("scale") == true) {
			try {
				Double step = Double.parseDouble(sc.next());
				Command scaleCommand = new ScaleCommand(step);
				//System.out.printf(arg0 + " " + step.toString());
				//System.out.println();
				actions.put(arg0, scaleCommand);
			} catch(NumberFormatException ex) {
				System.out.println("Wrong format of scale command");
			}
		} else if(text.contains("rotate") == true) {
			try {
				Double step = Double.parseDouble(sc.next());
				Command rotateCommand = new RotateCommand(step);
				//System.out.printf(arg0 + " " + step.toString());
				//System.out.println();
				actions.put(arg0, rotateCommand);
			} catch(NumberFormatException ex) {
				System.out.println("Wrong format of rotate command");
			}
		} else if(text.contains("push") == true) {
			Command pushCommand = new PushCommand();
			//System.out.println(arg0 + " " + "push");
			actions.put(arg0, pushCommand);
		} else if(text.contains("pop") == true) {
			Command popCommand = new PopCommand();
			//System.out.println(arg0 + " " + "pop");
			actions.put(arg0, popCommand);
		} else if(text.contains("color") == true) {
			try {
				Integer int1 = Integer.parseInt(sc.next(), 16);
				Command colorCommand = new ColorCommand(new Color(int1));
				//System.out.printf(arg0 + " " + int1.toString());
				//System.out.println();
				actions.put(arg0, colorCommand);
			} catch(NumberFormatException ex) {
				System.out.println("Wrong format of color command");
			}
		} else {
			sc.close();
			throw new IllegalArgumentException("Wrong input command!");
		}
		sc.close();
		return this;
	}
	
	/**
	 * Creates one productions and puts it into map. 
	 * @param arg0 key key-symbol that will be replaced with sequence arg1
	 * @param arg1 sequence that will replace symbol
	 */
	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		productions.put(arg0, arg1);
		//System.out.println(arg0 + " " + arg1);
		return this;
	}
	
	/**
	 * Sets angle for this builder
	 * @param arg0 new angle for this builder
	 */
	@Override
	public LSystemBuilder setAngle(double arg0) {
		this.angle = arg0;
		return this;
	}
	
	/**
	 * Sets axiom from where development of system starts. 
	 * @param arg0 new axiom
	 */
	@Override
	public LSystemBuilder setAxiom(String arg0) {
		this.axiom = arg0;
		return this;
	}

	/**
	 * Sets origin of this system
	 * @param arg0 x-component of origin
	 * @param arg1 y-component of origin 
	 */
	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		Vector2D newOrigin = new Vector2D(arg0, arg1);
		this.origin = newOrigin;
		return this;
	}

	/**
	 * Sets length of one turtle step.
	 * @param arg0 new value for unitLength
	 */
	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		this.unitLength = arg0;
		return this;
	}

	/**
	 * Sets degree scaler
	 * @param arg0 new value of degree scaler
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		this.unitLengthDegreeScaler = arg0;
		return this;
	}
	
	/**
	 * Concrete implementation of LSystem used for modeling Lindermayer's system. 
	 * @author Andi Škrgat
	 * @version 1.0
	 */
	private class LSystemImpl implements LSystem {

		/**
		 * Creates new context, pushes begin state to the stack, calls generate to get final sequence and then for every symbol that has its command in actions map calls execute for concrete command.
		 * @param arg0 depth of fractal
		 * @arg1 painter used for drawing objects
		 */
		@Override
		public void draw(int arg0, Painter arg1) {
			//arg0 = 1;
			Context context = new Context();
			TurtleState beginState = new TurtleState();
			beginState.setColor(Color.BLACK);
			beginState.setTurtlePosition(origin);
			beginState.setCurrEffLength(unitLength * Math.pow(unitLengthDegreeScaler, arg0));
			Vector2D dir = new Vector2D(1, 0);
			//System.out.println(angle);
			dir.rotate(Math.toRadians(angle));
			beginState.setDirection(dir);
			context.pushState(beginState);
			String finalSequence = generate(arg0);
			char[] data = finalSequence.toCharArray();
			for(int j = 0; j < data.length; j++) {
				if(actions.containsKey(data[j]) == true) {
					Command command = actions.get(data[j]);
					command.execute(context,arg1);
				}
			}
		}

		/**
		 * Generates final sequence by applying multiple productions to axiom.
		 * @param arg0 depth of fractal
		 * @returns generated string
		 */
		@Override
		public String generate(int arg0) {
			String oldText = axiom;
			for(int i = 0; i < arg0; i++) {
				StringBuilder sb = new StringBuilder();
				char[] data = oldText.toCharArray();
				for(int j = 0; j < data.length; j++) {
					if(productions.containsKey(data[j]) == true) {
						sb.append(productions.get(data[j]));
					} else {
						sb.append(data[j]);
					}
				}
				oldText = sb.toString();
			}
			return oldText;
		}
		
		public LSystemImpl() {
			
		}
		
	}
	

}
