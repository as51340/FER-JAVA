package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program računa opseg i površinu pravokutnika preko argumenata koje može dobiti preko komandne linije ili preko tipkovnice. 
 * @author Andi Škrgat
 * @version 1.0
 
 * 
 *
 */

public class Rectangle {
	
	/**
	 * Metoda koja se poziva prilikom izvođenja programa. Korisnik je slobodan unijeti argumente preko komandne linije ili preko tipkovnice. 
	 * Unos traje dok se god ne unesu prihvatljive vrijednosti.
	 * @param args širina i visina pravokutnika. Korisnik ako se odluči na ovu vrstu unosa mora unijeti oba argumenta.
	*/
	public static void main(String[] args) {
		double sirina, visina;
		Scanner sc = new Scanner(System.in);
		
		if(args.length == 2) {
			
			try {
				sirina = Double.parseDouble(args[0]);
				visina = Double.parseDouble(args[1]);
				ispis(sirina, visina);
			}
			catch(NumberFormatException ex) {
				System.out.println("Uneseni su krivi argumenti iz komandne linije.");
			}
			
		}
		else if(args.length == 1 || args.length > 2) {
			System.out.println("Unijeli ste pogrešan broj argumenata. Izlazim iz programa.");
		}
		else {
			boolean flag = false;
			sirina = citaj(sc,flag);
			flag = true;
			visina = citaj(sc,flag);
			ispis(sirina, visina);
		}
		sc.close();
	}
	
	/**
	 * Metoda kojom se računaju opseg i površina pravokutnika prema formuli 2*(a + b) i a*b  te ispisuju visina, širina, opseg i površina pravokutnika.
	 * @param sirina širina pravokutnika
	 * @param visina visina pravokutnika
	 */
	
	public static void ispis(double sirina, double visina) {
		System.out.println("Pravokutnik širine " + Double.toString(sirina) + " i visine " + Double.toString(visina) + " ima površinu " + sirina*visina + " te opseg " + 2*(sirina + visina));
	}
	
	/**
	 * Ova metoda se koristi za čitanje oba parametra preko tipkovnice pri čemu se pazi na vrstu unosa odnosno svaki krivi unos se detektira i obrađuje try catch blokom.
	 * Metoda se poziva iz metode main.
	 * @param sc razred Scanner za čitanje preko tipkovnice, koristimo metodu nextLine()
	 * @param flag zastavica koja nam služi za informaciju o tome učitavamo li sad visinu ili širinu pravokutnika
	 * @return funkcija vraća prihvatljivu učitanu vrijednost
	 */
	
	public static double citaj(Scanner sc, boolean flag) {
		double value;
		String s;
		
		while(true) {
			if(flag == false)
				System.out.printf("Unesite širinu > ");
			else
				System.out.printf("Unesite visinu > ");
			s = sc.nextLine();
			s = s.trim();
			try {
				value = Double.parseDouble(s);
				if(value < 0 ) {
					System.out.println("Unijeli ste negativnu vrijednost.");
				} else {
					return value;
				}
			}
			catch(NumberFormatException ex) {
				System.out.println("'" + s +"' se ne može protumačiti kao broj.");
				
			}
			
		}
	}
	
	
	
}
