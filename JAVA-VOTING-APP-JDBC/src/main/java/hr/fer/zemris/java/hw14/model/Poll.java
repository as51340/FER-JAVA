package hr.fer.zemris.java.hw14.model;

/**
 * Class that describes one entry in database <code>votingDB</code> for table <code>Polls</code>
 * @author Andi Škrgat
 * @version 1.0
 */
public class Poll {
	
	/**
	 * @return the pollId
	 */
	public long getPollId() {
		return pollId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param pollId
	 * @param title
	 * @param message
	 */
	public Poll(long pollId, String title, String message) {
		super();
		this.pollId = pollId;
		this.title = title;
		this.message = message;
	}

	/**
	 * Poll id
	 */
	private long pollId;
	
	/**
	 * Poll's title
	 */
	private String title;
	
	/**
	 * Poll's message
	 */
	private String message;
	
	

}
