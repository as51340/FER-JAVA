package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;

/**
 * Enum so lexer could know whether tokens are being extracted in {@linkplain EchoNode} or {@linkplain ForLoopNode}
 * @author Andi Škrgat
 * @version 1.0
 */
public enum LexerType {
	FOR, ECHO;
}
