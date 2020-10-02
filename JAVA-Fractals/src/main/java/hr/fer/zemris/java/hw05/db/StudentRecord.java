package hr.fer.zemris.java.hw05.db;

/**
 * Represents one record for each student with properties: lastName, firstName, jmbag and finalGrade
 * @author Andi Škrgat
 * @version 1.0
 */
public class StudentRecord {
	
	/**
	 * Student's jmbag
	 */
	private String jmbag;
	
	/**
	 * Student's last name
	 */
	private String lastName;
	
	/**
	 * Student's first name
	 */
	private String firstName;
	
	/**
	 * Student's final grade
	 */
	private int finalGrade;
	
	
	/**
	 * @return the jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}
	
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * @return the finalGrade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}
	
	
	

	/**
	 * @param jmbag student's jmbag
	 * @param lastName student's last name
	 * @param firstName student's first name
	 * @param finalGrade student's final grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

	
	
}
