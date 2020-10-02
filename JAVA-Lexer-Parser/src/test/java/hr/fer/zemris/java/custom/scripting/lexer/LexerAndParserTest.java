package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

public class LexerAndParserTest {
	
	
	@Test
	public void test1() {
		String input = "{$ FOR i -1 10 1 $} {$END   $}";
		Lexer lexer = new Lexer(input);
		assertEquals("",lexer.nextToken().getValue().asText());
		assertEquals("FOR", lexer.nextToken().getValue().asText());
		assertEquals(ElementString.class, lexer.getToken().getValue().getClass());
		//System.out.println(lexer.nextToken().getValue().asText());
		assertEquals("i", lexer.nextToken().getValue().asText());
		assertEquals(ElementVariable.class, lexer.getToken().getValue().getClass());
		assertEquals("-1", lexer.nextToken().getValue().asText());
		assertEquals(ElementConstantInteger.class, lexer.getToken().getValue().getClass());
		assertEquals("10", lexer.nextToken().getValue().asText());
		assertEquals(ElementConstantInteger.class, lexer.getToken().getValue().getClass());
		assertEquals("1", lexer.nextToken().getValue().asText());
		assertEquals(ElementConstantInteger.class, lexer.getToken().getValue().getClass());
		assertEquals("FOREND", lexer.nextToken().getValue().asText());
		assertEquals(ElementString.class, lexer.getToken().getValue().getClass());
	}
	
	
	@Test
	public void test2() {
		String text = "{$     for    sco_re       \"-1\"10    $}";
		Lexer lexer = new Lexer(text);
		assertEquals("", lexer.nextToken().getValue().asText());
		assertEquals("FOR", lexer.nextToken().getValue().asText());
		assertEquals(ElementString.class, lexer.getToken().getValue().getClass());
		assertEquals("sco_re", lexer.nextToken().getValue().asText());
		assertEquals(ElementVariable.class, lexer.getToken().getValue().getClass());
		assertEquals("\"-1\"", lexer.nextToken().getValue().asText());
		assertEquals(ElementString.class, lexer.getToken().getValue().getClass());
		assertEquals("10", lexer.nextToken().getValue().asText());
		assertEquals(ElementConstantInteger.class, lexer.getToken().getValue().getClass());
		assertEquals("FOREND", lexer.nextToken().getValue().asText());
		};
		
		
		@Test
		public void test3() {
			String text ="{$ FOR YEAR 1   last_year $}";
			Lexer lexer = new Lexer(text);
			assertEquals("", lexer.nextToken().getValue().asText());
			assertEquals("FOR", lexer.nextToken().getValue().asText());
			assertEquals(ElementString.class, lexer.getToken().getValue().getClass());
			assertEquals("YEAR", lexer.nextToken().getValue().asText());
			assertEquals(ElementVariable.class, lexer.getToken().getValue().getClass());
			assertEquals("1", lexer.nextToken().getValue().asText());
			assertEquals(ElementConstantInteger.class, lexer.getToken().getValue().getClass());
			assertEquals("last_year", lexer.nextToken().getValue().asText());
			assertEquals(ElementVariable.class, lexer.getToken().getValue().getClass());
			
		}
		
		
		@Test
		public void test4() {
			String text = "test {$ for 3 1 10 1 $}";
			Lexer lexer = new Lexer(text);
			assertEquals("test ", lexer.nextToken().getValue().asText());
			assertEquals("FOR", lexer.nextToken().getValue().asText());
			assertThrows(SmartScriptParserException.class, () -> {
				lexer.nextToken();
			});
		}
		
		
		@Test
		public void test5() {
			String text = "test  {$ FOR * \"1\" -10 \"1\" $} ";
			Lexer lexer = new Lexer(text);
			assertEquals("test  ", lexer.nextToken().getValue().asText());
			assertEquals("FOR", lexer.nextToken().getValue().asText());
			assertThrows(SmartScriptParserException.class, () -> {
				lexer.nextToken();
			});
		}
		
		
		@Test
		public void test6() {
			String text = "ovo je neki text node {$ FOR year @sin 10 $}";
			Lexer lexer = new Lexer(text);
			assertEquals("ovo je neki text node ", lexer.nextToken().getValue().asText());
			assertEquals("FOR", lexer.nextToken().getValue().asText());
			assertEquals("year", lexer.nextToken().getValue().asText());
			assertThrows(SmartScriptParserException.class, () -> {
				lexer.nextToken();
			});
		}
		
		
		@Test
		public void test7() {
			String text = "{$ FOR year 1 10 \"1\" \"10\" $}";
			Lexer lexer = new Lexer(text);
			assertEquals("", lexer.nextToken().getValue().asText());
			assertEquals("FOR", lexer.nextToken().getValue().asText());
			assertEquals(ElementString.class, lexer.getToken().getValue().getClass());
			assertEquals("year", lexer.nextToken().getValue().asText());
			assertEquals(ElementVariable.class, lexer.getToken().getValue().getClass());
			assertEquals("1", lexer.nextToken().getValue().asText());
			assertEquals(ElementConstantInteger.class, lexer.getToken().getValue().getClass());
			assertEquals("10", lexer.nextToken().getValue().asText());
			assertEquals(ElementConstantInteger.class, lexer.getToken().getValue().getClass());
			assertEquals("\"1\"", lexer.nextToken().getValue().asText());
			assertEquals(ElementString.class, lexer.getToken().getValue().getClass());
			assertEquals("\"10\"", lexer.nextToken().getValue().asText());
			assertEquals("FOREND", lexer.nextToken().getValue().asText());
			assertEquals("EOF", lexer.nextToken().getValue().asText());
		}
		
		
		@Test
		public void test8() {
			String text = "{$ FOR year $}";
			Lexer lexer = new Lexer(text);
			assertThrows(SmartScriptParserException.class, () -> {
				lexer.nextToken();
				lexer.nextToken();
				lexer.nextToken();
				lexer.nextToken();
			});
		}
		
		
		@Test
		public void test9() {
			String text = "{$ FOR year 1 10 1 3 $}";
			Lexer lexer = new Lexer(text);
			assertThrows(SmartScriptParserException.class, () -> {
				lexer.nextToken();
				lexer.nextToken();
				lexer.nextToken();
				lexer.nextToken();
				lexer.nextToken();
				lexer.nextToken();
				lexer.nextToken();
			});
		}
		
		
		@Test
		public void test10() {
			String text = "{$ FOR i-1.35bbb\"1\" $}";
			Lexer lexer = new Lexer(text);
			assertEquals("", lexer.nextToken().getValue().asText());
			assertEquals("FOR", lexer.nextToken().getValue().asText());
			assertEquals("i", lexer.nextToken().getValue().asText());
			assertEquals("-1.35", lexer.nextToken().getValue().asText());
			assertEquals("bbb", lexer.nextToken().getValue().asText());
			assertEquals("\"1\"", lexer.nextToken().getValue().asText());
			
		}
		
		
		@Test
		public void test11() {
			String text = "{$ FOR i 1 10 1 $}";
			Lexer lexer = new Lexer(text);
			assertEquals("", lexer.nextToken().getValue().asText());
			assertEquals("FOR", lexer.nextToken().getValue().asText());
			assertEquals("i", lexer.nextToken().getValue().asText());
			assertEquals("1", lexer.nextToken().getValue().asText());
			assertEquals("10", lexer.nextToken().getValue().asText());
			assertEquals("1", lexer.nextToken().getValue().asText());
		}
		
		
		@Test
		public void test12() {
			String text = "{$= i $}";
			Lexer lexer = new Lexer(text);
			assertEquals("", lexer.nextToken().getValue().asText());
			assertEquals("=", lexer.nextToken().getValue().asText());
			assertEquals(ElementString.class, lexer.getToken().getValue().getClass());
			assertEquals("i", lexer.nextToken().getValue().asText());
			assertEquals(ElementVariable.class, lexer.getToken().getValue().getClass());
			assertEquals("ECHOEND", lexer.nextToken().getValue().asText());
			assertEquals(ElementString.class, lexer.getToken().getValue().getClass());
		}
		
		
		
		
		@Test
		public void test13() {
			String text = "sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}";
			Lexer lexer = new Lexer(text);
			assertEquals("sin(", lexer.nextToken().getValue().asText());
			assertEquals(ElementString.class, lexer.getToken().getValue().getClass());
			assertEquals("=", lexer.nextToken().getValue().asText());
			assertEquals(ElementString.class, lexer.getToken().getValue().getClass());
			assertEquals("i", lexer.nextToken().getValue().asText());
			assertEquals(ElementVariable.class, lexer.getToken().getValue().getClass());
			assertEquals("ECHOEND", lexer.nextToken().getValue().asText());
			assertEquals(ElementString.class, lexer.getToken().getValue().getClass());
			assertEquals("^2) = ", lexer.nextToken().getValue().asText());
			assertEquals(ElementString.class, lexer.getToken().getValue().getClass());
			assertEquals("=", lexer.nextToken().getValue().asText());
			assertEquals(ElementString.class, lexer.getToken().getValue().getClass());
			assertEquals("i", lexer.nextToken().getValue().asText());
			assertEquals(ElementVariable.class, lexer.getToken().getValue().getClass());
			assertEquals("i", lexer.nextToken().getValue().asText());
			assertEquals(ElementVariable.class, lexer.getToken().getValue().getClass());
			assertEquals("*", lexer.nextToken().getValue().asText());
			assertEquals(ElementOperator.class, lexer.getToken().getValue().getClass());
			assertEquals("@sin", lexer.nextToken().getValue().asText());
			assertEquals(ElementFunction.class, lexer.getToken().getValue().getClass());
			assertEquals("\"0.000\"", lexer.nextToken().getValue().asText());
			assertEquals(ElementString.class, lexer.getToken().getValue().getClass());
			assertEquals("@decfmt", lexer.nextToken().getValue().asText());
			assertEquals(ElementFunction.class, lexer.getToken().getValue().getClass());
			assertEquals("ECHOEND", lexer.nextToken().getValue().asText());
			assertEquals(ElementString.class, lexer.getToken().getValue().getClass());
		}
		
		
		@Test
		public void test14() {
			String text =  "A \\{ tag follows {$= \"Joe \\\"Long\\\" Smith\"$}.";
			Lexer lexer = new Lexer(text);
			assertEquals("A { tag follows ", lexer.nextToken().getValue().asText());
			assertEquals("=", lexer.nextToken().getValue().asText());
			assertEquals("\"Joe \\\"Long\\\" Smith\"", lexer.nextToken().getValue().asText());
			
		} 
		
		
		@Test
		public void test15() {
			String text = readPrimjer(1);
			SmartScriptParser parser = new SmartScriptParser(text);
			DocumentNode doc = parser.getDocumentNode();
			assertEquals(1, doc.numberOfChildren());
			assertEquals(TextNode.class, doc.getChild(0).getClass());
		}
		
		
		@Test
		public void test16() {
			String text = readPrimjer(2);
			SmartScriptParser parser = new SmartScriptParser(text);
			DocumentNode doc = parser.getDocumentNode();
			assertEquals(1, doc.numberOfChildren());
			assertEquals(TextNode.class, doc.getChild(0).getClass());
		}
		
		
		@Test
		public void test17() {
			String text = readPrimjer(3);
			SmartScriptParser parser = new SmartScriptParser(text);
			DocumentNode doc = parser.getDocumentNode();
			assertEquals(1, doc.numberOfChildren());
			assertEquals(TextNode.class, doc.getChild(0).getClass());
		}
		
		
		@Test
		public void test18() {
			String text = readPrimjer(4);
			assertThrows(SmartScriptParserException.class, () -> {
				SmartScriptParser parser = new SmartScriptParser(text);
			});
		}
		
		
		@Test
		public void test19() {
			String text = readPrimjer(5);
			assertThrows(SmartScriptParserException.class, () -> {
				SmartScriptParser parser = new SmartScriptParser(text);
			});
		}
		
		
		@Test
		public void test20() {
			String text = readPrimjer(6);
			SmartScriptParser parser = new SmartScriptParser(text);
			DocumentNode doc = parser.getDocumentNode();
			assertEquals(3, doc.numberOfChildren());
			assertEquals(TextNode.class, doc.getChild(0).getClass());
			assertEquals(EchoNode.class, doc.getChild(1).getClass());
			assertEquals(TextNode.class, doc.getChild(2).getClass());
			
		}
		
		
		@Test
		public void test21() {
			String text = readPrimjer(7);
			SmartScriptParser parser = new SmartScriptParser(text);
			DocumentNode doc = parser.getDocumentNode();
			assertEquals(3, doc.numberOfChildren());
			assertEquals(TextNode.class, doc.getChild(0).getClass());
			assertEquals(EchoNode.class, doc.getChild(1).getClass());
			assertEquals(TextNode.class, doc.getChild(2).getClass());
		}
		
		
		@Test
		public void test22() {
			String text = readPrimjer(8);
			assertThrows(SmartScriptParserException.class, () -> {
				SmartScriptParser parser = new SmartScriptParser(text);
			});
		}
		
		
		@Test
		public void test23() {
			String text = readPrimjer(9);
			assertThrows(SmartScriptParserException.class, () -> {
				SmartScriptParser parser = new SmartScriptParser(text);
			});
		}
		
		
		@Test
		public void test24() {
			String text = readExample(1);
			SmartScriptParser parser = new SmartScriptParser(text);
			DocumentNode doc = parser.getDocumentNode();
			assertEquals(4, doc.numberOfChildren());
			assertEquals(TextNode.class, doc.getChild(0).getClass());
			assertEquals(ForLoopNode.class, doc.getChild(1).getClass());
			assertEquals(TextNode.class, doc.getChild(2).getClass());
			assertEquals(EchoNode.class, doc.getChild(3).getClass());
			assertEquals(TextNode.class, doc.getChild(1).getChild(0).getClass());
		}
		
		
		@Test
		public void test25() {
			String text = readExample(2);
			SmartScriptParser parser = new SmartScriptParser(text);
			DocumentNode doc = parser.getDocumentNode();
			assertEquals(2, doc.numberOfChildren());
			assertEquals(TextNode.class, doc.getChild(0).getClass());
			assertEquals(ForLoopNode.class, doc.getChild(1).getClass());
			assertEquals(TextNode.class, doc.getChild(1).getChild(0).getClass());
		}
		
		
		@Test
		public void test26() {
			String text = readExample(3);
			assertThrows(SmartScriptParserException.class,() -> {
				SmartScriptParser parser = new SmartScriptParser(text);
			});	
		}
	
