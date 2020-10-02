package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class LexerTest {

	
	@Test
	public void test1() {
		QueryLexer lexer = new QueryLexer(" jmbag	=\"0000000003\"");
		assertEquals("jmbag", lexer.getToken().getContent());
		assertEquals(TokenType.ATTRIBUTE, lexer.getCurrentToken().getType());
		assertEquals("=", lexer.getToken().getContent());
		assertEquals(TokenType.OPERATOR, lexer.getCurrentToken().getType());
		assertEquals("0000000003", lexer.getToken().getContent());
		assertEquals(TokenType.LITERAL, lexer.getCurrentToken().getType());
		assertEquals(TokenType.EOF, lexer.getToken().getType());
		assertThrows(ParserException.class, () -> {
			lexer.getToken();
		});
	}
	
	
	@Test
	public void test2() {
		QueryLexer lexer = new QueryLexer(" 	lastName	 = \"Blažić\"		");
		assertEquals("lastName", lexer.getToken().getContent());
		assertEquals(TokenType.ATTRIBUTE, lexer.getCurrentToken().getType());
		assertEquals("=", lexer.getToken().getContent());
		assertEquals(TokenType.OPERATOR, lexer.getCurrentToken().getType());
		assertEquals("Blažić", lexer.getToken().getContent());
		assertEquals(TokenType.LITERAL, lexer.getCurrentToken().getType());
		assertEquals(TokenType.EOF, lexer.getToken().getType());
		assertThrows(ParserException.class, () -> {
			lexer.getToken();
		});
	}
	
	
	@Test
	public void test3() {
		QueryLexer lexer = new QueryLexer(" firstName>\"A\" and lastName	 LIKE	 \"B*ć\"");
		assertEquals("firstName", lexer.getToken().getContent());
		assertEquals(TokenType.ATTRIBUTE, lexer.getCurrentToken().getType());
		assertEquals(">", lexer.getToken().getContent());
		assertEquals(TokenType.OPERATOR, lexer.getCurrentToken().getType());
		assertEquals("A", lexer.getToken().getContent());
		assertEquals(TokenType.LITERAL, lexer.getCurrentToken().getType());
		assertEquals("and", lexer.getToken().getContent());
		assertEquals(TokenType.LOGICAL_OPERATOR, lexer.getCurrentToken().getType());
		assertEquals("lastName", lexer.getToken().getContent());
		assertEquals(TokenType.ATTRIBUTE, lexer.getCurrentToken().getType());
		assertEquals("LIKE", lexer.getToken().getContent());
		assertEquals(TokenType.OPERATOR, lexer.getCurrentToken().getType());
		assertEquals("B*ć", lexer.getToken().getContent());
		assertEquals(TokenType.LITERAL, lexer.getCurrentToken().getType());
		assertEquals(TokenType.EOF, lexer.getToken().getType());
		assertThrows(ParserException.class, () -> {
			lexer.getToken();
		});
	}
	
	
	@Test
	public void test4() {
		QueryLexer lexer = new QueryLexer(" 	firstName>\"A\" 	and firstName<=\"C\" and lastName LIKE \"B*ć\" and jmbag>\"0000000002\"\r\n" + 
				"");
		assertEquals("firstName", lexer.getToken().getContent());
		assertEquals(TokenType.ATTRIBUTE, lexer.getCurrentToken().getType());
		assertEquals(">", lexer.getToken().getContent());
		assertEquals(TokenType.OPERATOR, lexer.getCurrentToken().getType());
		assertEquals("A", lexer.getToken().getContent());
		assertEquals(TokenType.LITERAL, lexer.getCurrentToken().getType());
		assertEquals("and", lexer.getToken().getContent());
		assertEquals(TokenType.LOGICAL_OPERATOR, lexer.getCurrentToken().getType());
		assertEquals("firstName", lexer.getToken().getContent());
		assertEquals(TokenType.ATTRIBUTE, lexer.getCurrentToken().getType());
		assertEquals("<=", lexer.getToken().getContent());
		assertEquals(TokenType.OPERATOR, lexer.getCurrentToken().getType());
		assertEquals("C", lexer.getToken().getContent());
		assertEquals(TokenType.LITERAL, lexer.getCurrentToken().getType());
		assertEquals("and", lexer.getToken().getContent());
		assertEquals(TokenType.LOGICAL_OPERATOR, lexer.getCurrentToken().getType());
		assertEquals("lastName", lexer.getToken().getContent());
		assertEquals(TokenType.ATTRIBUTE, lexer.getCurrentToken().getType());
		assertEquals("LIKE", lexer.getToken().getContent());
		assertEquals(TokenType.OPERATOR, lexer.getCurrentToken().getType());
		assertEquals("B*ć", lexer.getToken().getContent());
		assertEquals(TokenType.LITERAL, lexer.getCurrentToken().getType());
		assertEquals("and", lexer.getToken().getContent());
		assertEquals(TokenType.LOGICAL_OPERATOR, lexer.getCurrentToken().getType());
		assertEquals("jmbag", lexer.getToken().getContent());
		assertEquals(TokenType.ATTRIBUTE, lexer.getCurrentToken().getType());
		assertEquals(">", lexer.getToken().getContent());
		assertEquals(TokenType.OPERATOR, lexer.getCurrentToken().getType());
		assertEquals("0000000002", lexer.getToken().getContent());
		assertEquals(TokenType.LITERAL, lexer.getCurrentToken().getType());
		assertEquals(TokenType.EOF, lexer.getToken().getType());
		assertThrows(ParserException.class, () -> {
			lexer.getToken();
		});
	}
	

}
