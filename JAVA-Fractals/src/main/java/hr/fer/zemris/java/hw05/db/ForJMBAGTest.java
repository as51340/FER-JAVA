package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Test for checking if basic filtering works
 * @author Andi Škrgat
 * @version 1.0
 */
public class ForJMBAGTest {

	/**
	 * Main method from where program starts
	 * @param args arguments from console
	 * @throws IOException if something went wrong while reading records from file
	 */
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("C:\\Users\\Korisnik\\Desktop\\eclipse-workspaces\\dz\\hw05-0036513403\\src\\test\\resources\\database.txt"));
		StudentDatabase database = new StudentDatabase(lines);
		checkTrue(database);
		checkFalse(database);
		checkAttributes(database);
	}
	
	public static void checkTrue(StudentDatabase database) {
		IFilter filterTrue = (i) ->  true;
		//database.filter(filterTrue).forEach(System.out::println);
		System.out.println(database.filter(filterTrue).size());
	}
	
	public static void checkFalse(StudentDatabase database) {
		IFilter filterFalse = (i) -> false;
		database.filter(filterFalse).forEach(System.out::println);
	}
	
	public static void checkAttributes(StudentDatabase database) {
		for(StudentRecord record:database.getRecords()) {
			System.out.println(record.getJmbag() + " " + record.getFirstName() + " " + record.getLastName() + " " + record.getFinalGrade());
		}
	}

}