package hr.fer.zemris.java.hw03;

import java.nio.file.Files;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.rmi.server.LoaderHandler;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Class used for testing Parser.
 * @author Andi Škrgat
 * @version 1.0
 */
public class SmartScriptTester {
	
	/**
	 * Main method from wheres testing starts.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		String filename = "src/test/resources/extra/doc3.txt";
		String docBody = new String(
		 Files.readAllBytes(Paths.get(filename)),
		 StandardCharsets.UTF_8
		);
		
		try {
			SmartScriptParser parser = new SmartScriptParser(docBody);
			DocumentNode document = parser.getDocumentNode();
			String originalDocumentBody = document.toString();
			SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
			DocumentNode document2 = parser2.getDocumentNode();
			// now document and document2 should be structurally identical trees
			boolean same = document.equals(document2);  //==> "same" must be true
			System.out.println(same);
			System.out.println(document2.toString());
			System.out.println(document.toString());
		} catch(SmartScriptParserException ex) {
			System.out.println(ex.getMessage());
			System.exit(-1);
		}
		
		
		
		
		
		
		/*String filename = "src/test/resources/extra/doc1.txt";
		String docBody = new String(
		 Files.readAllBytes(Paths.get(filename)),
		 StandardCharsets.UTF_8
		);
		SmartScriptParser parser = null;
		*/
		
		
	}
	
	

}
