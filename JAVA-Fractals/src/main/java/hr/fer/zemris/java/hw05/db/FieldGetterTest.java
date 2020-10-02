package hr.fer.zemris.java.hw05.db;

public class FieldGetterTest {

	public static void main(String[] args) {
		StudentRecord record = new StudentRecord("0036513403", "Škrgat", "Andijovski", 5);
		System.out.println("First name: " + FieldValueGetters.FIRST_NAME.get(record));
		System.out.println("Last name: " + FieldValueGetters.LAST_NAME.get(record));
		System.out.println("JMBAG: " + FieldValueGetters.JMBAG.get(record));

	}

}
