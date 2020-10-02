package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program koji računa faktorijelu broja u rasponu 3-20. Ako se unese broj izvan intervala ili 
 * ako se unese nešto što nije broj program ispisuje odgovarajuću poruku. Program se izvodi dok se god
 * kao unos ne dobije "kraj".
 * @author Andi Škrgat
 * @version 1.0
 * 
 *
 */



public class Factorial {
	/**
	 * Metoda koja se poziva prilikom pokretanja programa. U beskonačnoj petlji učitavamo unos korisnika i 
	 * za svaki valjani unos računamo faktorijelu pomoću funkcije factorial. 
	 * @param args funkcija ne prima argumente iz komandne linije
	 */
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int num;
		
		while(true) {
			System.out.printf("Unesite broj > ");
			String s = sc.next();
			
			try {
				num = Integer.parseInt(s);
				if(num < 3 || num > 20) {
					System.out.println("'" + num + "' nije broj u dozvoljenom rasponu.");
					continue;
				}
				else {
					System.out.println(num + "!" +" = " + factorial(num));
				}
				
				
			
				
			}
			catch (NumberFormatException ex) {
				if(s.equals("kraj")) {
					System.out.println("Doviđenja.");
					break;
				} 
				else {
					System.out.println("'" + s + "'" + " nije cijeli broj.");
				}
			}
			catch(IllegalArgumentException ex) {
				System.out.println("Za ovaj argument se ne može izračunati faktorijelu.");
			}
			
			
			
		}
		sc.close();
		
	}
	
	/**
	 * Funkcija računa faktorijelu dobivenog argumenta. Ako je kao argument predan negativni broj metoda baca 
	 * IllegalArgumentException kao i ako joj damo broj veći od 20 jer faktorijela tog broja ne stane u 64-bitni prikaz cijelog broja.
	 * @param num broj za kojeg računamo faktorijelu
	 * @return vrijednost faktorijela
	 */
	
	public static long factorial(int num) {
		if(num < 0 || num > 20) {
			throw new IllegalArgumentException();
		}
		long value = 1;
		
		for(int i = 2; i <= num; i++) {
			value *= i;
		}
		return value;
	}
	
	
}
