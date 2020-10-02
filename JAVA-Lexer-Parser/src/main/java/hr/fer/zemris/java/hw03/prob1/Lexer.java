package hr.fer.zemris.java.hw03.prob1;


/**
 * @author Andi Škrgat
 * @version 1.0
 * Lexer is used as a system for doing lexical analysis of some input that can be e.g source code of some program and output is sequence of tokens.
 * It works in 2 states, basic and extended.  
 */
public class Lexer {
	
	private char[] data;
	private Token token;
	private int currentIndex;
	private LexerState state;
	
	/**
	 * @param text input that will be analyzed
	 */
	public Lexer(String text) {
		data = text.toCharArray();
		currentIndex = 0;
		state = LexerState.BASIC;
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
	 * @returns next token from input or throws LexerException if there was mistake in running code. 
	 */
	public Token nextToken() {
		int length = data.length;
		TokenType currentType = null;
		StringBuilder sb = new StringBuilder();
		if(currentIndex == length) { //if method was called first time after end was reached token with tokentype eof is generated
			token = new Token(TokenType.EOF, null);
			++currentIndex;
			return token;
		}
		else if(currentIndex > length) { //method was called second or third time... and exception is thrown
			throw new LexerException("EOF reached, no more characters to analyze.");
		}
		if(state == LexerState.BASIC) { 
			while(true) {
				if(currentIndex == length) { //if reached end of the input then break and return token
					break;
				}
				else if(currentType == null) {	//if we don't know yet type of token we are generating
					if(Character.isLetter(data[currentIndex]) == true) {
						currentType = TokenType.WORD;
						sb.append(data[currentIndex++]);		
					}
					else if(Character.isDigit(data[currentIndex]) == true) {		
						currentType = TokenType.NUMBER;
						sb.append(data[currentIndex++]);			
					}
					else if(Character.isSpace(data[currentIndex]) == true) { //if blank then just skip
						currentIndex++;
					}
					else if(data[currentIndex] == '\\') { //escaping
						if(currentIndex < length - 1 && (Character.isDigit(data[currentIndex+1]) == true || data[currentIndex + 1] == '\\')) {
							currentType = TokenType.WORD;
							sb.append(data[currentIndex +1]);
							currentIndex += 2;
						} else { //if it isn't regular thar LexerException
							throw new LexerException("Mistake in extracting tokens");
						}
					}
					else { //only symbol left so we return it
						Character c = Character.valueOf(data[currentIndex++]);
						this.token = new Token(TokenType.SYMBOL, c);
						return token;
					}
				}	
				else { // if we initialized type of token we are searching for
					if(Character.isLetter(data[currentIndex]) == true && currentType == TokenType.WORD) {
						sb.append(data[currentIndex++]);		
					}
					else if(Character.isDigit(data[currentIndex]) == true && currentType == TokenType.NUMBER) {
							sb.append(data[currentIndex++]);
					}
					else if(Character.isSpace(data[currentIndex]) == true) { //here blank means that we have to end our token
						currentIndex++;
						break;
					}
					else if(data[currentIndex] == '\\') { //possible escaping				
						if(currentIndex < length - 1 && (Character.isDigit(data[currentIndex+1]) == true || data[currentIndex + 1] == '\\')) {
							sb.append(data[currentIndex +1]);
							currentIndex += 2;
						} else {
							throw new LexerException("Mistake in extracting tokens");
						}
					}
					else if(Character.isDigit(data[currentIndex]) == true && currentType == TokenType.WORD ||
							Character.isLetter(data[currentIndex]) == true && currentType == TokenType.NUMBER) { //wrong data, letter or digit
						break;
					}						
					else { //we reached symbol so we'll generate past token
						break;					}
				}		
			}
			
			//we reached blank or symbol
			if(currentType == TokenType.WORD) { //type was word
				String value = sb.toString();
				this.token = new Token(TokenType.WORD, value);
				return token;
			}
			else if(currentType == TokenType.NUMBER) { //type was number
				Long value = null;
				try {
					value = Long.parseLong(sb.toString());
				} catch(NumberFormatException ex) {
					throw new LexerException("Error occured while converting to number.");
				}
				this.token = new Token(TokenType.NUMBER, value);
				
				return token;
			}
			else { //there wasn't past token-eof reached
				this.token = new Token(TokenType.EOF, null);
				currentIndex++;
				return token;
			}
		} else { // we are in extended mode
			while(true) { 
				if(currentIndex == length) { //eof reached
					break;
				}
				if(currentType == null) { //same... we didn't initalize
					if(Character.isSpace(data[currentIndex]) == true) { //if blank just skip
						currentIndex++;
					}
					else if(data[currentIndex] == '#') { //symbol representing end and nothing was saved before so we return it
						Character c = Character.valueOf(data[currentIndex++]);
						this.token = new Token(TokenType.SYMBOL, c);
						return token;
					}
					else { //letter or symbol, go on
						currentType = TokenType.WORD;
						sb.append(data[currentIndex++]);
					}
				} else { //type is initalized
					if(Character.isSpace(data[currentIndex]) == true) { //blank is skipped and wer are returning past token
						currentIndex++;
						break;
					}
					else if( data[currentIndex] == '#') { //end symbol
						break;
					}
					else { //letter or symbol, go on
						sb.append(data[currentIndex++]);
					}
				}
			}
			if(currentType == TokenType.WORD) {
				this.token = new Token(TokenType.WORD, sb.toString());
			}
			else {
				this.token = new Token(TokenType.EOF, null);
				currentIndex++;
			}
			return token;
		}
	}
	
	/**
	 * @returns last generated token, it can be called more than once and it does not start generating next token.
	 */
	public Token getToken() {
		return token;
	}
}
