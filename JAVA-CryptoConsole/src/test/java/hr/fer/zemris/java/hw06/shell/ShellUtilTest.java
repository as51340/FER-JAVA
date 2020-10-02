package hr.fer.zemris.java.hw06.shell;

import static org.junit.jupiter.api.Assertions.*;


import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellUtil;

public class ShellUtilTest {

	//start testing arguments splitting
	
		@Disabled
		@Test
		public void test9() { 
			String s = "     \r\n   ";
			List<String> list = ShellUtil.split(s);
			assertEquals(0, list.size());
		}
		
		@Disabled
		@Test
		public void test10() { 
			String s = "     \r\n   /home/john/info.txt    ";
			List<String> list = ShellUtil.split(s);
			assertEquals(1, list.size());
			assertEquals("/home/john/info.txt", list.get(0));
		}
		
		@Disabled
		@Test
		public void test11() { 
			String s = "     \r\n   /home/john/info.txt    /home/john/backupFolder";
			List<String> list = ShellUtil.split(s);
			assertEquals(2, list.size());
			assertEquals("/home/john/info.txt", list.get(0));
			assertEquals("/home/john/backupFolder", list.get(1));
		}
		
		@Disabled
		@Test
		public void test12() { 
			String s = "  \"C:/Program Files/Program1/info.txt\"   C:/tmp/informacije.txt  ";
			List<String> list = ShellUtil.split(s);
			assertEquals(2, list.size());
			assertEquals("C:/Program Files/Program1/info.txt", list.get(0));
			assertEquals("C:/tmp/informacije.txt", list.get(1));
		}
		
		@Disabled
		@Test
		public void test13() { 
			String s = "  \"C:/Program Files/Program1/info.txt\"";
			List<String> list = ShellUtil.split(s);
			assertEquals(1, list.size());
			assertEquals("C:/Program Files/Program1/info.txt", list.get(0));
		}
		
		@Disabled
		@Test
		public void test14() { 
			String s = "  \"C:/Program Files/Program1/info.txt\".txt";
			assertThrows(ShellIOException.class, () -> {
				ShellUtil.split(s);	
			});
		}
		
		@Disabled
		@Test
		public void test15() { 
			String s = "  \"C:/Program \\Files/Program1/info.txt\"";
			List<String> list = ShellUtil.split(s);
			assertEquals(1, list.size());
			assertEquals("C:/Program \\Files/Program1/info.txt", list.get(0));
		}
		
		@Disabled
		@Test
		public void test16() { 
			String s = "  \"C:/Program \\\\Files/Program1/info.txt\"";
			List<String> list = ShellUtil.split(s);
			assertEquals(1, list.size());
			assertEquals("C:/Program \\Files/Program1/info.txt", list.get(0));
		}
		
		@Disabled
		@Test
		public void test17() { 
			String s = "  \"C:/Program \\\"Files/Program1/info.txt\"";
			List<String> list = ShellUtil.split(s);
			assertEquals(1, list.size());
			assertEquals("C:/Program \"Files/Program1/info.txt", list.get(0));
		}
		
		@Disabled
		@Test
		public void test18() { 
			String s = "  \"C:/Program Files/Program1/info.txt";
			assertThrows(ShellIOException.class, () -> {
				 ShellUtil.split(s);	
			});
		}
		
		@Disabled
		@Test
		public void test19() { 
			String s = "  \"C:/Program Files/Program1/info.txt\\\"";
			assertThrows(ShellIOException.class, () -> {
				 ShellUtil.split(s);	
			});
		}

	}

