package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;

/**
 * Lexer is used as a system for doing lexical analysis of some input that can be e.g source code of some program and output is sequence of tokens.
 * It will work in various states due to several possible types of inputs that can be provided.
 * @author Andi Škrgat
 * @version 1.0
 */
public class Lexer {
	
	/**
	 * Data that will be analysed
	 */
	private char[] data;
	
	/**
	 * Current tokem
	 */
	private Token element;
	
	/**
	 * Current index in data
	 */
	private int currentIndex;
	
	/**
	 * Inside or outside mode. Differences are e.g inside and outside escaping
	 */
	private LexerState state;
	
	/**
	 * Intern information for lexer(whether it is extracting tokens for {@linkplain EchoNode} or {@linkplain ForLoopNode}
	 */
	private LexerType type;
	
	/**
	 * Information used for {@linkplain ForLoopNode} because number of elements in {@linkplain ForLoopNode} is limited
	 */
	private int param = 0;
	
	/**
	 * Information if we reached end of the block
	 */
	private int c = 0;
	
	/**
	 * @param text input that will be analyzed
	 */
	public Lexer(String text) {
		data = text.toCharArray();
		currentIndex = 0;
		state = LexerState.OUTSIDE;
	}
	
	/**
	 * Sets state of lexer to value of enum LexerState state.
	 * @param state given state
	 */
	public void setState(LexerState state) {
		if(state == null) {
			throw new NullPointerException("State can't be null!");
		}
		this.state = state;
	}
	
