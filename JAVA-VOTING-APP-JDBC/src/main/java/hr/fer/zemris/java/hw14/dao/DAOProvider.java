package hr.fer.zemris.java.hw14.dao;

import hr.fer.zemris.hw14.dao.sql.SQLDAO;

/**
 * Singleton class that knows what service provider has to return for accessing votingDB database 
 * @author Andi Škrgat
 * @version 1.0
 */
public class DAOProvider {

	private static DAO dao = new SQLDAO();
	
	/**
	 * Dohvat primjerka.
	 * 
	 * @return objekt koji enkapsulira pristup sloju za perzistenciju podataka.
	 */
	public static DAO getDao() {
		return dao;
	}
	
}