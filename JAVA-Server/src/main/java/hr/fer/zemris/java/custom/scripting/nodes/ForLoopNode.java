package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * A node representing a single for-loop construct. It inherits {@linkplain EchoNode}
 * @author Andi Škrgat
 * @version 1.0
 */
public class ForLoopNode extends Node{
	
	/**
	 * Variable in loop
	 */
	private ElementVariable variable;
	
	/**
	 * Start expression
	 */
	private Element startExpression;
	
	/**
	 * End expression
	 */
	private Element endExpression;
	
	/**
	 * Offset while iterating
	 */
	private Element stepExpression = null;
	
	/**
	 * @return the variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}
	
	/**
	 * @return the startExpression
	 */
	public Element getStartExpression() {
		return startExpression;
	}
	
	/**
	 * @return the endExpression
	 */
	public Element getEndExpression() {
		return endExpression;
	}
	
	/**
	 * Constructor that defines all possible elements of this node
	 * @param variable ElementVariable
	 * @param startExpression startExpression
	 * @param endExpression endExpression
	 * @param stepExpression stepExpression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,Element stepExpression) {
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * @return the stepExpression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	/**
	 * @returns string representation of ForLoopNode
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{$FOR ");
		if(this.getVariable() != null) {
			sb.append(this.getVariable().asText());
			sb.append(" ");
		}
		if(this.getEndExpression() != null) {
			String text = this.getEndExpression().asText();
			sb.append(text);
			sb.append(" ");
		}
		if(this.getStartExpression() != null) {
			String text = this.getStartExpression().asText();
			sb.append(text);
			sb.append(" ");
		}
		if(this.getStepExpression() != null) {
			String text = this.getStepExpression().asText();
			sb.append(text);
			sb.append(" ");
		}
		sb.append("$}");
		return sb.toString();
	}
	
	
	/**
	 * @returns true if 2 {@linkplain ForLoopNode}  are same, else otherwise. Here it's just important that they are both instanceof {@linkplain ForLoopNode}
	 */
	@Override
	public boolean equals(Object obj) {
		ForLoopNode fln;
		if(obj instanceof ForLoopNode)  {
			fln = (ForLoopNode) obj;
		} else {
			return false;
		}
		if(this.variable != null) {
			if(fln.getVariable() == null) {
				return false;
			} else {
				if(this.variable.equals(fln.variable) == false) {
					return false;
				}
			}
		}
		if(this.stepExpression != null) {
			if(fln.getStepExpression() == null) {
				return false;
			} else {
				if(this.stepExpression.equals(fln.stepExpression) == false) {
					return false;
				}
			}
		}
		if(this.startExpression != null) {
			if(fln.getStartExpression() == null) {
				return false;
			} else {
				if(this.startExpression.equals(fln.startExpression) == false) {
					return false;
				}
			}
		}
		if(this.endExpression != null) {
			if(fln.getEndExpression() == null) {
				return false;
			} else {
				if(this.endExpression.equals(fln.endExpression) == false) {
					return false;
				}
			}
		}
		return true;
	}
	

	/**
	 * Calls visitor to do some job on it
	 * @param visitor visitor that will do some job
	 */
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}
	
}
