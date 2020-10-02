package hr.fer.zemris.hw14.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.model.Data;
import hr.fer.zemris.java.hw14.model.Poll;

/**
 * Implementation of <{@linkplain DAO} with methods for working with database <code>votingDB</code>.
 * Implemented method are used for getting all polls, poll from given id, all data, data from given id and for updating number of votes
 */
public class SQLDAO implements DAO {

	@Override
	public List<Poll> getPolls() throws DAOException {
		List<Poll> list = new ArrayList<Poll>();
		Connection con = SQLConnectionProvider.getConnection();
		String sql = "select id, title, message from polls";
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(sql);
			ResultSet rset = pst.executeQuery();
			try {
				while(rset.next()) {
					Poll poll = new Poll(rset.getLong("id"), rset.getString("title"), rset.getString("message"));
					list.add(poll);
				}
			} finally {
				try {
					rset.close();
				} catch(SQLException ex) {
					ex.printStackTrace();
				}
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				pst.close();	
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
		return list;	
	}

	@Override
	public List<Data> getAllData(long pollID, boolean order) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		List<Data> bands = new ArrayList<Data>();
		String sql = "select * from pollOptions where pollID = " + pollID;
		if(order == true) {
			sql +=" order by votesCount desc";
		}
		try(PreparedStatement pst = con.prepareStatement(sql);) {
			ResultSet rset = pst.executeQuery();
			try {
				while(rset.next()) {
					Data band = new Data(rset.getLong(1), rset.getString(2), rset.getString(3), rset.getLong(4), rset.getInt(5));
					bands.add(band);
				}	
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
			try {
				rset.close();
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return bands;
	}

	@Override
	public void updateData(long id, long pollID) throws DAOException {
		String sql = "update PollOptions set votesCount = votesCount + 1 where id = " + id + " and pollID = " + pollID;
		Connection con = SQLConnectionProvider.getConnection();
		try(PreparedStatement pst = con.prepareStatement(sql);) {
			pst.executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		} 
	}

	@Override
	public List<Data> getWinners(long pollID) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		List<Data> bands = new ArrayList<Data>();
		String sql = "select * from pollOptions where votesCount >= all (select votesCount from pollOptions where votesCount is not null)"
				+ "and pollID=" + pollID  + "order by id";
		try(PreparedStatement pst = con.prepareStatement(sql);) {
			ResultSet rset = pst.executeQuery();
			try {
				while(rset.next()) {
					Data band = new Data(rset.getLong(1), rset.getString(2), rset.getString(3), rset.getLong(4), rset.getInt(5));
					bands.add(band);
				}
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
			try {
				rset.close();
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return bands;
	}

	@Override
	public Poll getPoll(long id) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		String sql = "select * from polls where id = " + id;
		Poll poll = null;
		try(PreparedStatement pst = con.prepareStatement(sql);) {
			ResultSet rset = pst.executeQuery();
			rset.next();
			poll = new Poll(rset.getLong(1), rset.getString(2), rset.getString(3));
			try {
				rset.close();
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		return poll;
	}
}