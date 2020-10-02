package hr.fer.zemris.hw14.dao.sql;

import java.sql.Connection;

/**
 * Stores connection with database votingDB in ThreadLocal object. ThreadLocal is actually
 * a map where keys are thread identificators that perform operation on map
 * @author Andi Škrgat
 * @version 1.0
 */
public class SQLConnectionProvider {

	private static ThreadLocal<Connection> connections = new ThreadLocal<>();
	
	/**
	 * Sets connection as current thread (or deletes entry form map if argument is <code>null</code>).
	 * @param con connection with database
	 */
	public static void setConnection(Connection con) {
		if(con==null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}
	
	/**
	 * Gets connection which is available to current thread for using
	 * @return connection with database
	 */
	public static Connection getConnection() {
		return connections.get();
	}
	
}