		@Test
		public void test27() {
			String text = readExample(4);
			assertThrows(SmartScriptParserException.class,() -> {
				SmartScriptParser parser = new SmartScriptParser(text);
			});	
		}
		
		@Test
		public void test28() throws IOException {
			byte[] data = this.getClass().getClassLoader().getResourceAsStream("extra/doc1.txt").readAllBytes();
			String text = new String(data, StandardCharsets.UTF_8);
			SmartScriptParser parser = new SmartScriptParser(text);
			DocumentNode doc = parser.getDocumentNode();
			assertEquals(4, doc.numberOfChildren());
			assertEquals(3, doc.getChild(1).numberOfChildren());
			assertEquals(5, doc.getChild(3).numberOfChildren());
			
		}
		private String readPrimjer(int n) {
			  try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt")) {
			    if(is==null) throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");
			    byte[] data = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt").readAllBytes();
			    String text = new String(data, StandardCharsets.UTF_8);
			    return text;
			  } catch(IOException ex) {
			    throw new RuntimeException("Greška pri čitanju datoteke.", ex);
			  }
		}
		
		private String readExample(int n) {
			  try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/example"+n+".txt")) {
			    if(is==null) throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");
			    byte[] data = this.getClass().getClassLoader().getResourceAsStream("extra/example"+n+".txt").readAllBytes();
			    String text = new String(data, StandardCharsets.UTF_8);
			    return text;
			  } catch(IOException ex) {
			    throw new RuntimeException("Greška pri čitanju datoteke.", ex);
			  }
		}
		
	
		
		
		
		


}
