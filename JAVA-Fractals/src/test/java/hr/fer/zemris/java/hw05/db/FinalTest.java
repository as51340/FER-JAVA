package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class FinalTest {

	
	@Test
	public void test1() throws IOException {
		StudentDatabase db = new StudentDatabase(Files.readAllLines(Paths.get("C:\\Users\\Korisnik\\Desktop\\eclipse-workspaces\\dz\\hw05-0036513403\\src\\test\\resources\\database.txt")));
		String input ="query jmbag = \"0000000003\"";
		List<String> list = StudentDB.answerQuery(db, input);
		assertEquals("+============+========+========+===+", list.get(0));
		assertEquals("| 0000000003 | Bosnić | Andrea | 4 |", list.get(1));
		assertEquals("+============+========+========+===+", list.get(2));
		assertEquals("Records selected: 1", list.get(3));
	}
	
	
	@Test
	public void test2() throws IOException {
		StudentDatabase db = new StudentDatabase(Files.readAllLines(Paths.get("C:\\Users\\Korisnik\\Desktop\\eclipse-workspaces\\dz\\hw05-0036513403\\src\\test\\resources\\database.txt")));
		String input ="query jmbag = \"0000000003\" AND lastName LIKE \"B*\"";
		List<String> list = StudentDB.answerQuery(db, input);
		assertEquals("+============+========+========+===+", list.get(0));
		assertEquals("| 0000000003 | Bosnić | Andrea | 4 |", list.get(1));
		assertEquals("+============+========+========+===+", list.get(2));
		assertEquals("Records selected: 1", list.get(3));
	}
	
	
	@Test
	public void test3() throws IOException {
		StudentDatabase db = new StudentDatabase(Files.readAllLines(Paths.get("C:\\Users\\Korisnik\\Desktop\\eclipse-workspaces\\dz\\hw05-0036513403\\src\\test\\resources\\database.txt")));
		String input ="query jmbag = \"0000000003\" AND lastName LIKE \"L*\"";
		List<String> list = StudentDB.answerQuery(db, input);;
		assertEquals("Records selected: 0", list.get(0));
	}
	
	@Test
	public void test4() throws IOException {
		StudentDatabase db = new StudentDatabase(Files.readAllLines(Paths.get("C:\\Users\\Korisnik\\Desktop\\eclipse-workspaces\\dz\\hw05-0036513403\\src\\test\\resources\\database.txt")));
		String input ="query lastName LIKE \"Be*\"";
		List<String> list = StudentDB.answerQuery(db, input);;
		assertEquals("Records selected: 0", list.get(0));
	}
	
	@Test
	public void test5() throws IOException {
		StudentDatabase db = new StudentDatabase(Files.readAllLines(Paths.get("C:\\Users\\Korisnik\\Desktop\\eclipse-workspaces\\dz\\hw05-0036513403\\src\\test\\resources\\database.txt")));
		String input = "query lastName LIKE \"B*\"";
		List<String> list = StudentDB.answerQuery(db, input);;
		assertEquals("+============+===========+===========+===+", list.get(0));
		assertEquals("| 0000000002 | Bakamović | Petra     | 3 |", list.get(1));
		assertEquals("| 0000000003 | Bosnić    | Andrea    | 4 |", list.get(2));
		assertEquals("| 0000000004 | Božić     | Marin     | 5 |", list.get(3));
		assertEquals("| 0000000005 | Brezović  | Jusufadis | 2 |", list.get(4));
		assertEquals("+============+===========+===========+===+", list.get(5));
		assertEquals("Records selected: 4", list.get(6));
	}
	

}