	/**
	 * @returns next token from input or throws LexerException if there was mistake in running code. Return type is {@linkplain Token} and its value can
	 * be {@linkplain ElementString}, {@linkplain ElementFunction}, {@linkplain ElementOperator}, {@linkplain ElementConstantInteger} or {@linkplain ElementConstantDouble},
	 * Lexer is using some rules about escaping, it's only possible to have \\ and \" in tag and \\ \{ out of tag.
	 */
	@SuppressWarnings("deprecation")
	public Token nextToken() {
		int length = data.length;
		if(c == 1 && type == LexerType.FOR) { // this block uses private variable c for information if we entered echo, for or end block
			Element value = new ElementString("FOR");
			this.element = new Token(TokenType.FOR, value);
			c = 0;
			return element;
		} else if(c == 1 && type == LexerType.ECHO) {
			Element value = new ElementString("=");
			this.element = new Token(TokenType.ECHO, value);
			c = 0;
			return element;
		} else if(c == 2) {
			Element value = new ElementString("END");
			this.element = new Token(TokenType.END, value);
			param = 0;
			c = 0;
			return element;
		}
		if(currentIndex >= length) { //if we reached end ElementString with value EOF is returned so parser could now when is end reached
			Element value = new ElementString("EOF");
			this.element = new Token(TokenType.EOF, value);
			return this.element;
		}
		StringBuilder sb = new StringBuilder();
		if(state == LexerState.OUTSIDE) {//if we are out of tag
			while(true) {
				if(currentIndex >  length -1)  {
					Element value = new ElementString(sb.toString()); //if end is reached
					this.element = new Token(TokenType.TEXT, value);
					return element;
				}
				if(data[currentIndex] == '{'){//if next char is $ then we are entering some new mode
					if(currentIndex < length -1 && data[currentIndex + 1] == '$') {
						c = check();
						//System.out.println(c);
						if(c == 1) { //for or echo block is next
							setState(LexerState.INSIDE); //Lexer changes mode to inside
							Element value = new ElementString(sb.toString());
							this.element = new Token(TokenType.TEXT, value);
							return element;
						}  else if(c == 2){ //end block 
							Element value = new ElementString(sb.toString());
							this.element = new Token(TokenType.TEXT, value);
							return element;
						} else if(c == 3) {
							sb = extractVariable(data, sb);
							Element el = new ElementVariable(sb.toString());
							element = new Token(TokenType.TAGNAME, el);
							return element;
						}
					} else {
						sb.append(data[currentIndex++]);
					}
				}
				 else if(data[currentIndex] == '\\'){ //check if there is regular escape 
					if(currentIndex == length - 1) {
						throw new SmartScriptParserException("Wrong outside escaping...");
					}
					else if(currentIndex < length - 1 && (data[currentIndex +1] == '{'  || data[currentIndex +1]  == '\\')) {
						sb.append(data[currentIndex +1]);
						currentIndex += 2;
					} else {
						throw new SmartScriptParserException("Wrong outside escaping...");
					}
				} else{
					sb.append(data[currentIndex++]);
				} 
			}
		} else if(state == LexerState.INSIDE) { //we came inside some tag 
				if(currentIndex >= length) {
					throw new SmartScriptParserException("Somewhere near end"); //because it means tag isn't closed
				}
				if(type == LexerType.ECHO)  { //we read = so we'll be extracting echo tokens
					if(data[currentIndex] == '\"') { //possible string next
//						sb.append(data[currentIndex++]);
						currentIndex++;
						sb = extractGeneral(data, sb);
						Element value = new ElementString(sb.toString());
						element = new Token(TokenType.STRING, value);
						return element;
					}
					if(Character.isLetter(data[currentIndex]) == true) { //if letter is first then try to extract variable
						//System.out.println(data[currentIndex]);
						sb.append(data[currentIndex++]);
						sb = extractVariable(data, sb);
						Element value = new ElementVariable(sb.toString());
						this.element = new Token(TokenType.VARIABLE, value);
						//System.out.println(el.asText());
						return element;
					} else if(currentIndex < length - 1 && data[currentIndex] == '@' && Character.isLetter(data[currentIndex + 1]) == true) { 
						currentIndex++; //we read @ next token will be ElementFunction
						sb = extractVariable(data, sb);
						Element value = new ElementFunction(sb.toString());
						//System.out.println(el.asText());
						this.element = new Token(TokenType.FUNCTION, value);
						return element;
					} else if(Character.isDigit(data[currentIndex]) == true) { //try to extract number
						sb.append(data[currentIndex++]);
						Element value = extractNumber(data, sb);
						element = new Token(TokenType.NUMBER, value);
						return element;
					} else if(data[currentIndex] == '+' || data[currentIndex] == '*' || data[currentIndex] == '/' || data[currentIndex] == '^') {
						sb.append(data[currentIndex++]); //try to extract variable
						skipBlanks();
						Element el = new ElementOperator(sb.toString());
						this.element = new Token(TokenType.OPERATOR, el);
						return element;
					}
					else if(data[currentIndex] == '-') { //check if next char is digit or something else
						if(currentIndex < length -1 && Character.isDigit(data[currentIndex + 1]) == true) {
							sb.append(data[currentIndex++]);
							Element value = extractNumber(data, sb);
							element = new Token(TokenType.NUMBER, value);
							return element;
						} else {
							currentIndex++;
							Element value = new ElementOperator("-");
							element = new Token(TokenType.OPERATOR, value);
							return element;
						}
					}
					else if(data[currentIndex] == '$') { //possible end of the block
						boolean end = checkEnd(data);
						if(end == true) {
							setState(LexerState.OUTSIDE);
							Element el = new ElementString("ECHOEND");
							this.element = new Token(TokenType.ECHOEND, el);
							return element;	
						} else {
							throw new SmartScriptParserException("Nor regular ending");
						}
						
					}else if(Character.isSpace(data[currentIndex]) == true) {
						skipBlanks();
						return nextToken();
					}
				
				} else if(type == LexerType.FOR) { //we are extracting tokens for FOR block
					if(currentIndex >= length) {
						throw new SmartScriptParserException("Cannot extract anything...");
					}
					if(data[currentIndex] == '\"') { // possible string next
//						sb.append(data[currentIndex++]);
						currentIndex++;
						sb = extractGeneral(data, sb);
						Element value = new ElementString(sb.toString());
						element = new Token(TokenType.STRING, value);
						param++;
						return element;
					}
					if(param == 0) { //in FOR block first variable must be first
						if(Character.isLetter(data[currentIndex]) == true) {
							sb.append(data[currentIndex++]);
							sb = extractVariable(data, sb);
							param++;
							Element value = new ElementVariable(sb.toString());
							this.element = new Token(TokenType.VARIABLE, value);
							return element;
						} 	
						
						else  {
							throw new SmartScriptParserException("Parsing not possible, there is no variable at the first place");
						}
					} else { //we read variable, go on
						if(Character.isDigit(data[currentIndex]) == true) { //try to extract number
							sb.append(data[currentIndex++]);
							Element el = extractNumber(data, sb);
							element = new Token(TokenType.NUMBER, el);
							param++;
							if(param > 4) { //there should be max 4 param
								throw new SmartScriptParserException("Too many arguments");
							}
							return element;
						}  else if(data[currentIndex] == '@') { //functions are not allowed in FOR block
							throw new SmartScriptParserException("Functions are not allowed in for tag");
							
						} if(Character.isLetter(data[currentIndex]) == true) { //try to extract element
							sb.append(data[currentIndex++]);
							sb = extractVariable(data, sb);
							Element value = new ElementVariable(sb.toString());
							this.element = new Token(TokenType.VARIABLE, value);
							param++;
							//System.out.println(data[currentIndex+ 4]);
							if(param > 4) {
								throw new SmartScriptParserException("Too many arguments");
							}					
							return element;
						} else if(data[currentIndex] == '+' || data[currentIndex] == '*' || data[currentIndex] == '/' || data[currentIndex] == '^') {
							throw new SmartScriptParserException("Operators are not allowed in for tag");
							
						} else if(data[currentIndex] == '-') { //-can here only be used for number
							if(currentIndex < length -1 && Character.isDigit(data[currentIndex + 1]) == true) {
								sb.append(data[currentIndex++]);
								Element el= extractNumber(data, sb);
								element = new Token(TokenType.NUMBER, el);
								param++;
								if(param > 4) {
									throw new SmartScriptParserException("Too many arguments");
								}
								return element;
							} else {
								throw new SmartScriptParserException("Operators are not allowed in for tag");
							}
						}
						else if(Character.isSpace(data[currentIndex]) == true) { //just for check
							skipBlanks();
							return nextToken();
						}
						else if(data[currentIndex] == '$') { //possible end of the block
							boolean end = checkEnd(data);
							if(end == true) {
								setState(LexerState.OUTSIDE);
								Element el = new ElementString("FOREND");
								this.element = new Token(TokenType.FOREND, el);
								if(param < 3) {
									throw new SmartScriptParserException("Too few arguments");
								}
								return element;	
							} else {
								throw new SmartScriptParserException("Nor regular ending");
							}
							
						}
					}
				}
		}
		throw new SmartScriptParserException("Nothing was returned");
	}
			
