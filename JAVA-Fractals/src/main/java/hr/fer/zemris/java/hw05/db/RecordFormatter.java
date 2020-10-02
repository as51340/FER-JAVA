package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used for formatting output of query command with one static method format that accepts list of student records
 * @author Andi Škrgat
 * @version 1.0
 */
public class RecordFormatter {

	
	/**
	 * Formats output
	 * @param records list of student's records 
	 * @returns List<String> with every record formatted as required
	 */
	public static List<String> format(List<StudentRecord> records) {
		List<String> list = new ArrayList<String>();
		String size = "Records selected: " + records.size();
		if(records.size() == 0) {
			list.add(size);
			return list;
		}
		int maxSur = -1;
		int maxName = -1;
		int gradeSize = 1, jmbagSize = 10;
		for(StudentRecord record: records) {
			if(record == null) {
				continue;
			}
			if(record.getFirstName() != null && record.getFirstName().length() > maxName) {
				maxName = record.getFirstName().length();
			}
			if(record.getLastName() != null && record.getLastName().length() > maxSur) {
				maxSur = record.getLastName().length();
			}
			if(record.getJmbag() != null && record.getJmbag().length() > jmbagSize) {
				jmbagSize = record.getJmbag().length();
			}
 		}
		
		StringBuilder separator = new StringBuilder();
		separator.append("+" + "=".repeat(jmbagSize +2) + "+" + "=".repeat(maxSur + 2) + "+" + "=".repeat(maxName +2) + "+" + "=".repeat(gradeSize +2) + "+");
		list.add(separator.toString());
		for(StudentRecord record:records) {
			/*if(record == null) {
				continue;
			}*/
			list.add("| " + record.getJmbag() + " ".repeat(jmbagSize - record.getJmbag().length() +1) + "| " + record.getLastName() + " ".repeat(maxSur-record.getLastName().length() +1) + "| " + 
		   record.getFirstName() + " ".repeat(maxName - record.getFirstName().length() +1 ) + "| " + record.getFinalGrade() + " |");
		}
		list.add(separator.toString());
		list.add(size);
		return list;
	}
}
