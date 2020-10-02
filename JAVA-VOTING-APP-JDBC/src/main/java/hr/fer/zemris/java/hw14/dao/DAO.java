package hr.fer.zemris.java.hw14.dao;

import java.util.List;
import hr.fer.zemris.java.hw14.model.Data;
import hr.fer.zemris.java.hw14.model.Poll;

/**
 * Defines all methods that will be used when user approaches votingDB database
 * @author Andi Škrgat
 * @version 1.0
 */
public interface DAO {
	
	/**
	 * @return all polls from table <code>Polls</code>
	 */
	List<Poll> getPolls() throws DAOException;
	
	/**
	 * @return poll with given id
	 * @throws DAOException
	 */
	Poll getPoll(long id) throws DAOException;
	
	/**
	 * @return list of all bands for which can be voted 
	 * @throws DAOException
	 */
	List<Data> getAllData(long pollID, boolean order) throws DAOException;
	
	
	/**
	 * Updates number of votes for given band
	 * @param id band's id
	 * @param pollID poll's id
	 * @throws DAOException
	 */
	void updateData(long id, long pollID) throws DAOException;
	
	/**
	 * @param pollID poll's id
	 * @return winner or winners if there are more bands with the same number of votes
	 * @throws DAOException
	 */
	List<Data> getWinners(long pollID) throws DAOException;
	
	
}