	/**
	 * Method used for extraction variables
	 * @param data char[] input string
	 * @param sb StringBuilder that stores string
	 * @return StringBuilder that stores string
	 */
	@SuppressWarnings("deprecation")
	private StringBuilder extractVariable(char[] data, StringBuilder sb) {
		while(currentIndex < data.length) {
			if(Character.isSpace(data[currentIndex]) == true){
				skipBlanks();
				break;
			}else if(Character.isLetter(data[currentIndex]) == false && Character.isDigit(data[currentIndex]) == false && data[currentIndex] != '_') {
				break;
			}  else {
				sb.append(data[currentIndex++]);
			}
		}
		return sb;
	}
	
	/**
	 * Method used for extraction string from input.
	 * @param data input string
	 * @param sb StringBuilder
	 * @returns StringBuilder in which string will be stored
	 */
	private StringBuilder extractGeneral(char[] data, StringBuilder sb) {
		while(currentIndex < data.length) {
			if(data[currentIndex] == '\\') { 
				if(currentIndex < data.length -1 && (data[currentIndex +1] == '\\' || data[currentIndex +1] == '\"'  )) {
					sb.append(data[currentIndex+ 1]);
					currentIndex +=2;
				}  else if(data[currentIndex +1] == 'n') {
					sb.append('\n');
					currentIndex +=2;
				} else if(data[currentIndex +1] == 't') {
					sb.append('\t');
					currentIndex +=2;
				} else if(data[currentIndex +1] == 'r') {
					sb.append('\r');
					currentIndex +=2;
				}else {
					throw new SmartScriptParserException("Inside escape not allowed");	
				}
			}else if(data[currentIndex] == '\"') {
				currentIndex++;
				skipBlanks();
				break;
			}else {
				sb.append(data[currentIndex++]);
			}
		}
		return sb;
	}
	
