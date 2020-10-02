package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Program from which user interacts with student's database
 * @author Andi Škrgat
 * @version 1.0
 */
public class StudentDB {

	/**
	 * Main method from where program starts
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		StudentDatabase db = new StudentDatabase(Files.readAllLines(Paths.get("C:\\Users\\Korisnik\\Desktop\\eclipse-workspaces\\dz\\hw05-0036513403\\src\\test\\resources\\database.txt")));
		Scanner sc = new Scanner(System.in);
		System.out.print("> ");
		String input;
		while((input = sc.nextLine()).equals("exit") == false) {
			try {
				List<String> output = answerQuery(db, input);
				output.forEach(System.out::println);
			} catch(IllegalArgumentException | ParserException ex) {
				System.out.println(ex.getMessage());
			}
			System.out.println();
			System.out.print("> ");
		}
		System.out.println("Goodbye!");
		sc.close();
	}
	
	/**
	 * Private method called for answering queries
	 * @param db student's database
	 * @param input user's command
	 */
	public static List<String> answerQuery(StudentDatabase db, String input) {
		//System.out.println(input);
		input = input.trim();
		List<StudentRecord> records = new ArrayList<>();
		int queryLength = "query".length();
		if(input.length() < 5) {
			throw new IllegalArgumentException("Wrong command");
		}
		if(input.substring(0, queryLength).equals("query") == false) {
			throw new IllegalArgumentException("Wrong command");
		}
		input = input.substring(queryLength);
		QueryParser parser;
		parser = new QueryParser(input);
		if(parser.isDirectQuery()) {
			StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
			if(r != null) {
				System.out.println("Using index for record retrieval.");
				records.add(r);
			}
		} else {
		 for(StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
			 records.add(r);
		 }
		}
		 List<String> output = RecordFormatter.format(records);
		 return output;
	}
}
