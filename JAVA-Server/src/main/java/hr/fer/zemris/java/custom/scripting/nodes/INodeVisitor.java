package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Visitor that defines methods for executing some job on concrete {@linkplain Node}
 * @author Andi Škrgat
 * @version 1.0
 */
public interface INodeVisitor {
	
	/**
	 * Performs job on {@linkplain TextNode}
	 * @param node node on which job will be done
	 */
	public void visitTextNode(TextNode node);
	
	/**
	 * Performs job on {@linkplain ForLoopNode}
	 * @param node node on which job will be done
	 */
	public void visitForLoopNode(ForLoopNode node);

	/**
	 * Performs job on {@linkplain EchoNode}
	 * @param node node on which job will be done
	 */
	public void visitEchoNode(EchoNode node);
	
	/**
	 * Performs job on {@linkplain DocumentNode}
	 * @param node node on which job will be done
	 */
	public void visitDocumentNode(DocumentNode node);
}