	/**
	 * Extracts number form lexer input
	 * @param data string input
	 * @param sb StringBuilder
	 * @returns StringBuilder
	 */
	@SuppressWarnings("deprecation")
	private Element extractNumber(char[] data, StringBuilder sb) {
		boolean usedDot = false;
		while(currentIndex < data.length) {
			if(Character.isDigit(data[currentIndex]) == true) {
				sb.append(data[currentIndex++]);
			} else if(data[currentIndex] == '.' && usedDot == false) {
				usedDot = true;
				sb.append('.');
				currentIndex++;
			} else if(Character.isSpace(data[currentIndex]) == true){
				skipBlanks();
				break;
			} else {
				break;
			}
		}
		
		Element el = null;
		if(usedDot == false) {
			try {
				Integer i = Integer.parseInt(sb.toString());
				el = new ElementConstantInteger(i);
			} catch(NumberFormatException ex) {
				System.out.println("Mistake happened, that is not integer");
			}
		} else {
			try {
				Double d = Double.parseDouble(sb.toString());
				el = new ElementConstantDouble(d);
			} catch(NumberFormatException ex) {
				System.out.println("Mistake happened, that is not double");
			}
		}
		return el;
	}
	
	/**
	 * Checks for end of the tag
	 * @param data lexer's data
	 * @returns true if there is regular end of the tag $}, else otherwise
	 */
	private boolean checkEnd(char[] data) {
		if(currentIndex >= data.length -1) {
			throw new SmartScriptParserException("There's no end of tag");
		}
		if(data[currentIndex] == '$') {
			 if(data[currentIndex +1] == '}') {
				currentIndex += 2;
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * Method we'll internally use for skipping blanks inside tags
	 * @returns true if we reached end of the input, false otherwise
	 */
	@SuppressWarnings("deprecation")
	private void skipBlanks() {
		int length = data.length; 
		while(true) {
			if(currentIndex == length) {
				System.out.println("Pogreška u brisanju praznina");
				return;
			}
			if(Character.isSpace(data[currentIndex]) == true) {
				currentIndex++;
			}
			else {
				return;
			}
		}
	}
	
	/**
	 * Method used for checking if after {$ there is valid tag name
	 * @returns 1 for FOR or ECHO, 2 for END and 3 for something else
	 */
	private int check() {
		int length = data.length;
		if(type == null ||( data[currentIndex] == '{' && data[currentIndex +1] == '$')) { //we're at the beginning
			currentIndex += 2;
			skipBlanks();
			if(currentIndex >= length) {
				throw new SmartScriptParserException("Cannot extract anything...");
			}
			if(data[currentIndex] == '=') {
				currentIndex++;
				skipBlanks();
				type = LexerType.ECHO;
				return 	1;
			} else if(currentIndex < length - 2 && Character.toUpperCase(data[currentIndex]) == 'F' && 
					Character.toUpperCase(data[currentIndex + 1]) == 'O' && Character.toUpperCase(data[currentIndex + 2]) == 'R') {
				currentIndex += 3;
				skipBlanks();
				type = LexerType.FOR;
				return 1;
			} else if(currentIndex < length - 2 && Character.toUpperCase(data[currentIndex]) == 'E' && 
					Character.toUpperCase(data[currentIndex + 1]) == 'N' && Character.toUpperCase(data[currentIndex + 2]) == 'D'
					) {
				currentIndex += 3;
				skipBlanks();
				if(currentIndex < length - 1 && data[currentIndex] == '$' && data[currentIndex +1] == '}') {
					currentIndex += 2;
					return 2;
				} else {
					throw new SmartScriptParserException("No ending of end tag");//moguce da ce tu trebat jos neki tag name provjeriti
				}	
			} else {
				return 3;
			}
		}
		throw new SmartScriptParserException("Ne postoji takav tag");
	}
	
	/**
	 * @returns last generated token, it can be called more than once and it does not start generating next token.
	 */
	public Token getToken() {
		return element;
	}

}