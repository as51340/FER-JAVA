package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Program that accepts a single argument from command line-filename. It opens file with given file name, parses it into a tree and then 
 * reproduces its approximate original form onto standard output
 * @author Andi Škrgat
 * @version 1.0
 */
public class TreeWriter {
	
	/**
	 * Implementation of {@linkplain INodeVisitor} for parsing file into a tree and reproducing its approximate content
	 * @author Andi Škrgat
	 * @version 1.0
	 */
	public static class WriterVisitor implements INodeVisitor{

		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.getText());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.print(node);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.print(node);
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			intern(node);
		}
		
		/**
		 * Intern method that visitor uses for visiting document node. It recursively passes children and gets its content.
		 * @param child root {@linkplain DocumentNode}
		 */
		private void intern(Node child) {
			if(child instanceof TextNode) {
				((TextNode) child).accept(this);
			} else if(child instanceof EchoNode) {
				((EchoNode) child).accept(this);
			} else if(child instanceof ForLoopNode) {
				((ForLoopNode) child).accept(this);
			}
			int number = 0;
			try {
				number = child.numberOfChildren();
				for(int i = 0; i < number; i++) {
					Node child2 = child.getChild(i);
					if(child2 != null) {
						intern(child2);
					}
				}
			} catch(NullPointerException ex) {
				//System.out.println(ex.getLocalizedMessage()); // null pointer happens because intern collection is allocated only first time object
				//is inserted into collection
			}
			
			if(child instanceof ForLoopNode) {
				System.out.print("{$END$}");
			}
		}
	}

	/**
	 * Main method from where program starts
	 * @param args program accepts a single argument from command line-filename
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		if(args.length != 1) {
			throw new IllegalArgumentException("File name expected");
		}
		String filename = args[0];
//		String filename = "src/test/resources/problem2TestFiles/primjer9.txt";
		String docBody = new String(
		 Files.readAllBytes(Paths.get(filename)),
		 StandardCharsets.UTF_8
		);
		SmartScriptParser p = new SmartScriptParser(docBody);
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);
	}
}
