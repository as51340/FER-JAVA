package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;

/**
 * Class used for keeping informations about all students with methods for fast searching for records in map index as well as filtering database based on some condition
 * @author Andi Škrgat
 * @version 1.0
 */
public class StudentDatabase {
	
	/**
	 * Students's records
	 */
	private List<StudentRecord> records;
	
	/**
	 * Map used for fast getting students's record based on key student's jmbag
	 */
	private Map<String, StudentRecord> index;
	
	/**
	 * Constructor that initializes list and map of records and checks if all grades are between 1 and 5 and if there is only one record for each student  
	 * @param lines students's records read from the file written in list
	 */
	public StudentDatabase(List<String> lines) {
		records = new ArrayList<StudentRecord>();
		index = new LinkedHashMap<String, StudentRecord>();
		Set<String> jmbags = new HashSet<String>();
		for(String line: lines) {
			String jmbag, firstName, lastName, s1, s2;
			Integer finalGrade = null;
			try(Scanner sc = new Scanner(line)) {
				jmbag = sc.next();
				if(jmbags.contains(jmbag) == true) {
					throw new IllegalArgumentException("There is already record for this student");
				}
				jmbags.add(jmbag);
				lastName = sc.next();
				s1 = sc.next();
				s2 = sc.next();
				if(sc.hasNext() == true) {
					finalGrade = Integer.parseInt(sc.next());
					lastName = lastName + " " + s1;
					firstName = s2;
				} else {
					firstName = s1;
					finalGrade = Integer.parseInt(s2);
				}
				if(finalGrade < 1 || finalGrade > 5) {
					throw new IllegalArgumentException("Grade is not between 1 and 5");
				}
				StudentRecord rec = new StudentRecord(jmbag, lastName, firstName, finalGrade);
				records.add(rec);
				index.put(jmbag,  rec);
			} catch(NoSuchElementException ex) {
				System.out.println("Missing attribute in database file");
			}
		}
	}
	
	/**
	 * @param jmbag student's jmbag
	 * @returns student's record for given student's jmbag or null if map doesn't contain mapping for this jmbag
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return index.get(jmbag);
	}
	
	/**
	 * Loops through all records and calls method accepts for every record. If accepts returned true then record is added to temporary list and that list is returned
	 * @param filter filter that, by calling method accepts, filters records from student's database
	 * @returns new list of student's records filled only with records that satisfies condition from filter's method accepts
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> newList = new ArrayList<>();
		for(StudentRecord record: records) {
			if(filter.accepts(record) == true) {
				newList.add(record);
			}
		}
		return newList;
	}

	/**
	 * @return the records
	 */
	public List<StudentRecord> getRecords() {
		return records;
	}

	/**
	 * @return the index
	 */
	public Map<String, StudentRecord> getIndex() {
		return index;
	}
	
	

}
