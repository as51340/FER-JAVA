package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * Class that represents one simple compiler with subsystems for lexical analysis and syntax tree.
 * @author Andi Škrgat
 * @version 1.0
 */
public class SmartScriptParser {
	
	/**
	 * Lexex for this parser
	 */
	Lexer lexer;
	
	/**
	 * {@linkplain ObjectStack} is internally used for storing temp elements
	 */
	ObjectStack os;
	
	/**
	 * Final product of parser
	 */
	DocumentNode docNode;
	private static final int defaultCapacity = 100;
	
	/**
	 * Constructor that creates lexer(subystem for lexical analysis) and initialize it with obtained text and then starts extracting tokens from document body.
	 * @param docBody string that contains document body.
	 */
	public SmartScriptParser(String docBody) {
		lexer = new Lexer(docBody);
		os = new ObjectStack();
		docNode = new DocumentNode();
		extract();
	}
	
	/**
	 * Method used for extraction tokens from lexer. 
	 */
	public void extract() {
		os.push(docNode);
		while(true) {
			Token token = lexer.nextToken();
			if(token.getType() == TokenType.EOF) {
				break;
			}
			else if(token.getType() == TokenType.ECHO) {
				processEcho();
			} else if(token.getType() == TokenType.FOR) {
				processFor();
			} else if(token.getType() == TokenType.END) {
				os.pop();
				if(os.isEmpty() == true) {
					throw new SmartScriptParserException("More ends than opened non-empty tags");
				}
			} else if(token.getType() == TokenType.TEXT){
				ElementString es = (ElementString) token.getValue();
				if(es.getValue().isEmpty() == false) {
					TextNode textNode = new TextNode(es.getValue());
					Node last = (Node) os.peek();
					last.addChildNode(textNode);
				}
				
			} else if(token.getType() == TokenType.TAGNAME) {
				throw new SmartScriptParserException("No appropriate node");
			}
		}
	}
	
	/**
	 * Processes all tokens for {@linkplain ForLoopNode} 
	 */
	private void processFor() {
		Element[] arr = new Element[4];
		for(int i = 0; i < 4; i++) {
			arr[i] = null;
		}
		int cnt = 0;
		while(true) {
			if(lexer.nextToken().getType() == TokenType.FOREND) {
				break;
			}
			arr[cnt++] = lexer.getToken().getValue(); 
		}
		ForLoopNode fln = new ForLoopNode((ElementVariable)arr[0],arr[1], arr[2], arr[3]);
		Node last = (Node)os.peek();
		last.addChildNode(fln);
		os.push(fln);
	}
	
	/**
	 * Processes all token for {@linkplain EchoNode}
	 */
	private void processEcho() {
		Element[] arr = new Element[defaultCapacity];
		int index = 0;
		while(true) {
			Token el = lexer.nextToken();
			if(el.getType() == TokenType.ECHOEND) {
				break;
			}
			arr[index++] = el.getValue();
		}
		EchoNode echo = new EchoNode(arr);
		Node last = (Node) os.peek();
		last.addChildNode(echo);
	}
	
	/**
	 * @returns DocumentNode
	 */
	public DocumentNode getDocumentNode() {
		return this.docNode;
	}
	

	
	
	
}
