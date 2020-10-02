package hr.fer.zemris.java.hw01;
import java.util.Scanner;

/**
 * Klasa predstavlja jedno binarno stablo sa metodama za dodavanje čvorova, određivaje veličine stabla i provjeravanja da li se neki element već nalazi u stablu. Za čitanje podataka koristi se 
 * razred Scanner iz paketa java.util.Scanner. 
 * @author Andi Škrgat
 * @version 1.0
 */

public class UniqueNumbers {
	
	/**
	 * Unutarnja klasa za implementiranje čvora stabla.
	 * @author Andi Škrgat
	 * @version 1.0
	 */
	static class TreeNode {
		int value;
		TreeNode left, right;
	}
	
	/**
	 * Metoda iz koje počinje izvršavanje programa. Korisnikovi unosi se primaju tipkovnice i ako su valjani dodavaju se u stablo. Iz te se funkcije pozivaju pomoćne funkcije za dodavanje čvorova, 
	 * provjere da li se neki čvor već nalazi u stablu kao i za ispis stabla na 2 načina: uzlazno i silazno. Čvorovi se dodavaju i provjeravaju dok se god ne upise "kraj".
	 * @param args funkcija ne prima argumente iz komandne linije.
	 */
	
	public static void main(String[] args) {
		TreeNode glava = null;
		int num;
		String s;
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			System.out.printf("Unesite broj > ");
			s = sc.next();
			try {
				num = Integer.parseInt(s);
				if(containsValue(glava,num) == false) {
					glava = addNode(glava, num);
					System.out.println("Dodano.");
				}
				else {
					System.out.println("Broj već postoji. Preskačem.");
				}
				
			}
			catch(NumberFormatException ex) {
				if(s.equals("kraj")) {
					System.out.printf("Ispis od najmanjeg: ");
					ispisUp(glava);
					System.out.println();
					System.out.printf("Ispis od najvećeg: ");
					ispisDown(glava);
					break;
				}
				else {
					System.out.println("'" +s + "' nije cijeli broj.");
				}
			}
		}
		sc.close();
		
	}
	
	/**
	 * Metoda dodaje čvorove na način da se čvor sa manjom vrijednošću sprema lijevo, a čvor sa većom vrijednošću sprema desno u odnosu na neki referentni čvor. 
	 * Ako već postoji čvor sa tom vrijednošću metoda ne mijenja strukturu stabla.
	 * @param head korijen stabla 
	 * @param value vrijednost koja se dodaje u stablo
	 * @return uvijek se vraća glava stabla
	 */
	
	public static TreeNode addNode(TreeNode head, int value) {
		if(head == null) {
			head = new TreeNode();
			head.value = value;
		}
		else if (value < head.value) {
			head.left = addNode(head.left, value);
		}
		else if(value > head.value) {
			head.right = addNode(head.right, value);
		}
		return head;
		
	}
	/**
	 * Metoda vraća broj elemenata u stablu.
	 * @param head korijen stabla
	 * @return veličina stabla
	 */
	
	public static int treeSize(TreeNode head) {
		if(head == null) {
			return 0;
		}
		return treeSize(head.left) + treeSize(head.right) +1;
	}
	
	/**
	 * Metoda provjerava nalazi li se čvor sa vrijednošću value u stablu.
	 * @param head korijen stabla
	 * @param value vrijednost za koju želimo provjeriti nalazi li se u stablu
	 * @return true ako se nalazi, false inače
	 */
	
	public static boolean containsValue(TreeNode head, int value) {
		if(head == null) {
			return false;
		}
		if(value < head.value) {
			return containsValue(head.left, value);
		}
		else if(value > head.value) {
			return containsValue(head.right, value);
		}
		else {
			return true;
		}
		
	}
	/**
	 * Metoda ispisuje uzlazno poredane elemente stabla.
	 * @param head korijen stabla
	 */
	
	public static void ispisUp(TreeNode head) {
		if(head == null)
			return;
		if(head.left != null) {
			ispisUp(head.left);
		}
		System.out.printf(head.value+ " ");
		if(head.right != null) {
			ispisUp(head.right);
		}
	}
	/**
	 * Metoda ispisuje silazno poredane elemente stabla.
	 * @param head korijen stabla
	 */
	
	public static void ispisDown(TreeNode head) {
		if(head == null)
			return;
		if(head.right != null) {
			ispisDown(head.right);
		}
		System.out.printf(head.value+ " ");
		if(head.left != null) {
			ispisDown(head.left);
		}
	}
	
	
	
	
}
