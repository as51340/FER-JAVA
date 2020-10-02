package hr.fer.zemris.java.hw14;

import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * Initializes tables <code>Polls</code> and <code>PollOptions</code> if aren't already created and inserts data for both poll. We use here 2 polls: one so we can
 * finally find out who is the best football player in the world at the moment(I'm sure that FER students will be of great help here) and the other gives user
 * oppurtinity to vote for one of available rock bands. 
 * @author Andi Škrgat
 * @version 1.0
 */
@WebListener
public class Initialization implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		Properties bundle = new Properties();
		try(BufferedReader br = Files.newBufferedReader(Paths.get("src/main/webapp/WEB-INF/dbsettings.properties"))) {
			bundle.load(br);
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass((String)bundle.get("driver"));
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Error pool initialization", e1);
		}
		String user = (String)bundle.get("user");
		String password = (String)bundle.get("password");
		String connectionUrl = "jdbc:derby://" + (String)bundle.get("host") +
			":" + (String)bundle.get("port") + "/" + (String)bundle.get("name") + ";user="
			+ user + ";password=" + password;
		cpds.setJdbcUrl(connectionUrl);
		cpds.setUser(user);
		cpds.setPassword(password);
		cpds.setInitialPoolSize(5);
		cpds.setMinPoolSize(5);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);
		try {
			checkTables(cpds);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}
	
	/**
	 * Checks if tables <code>Polls</code> and <code>PollOptions</code> exist and if not creates them
	 * @param source connection pool
	 * @throws SQLException
	 */
	private void checkTables(DataSource source) throws SQLException {
		Connection con = source.getConnection();
		DatabaseMetaData metadata = con.getMetaData();
		ResultSet rsPolls = metadata.getTables(null, null, "POLLS", null);
		if(rsPolls.next() == false) { //if doesn't exist or is empty
			createPollTable(con);
			createOptionsTable(con);
			populatePollBandTable(con);
			populatePollPlayerTable(con);
		} else {
			String sql2 = "select * from polls where title = 'Vote for best football player'";
			String sql1 = "select * from polls where title = 'Vote for your favourite band'";
			try(PreparedStatement pst = con.prepareStatement(sql1);) {
				ResultSet rsPolls1 = pst.executeQuery();
				if(rsPolls1.next() == false) {
					populatePollBandTable(con);
				}
				try {
					rsPolls1.close();
				} catch(SQLException ex) {
					ex.printStackTrace();
				}
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
			
			try(PreparedStatement pst = con.prepareStatement(sql2);) {
				ResultSet rsPolls1 = pst.executeQuery();
				if(rsPolls1.next() == false) {
					populatePollBandTable(con);
				}
				try {
					rsPolls1.close();
				} catch(SQLException ex) {
					ex.printStackTrace();
				}
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
		}

	}
	
	/**
	 * Creates table for some poll and populates it with data for each poll
	 * @param con active connection
	 */
	private void createOptionsTable(Connection con) {
		String createOptionsTable = "CREATE TABLE PollOptions" + 
				" (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," + 
				" optionTitle VARCHAR(100) NOT NULL," + 
				" optionLink VARCHAR(150) NOT NULL," + 
				" pollID BIGINT," + 
				" votesCount BIGINT," + 
				" FOREIGN KEY (pollID) REFERENCES Polls(id)" + 
				")";
		try(PreparedStatement pst = con.prepareStatement(createOptionsTable);) {
			pst.executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Populates table where informations about bands will be stored 
	 * @param con active connection
	 * @param pollIDBand
	 */
	private void populateOptionsBandData(Connection con, long pollID) {
		String insertBand = "INSERT INTO PollOptions(optionTitle, optionLink, pollID, votesCount) values"
				+ "('The Beatles', 'https://www.youtube.com/watch?v=z9ypq6_5bsg', " + pollID + ", 0),"
				+ "('The Platters', 'https://www.youtube.com/watch?v=H2di83WAOhU', " + pollID + ", 0),"
				+ "('The Beach Boys', 'https://www.youtube.com/watch?v=2s4slliAtQU', " + pollID + ", 0), "
				+ "('The Four Seasons', 'https://www.youtube.com/watch?v=y8yvnqHmFds', " + pollID + ", 0), "
				+ "('The Marcels', 'https://www.youtube.com/watch?v=qoi3TH59ZEs', " + pollID + ", 0),"
				+ "('The Everly Brothers', 'https://www.youtube.com/watch?v=tbU3zdAgiX8', " + pollID + ", 0),"
				+ "('U2', 'https://www.youtube.com/watch?v=_Ye8GLPUVsM&list=PLEsW0I4D4LQYL-Sf4VsTYigiyyaGboDYm', " + pollID + ", 0), "
				+ "('The Mamas And The Papas', 'https://www.youtube.com/watch?v=N-aK6JnyFmk', " + pollID + ", 0),"
				+ "('Aerosmith', 'https://www.youtube.com/watch?v=JkK8g6FMEXE', " + pollID + ", 0), "
				+ "('4 Non Blondes', 'https://www.youtube.com/watch?v=6NXnxTNIWkc', " + pollID + ", 0),"
				+ "('Bon Jovi', 'https://www.youtube.com/watch?v=vx2u5uUu3DE&list=PLmfMgqsFYhSNVs8jiqOYksJcauba2D9kM', " + pollID + ", 0),"
				+ "('Led Zeppelin', 'https://www.youtube.com/watch?v=sfR_HWMzgyc', " + pollID + ", 0),"
				+ "('The Eagles', 'https://www.youtube.com/watch?v=EqPtz5qN7HM', " +pollID + ", 0), "
				+ "('Queen', 'https://www.youtube.com/watch?v=fJ9rUzIMcZQ', " +pollID + ", 0), "
				+ "('Metallica', 'https://www.youtube.com/watch?v=tAGnKpE4NCI', " + pollID + ", 0), "
				+ "('AC/DC', 'https://www.youtube.com/watch?v=v2AC41dglnM', " + pollID + ", 0), "
				+ "('The Cranberries', 'https://www.youtube.com/watch?v=6Ejga4kJUts', " +pollID + ", 0), "
				+ "('Green Day', 'https://www.youtube.com/watch?v=r00ikilDxW4', " + pollID + ", 0), "
				+ "('Linkin Park', 'https://www.youtube.com/watch?v=kXYiU_JCYtU', " + pollID + ", 0), "
				+ "('Nirvana', 'https://www.youtube.com/watch?v=hTWKbfoikeg', " + pollID + ", 0), "
				+ "('Coldplay', 'https://www.youtube.com/watch?v=kayI9QB1-IA', " + pollID + ", 0)"; 
		try(PreparedStatement pst = con.prepareStatement(insertBand);) {
			pst.executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	private void populateOptionsPlayerData(Connection con, long pollID) {
		String insertPlayers = "insert into PollOptions(optionTitle, optionLink, pollID, votesCount) values "
				+ "('Lionel Messi', 'https://www.youtube.com/watch?v=7su4WU6UPu8', " + pollID + ", 0), "
				+ "('Cristiano Ronaldo', 'https://www.youtube.com/watch?v=-mFOj3Vfm1A', "+ pollID + ", 0), "
				+ "('Kylian Mbappe', 'https://www.youtube.com/results?search_query=mbappe', " + pollID + ", 0), "
				+ "('Neymar', 'https://www.youtube.com/watch?v=wp1ZltRNdJs', " + pollID + ", 0), "
				+ "('Antoine Griezmann', 'https://www.youtube.com/watch?v=NN4aKUZa6RU', " + pollID + ", 0), "
				+ "('Luka Modrić', 'https://www.youtube.com/watch?v=yN-8hyZxdyg', " + pollID + ", 0), "
				+ "('Kevin De Bruyne', 'https://www.youtube.com/watch?v=WLLy7CV7W9E', " + pollID + ", 0), "
				+ "('Virgil Van Dijk', 'https://www.youtube.com/watch?v=IkOWWXAK7Cg', " + pollID + ", 0), "
				+ "('Sadio Mane', 'https://www.youtube.com/watch?v=2ngNR326dew', " + pollID + ", 0), "
				+ "('Robert Lewandowski', 'https://www.youtube.com/watch?v=r4tJZOpj3f0', " + pollID + ", 0)";
		try(PreparedStatement pst = con.prepareStatement(insertPlayers);) {
			pst.executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Populates poll table with title and mesage for band
	 * @param con active connection
	 */
	private void populatePollBandTable(Connection con) {
		String insertTwoPolls = "INSERT into Polls(title, message) values(?, ?)";
		long pollIDBand = 0;
		try(PreparedStatement pst = con.prepareStatement(insertTwoPolls, Statement.RETURN_GENERATED_KEYS);) {
			pst.setString(1, "Vote for your favourite band");
			pst.setString(2, "What is your favourite band?");
			pst.executeUpdate();
			ResultSet rset = pst.getGeneratedKeys();
			if(rset != null && rset.next()) {
				pollIDBand = rset.getLong(1); // playera dodat
			}
			populateOptionsBandData(con, pollIDBand);
			try {
				rset.close();
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Populates poll table with title and mesage for football player poll
	 * @param con active connection
	 */
	private void populatePollPlayerTable(Connection con) {
		long pollIDPlayer = -1;
		String insertTwoPolls = "INSERT into Polls(title, message) values(?, ?)";
		try(PreparedStatement pst = con.prepareStatement(insertTwoPolls, Statement.RETURN_GENERATED_KEYS);) {
			pst.setString(1, "Vote for best football player");
			pst.setString(2, "Who is the best?");
			pst.executeUpdate();
			ResultSet rset = pst.getGeneratedKeys();
			if(rset != null && rset.next()) {
				pollIDPlayer = rset.getLong(1); // playera dodat
			}
			populateOptionsPlayerData(con, pollIDPlayer);
			try {
				rset.close();
			} catch(SQLException ex) {
				ex.printStackTrace();
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Creates poll table
	 * @param con active connection
	 */
	private void createPollTable(Connection con) {
		String createPoll = "CREATE TABLE Polls" + 
				" (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," + 
				" title VARCHAR(150) NOT NULL," + 
				" message CLOB(2048) NOT NULL" + 
				")";
		try(PreparedStatement pst = con.prepareStatement(createPoll);) {
			pst.executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		} 
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}