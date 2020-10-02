package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Class that always returns same instance of {@linkplain EntityManagerFactory}
 * @author Andi Škrgat
 * @version 1.0
 */
public class JPAEMFProvider {

	public static EntityManagerFactory emf;
	
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}