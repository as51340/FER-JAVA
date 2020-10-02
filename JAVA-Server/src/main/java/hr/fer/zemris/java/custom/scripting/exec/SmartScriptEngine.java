package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.NoSuchElementException;
import java.util.Stack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.multistack.ObjectMultistack;
import hr.fer.zemris.java.multistack.ValueWrapper;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.SmartHttpServer;

/**
 * Executes the document that was parsed with {@linkplain SmartScriptParser}. Used for generating dynamic content in {@linkplain SmartHttpServer}
 * @author Andi Škrgat
 * @version 1.0
 */
public class SmartScriptEngine {

	/**
	 * Reference to the document node containing all nodes from one given file
	 */
	private DocumentNode documentNode;
	
	/**
	 * Reference to the {@linkplain RequestContext} that will generate concrete request TODO
	 */
	private RequestContext requestContext;
	
	/**
	 * Multistack used in implementation of visitor that passes through all nodes and performs required operation
	 */
	private ObjectMultistack multistack = new ObjectMultistack();
	
	/**
	 * Visitor that will loop through all tags and perform concrete operations
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		/**
		 * Writes text that this node contains using output stream from {@linkplain RequestContext} 
		 */
		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				throw new RuntimeException("Error happened while writing text of some text node");
			}
		}
		
		/**
		 * Calls accepts on all its node children while iterating over for loop.
		 */
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			int numChildren = node.numberOfChildren();
			ElementVariable newVar = new ElementVariable(node.getVariable().getName());
			String varName = newVar.getName();
			ElementConstantInteger startElem = null; //extract start expression
			ElementConstantInteger endElem = null; //extract end expression		
			ElementConstantInteger offElem = null; //get offset
			try {
				startElem = (ElementConstantInteger) node.getStartExpression();
				endElem = (ElementConstantInteger) node.getEndExpression();
				offElem = (ElementConstantInteger) node.getStepExpression();
			} catch(ClassCastException ex) {
				throw new RuntimeException("Cannot set, end or offset value for ForLoopNode");
			}
			int startValue = startElem.getValue(); //get element's value
			int endValue = endElem.getValue(); //get end value
			int offValue = offElem.getValue();
			multistack.push(varName, new ValueWrapper(startValue));
			int i = startValue;
			for(; i <= endValue; i+=offValue) {
				for(int j = 0; j < numChildren; j++) {
					Node child = node.getChild(j);
					if(child instanceof TextNode) {
						((TextNode) child).accept(visitor);
					} else if(child instanceof EchoNode) {
						((EchoNode) child).accept(visitor);
					} else if(child instanceof ForLoopNode) {
						((ForLoopNode) child).accept(visitor);
					} else {
						throw new RuntimeException("For loop node containt unimplemented nodes in its structure");
					}
				}
				int currValue = (Integer) multistack.peek(varName).getValue(); //get top element on stack 
				currValue+=offValue;
				multistack.push(varName, new ValueWrapper(currValue));
			}
			multistack.pop(varName); //removes one instance of variable when ForLoopNode end is reached
		}

		/**
		 * Performs useful operations when use of operator or function was specified
		 */
		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> tempStack = new Stack<Object>(); //temp stack used for storing temporary values on it
			Element[] tokens = node.getElements();
			for(Element el: tokens) {
				if(el == null) {
					continue;
				}
				if(el instanceof ElementConstantInteger) {
					tempStack.push(((ElementConstantInteger) el).getValue());
				} else if(el instanceof ElementConstantDouble) {
					tempStack.push(((ElementConstantDouble) el).getValue());
				} else if(el instanceof ElementString) {
					tempStack.push(((ElementString) el).getValue());
				} else if(el instanceof ElementVariable) {
					try {
						ValueWrapper vp = multistack.peek(((ElementVariable) el).getName());
						tempStack.push(vp.getValue());
					} catch(NoSuchElementException ex) { 
						multistack.push(((ElementVariable) el).getName(), new ValueWrapper(-1)); //push new value if nothing exists
					}
				} else if(el instanceof ElementOperator) {
					ElementOperator operator = (ElementOperator) el;
					Object val1 = tempStack.pop();
					Object val2 = tempStack.pop();
					ValueWrapper vp1 = new ValueWrapper(val1);
					String operatorName = operator.asText();
					if(operatorName.equals("+") == true) {
						vp1.add(val2);
					} else if(operatorName.equals("-") == true) {
						vp1.subtract(val2);
					} else if(operatorName.equals("*") == true) {
						vp1.multiply(val2);
					} else if(operatorName.equals("/") == true) {
						vp1.divide(val2);
					} else {
						throw new RuntimeException("No such operator defined");
					}
					tempStack.push(vp1.getValue());
				} else if(el instanceof ElementFunction) {
					ElementFunction ef = (ElementFunction) el;
					String funName = ef.getName();
					if(funName.equals("sin") == true) {
						operSin(tempStack);
					} else if(funName.equals("decfmt") == true) {
						operDecfmt(tempStack);
					} else if(funName.equals("dup") == true) {
						operDup(tempStack);
					} else if(funName.equals("swap") == true) {
						operSwap(tempStack);
					} else if(funName.equals("setMimeType") == true) {
						operSetMimeType(tempStack);
					} else if(funName.equals("paramGet") == true) {
						operParamGet(tempStack);
					} else if(funName.equals("pparamGet") == true) {
						operPParamGet(tempStack);
					} else if(funName.equals("pparamSet") == true) {
						operPParamSet(tempStack);
					} else if(funName.equals("tparamGet") == true) {
						operTParamGet(tempStack);
					} else if(funName.equals("tparamSet") == true) {
						operTParamSet(tempStack);
					} else if(funName.equals("tparamDel") == true) {
						operTParamDel(tempStack);
					} else if(funName.equals("tparamDel") == true) {
						operPParamDel(tempStack);
					} 
				} else {
					throw new RuntimeException("Unfamiliar token in echo node");
				}
			}
			Stack<Object> temp2 = new Stack<Object>();
			while(tempStack.isEmpty() == false) {
				temp2.push(tempStack.pop());
			}
			while(temp2.isEmpty() == false) {
				try {
					requestContext.write(temp2.pop().toString());
				} catch (IOException e) {
					throw new RuntimeException("Cannot write final echo ending");
				}
			}
		}

		/**
		 * {@inheritDoc}
		 * Calls accept for all direct children
		 */
		@Override
		public void visitDocumentNode(DocumentNode node) {
			int numChildren =  node.numberOfChildren();
			for(int i = 0; i < numChildren; i++) {
				Node child = node.getChild(i);
				if(child instanceof TextNode) {
					((TextNode) child).accept(visitor);
				} else if(child instanceof EchoNode) {
					((EchoNode) child).accept(visitor);
				} else if(child instanceof ForLoopNode) {
					((ForLoopNode) child).accept(visitor);
				} else {
					throw new RuntimeException("Document node containt unimplemented nodes in its structure");
				}
			}
			try {
				requestContext.write("\r\n".getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/**
		 * Performs operation sinus
		 * @param tempStack temporary stack for {@linkplain EchoNode}
		 */
		private void operSin(Stack<Object> tempStack) {
			Object val = tempStack.pop();
			try {
				double d = Double.parseDouble(val.toString());
				d = (d * (2 * Math.PI)) / 360;
				double sin = Math.sin(d);
				tempStack.push(sin);
			} catch(NumberFormatException ex) {
				throw new RuntimeException("Could not perform function sin");
			}
		}
		
		/**
		 * Perform decimal formatting
		 * @param tempStack temporary stack for {@linkplain EchoNode}
		 */
		private void operDecfmt(Stack<Object> tempStack) {
			Object f = tempStack.pop();
			Object x = tempStack.pop();
			String format = f.toString();
			DecimalFormat formatter = new DecimalFormat(format);
			String r = formatter.format(x);
			tempStack.push(r);
		}
		
		/**
		 * Duplicates current peek value on temp stack
		 * @param tempStack temporary stack for {@linkplain EchoNode}
		 */
		private void operDup(Stack<Object> tempStack) {
			Object val = tempStack.pop();
			tempStack.push(val);
			tempStack.push(val);
		}
		
		/**
		 * Performs swapping of 2 peek elements on stack
		 * @param tempStack temporary stack for {@linkplain EchoNode}
		 */
		private void operSwap(Stack<Object> tempStack) {
			Object val1 = tempStack.pop();
			Object val2 = tempStack.pop();
			tempStack.push(val1);
			tempStack.push(val2);	
		}
		
		/**
		 * Sets mime type
		 * @param tempStack temporary stack for {@linkplain EchoNode}
		 */
		private void operSetMimeType(Stack<Object> tempStack) {
			Object val = tempStack.pop();
			requestContext.setMimeType(val.toString());
		}
		
		/**
		 * Obtains from requestContext parameters map a value mapped for name and pushes it onto stack. If there is no such mapping, it pushes 
		 * instead defValue onto stack
		 * @param tempStack temporary stack for {@linkplain EchoNode}
		 */
		private void operParamGet(Stack<Object> tempStack) {
			Object dv = tempStack.pop();
			Object name = tempStack.pop();
			Object value = requestContext.getParameter((String)name);
			tempStack.push(value == null ? dv:value);
		}
		
		/**
		 * Obtains from requestContext persistent parameters map a value mapped for name and pushes it onto stack. If there is no such mapping, it pushes 
		 * instead defValue onto stack
		 * @param tempStack temporary stack for {@linkplain EchoNode}
		 */
		private void operPParamGet(Stack<Object> tempStack) {
			Object dv = tempStack.pop();
			Object name = tempStack.pop();
			Object value = requestContext.getPersistentParameter((String)name);
			tempStack.push(value == null ? dv:value);
		}
		
		/**
		 * Sets persistent parameter for {@linkplain RequestContext}
		 * @param tempStack temporary stack for {@linkplain EchoNode}
		 */
		private void operPParamSet(Stack<Object> tempStack) {
			Object name = tempStack.pop();
			Object value = tempStack.pop();
			requestContext.setPersistentParameter(name.toString(), value.toString());
		}
		
		/**
		 * Removes association for popped name from {@linkplain RequestContext} persistent parameters map
		 * @param tempStack temporary stack for {@linkplain EchoNode}
		 */
		private void operPParamDel(Stack<Object> tempStack) {
			Object name = tempStack.pop();
			requestContext.removePersistentParameter((String) name);
		}
		
		/**
		 * Obtains from requestContext temporary parameters map a value mapped for name and pushes it onto stack. If there is no such mapping, it pushes 
		 * instead defValue onto stack
		 * @param tempStack temporary stack for {@linkplain EchoNode}
		 */
		private void operTParamGet(Stack<Object> tempStack) {
			Object dv = tempStack.pop();
			Object name = tempStack.pop();
			Object value = requestContext.getTemporaryParameter((String)name);
			tempStack.push(value == null ? dv:value);
		}
		
		/**
		 * Sets temporary parameter for {@linkplain RequestContext}
		 * @param tempStack temporary stack for {@linkplain EchoNode}
		 */
		private void operTParamSet(Stack<Object> tempStack) {
			Object name = tempStack.pop();
			Object value = tempStack.pop();
			requestContext.setTemporaryParameter(name.toString(), value.toString());
		}
		
		/**
		 * Removes association for popped name from {@linkplain RequestContext} temporary parameters map
		 * @param tempStack temporary stack for {@linkplain EchoNode}
		 */
		private void operTParamDel(Stack<Object> tempStack) {
			Object name = tempStack.pop();
			requestContext.removeTemporaryParameter(name.toString());
		}
		
		};
	
		/**
		 * Constructor that initializies document node and request context for this script engine
		 * @param documentNode document parsed into document node
		 * @param requestContext request context for script engine
		 */
		public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
				this.documentNode = documentNode;
				this.requestContext = requestContext;
		}
		
		/**
		 * Runs document that's parsed into {@linkplain DocumentNode}
		 */
		public void execute() {
			documentNode.accept(visitor);
		}
}
