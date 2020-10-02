package hr.fer.zemris.java.hw14.model;

/**
 * Class represents one entry in database <code>votingDB</code> in table <code>PollsOptions</code>
 * @author Andi Škrgat
 * @version 1.0
 */
public class Data {
	
	/**
	 * Item's id
	 */
	private long id;
	
	/**
	 * Item's name
	 */
	private String name;
	
	/**
	 * Url to the highlight of some football player or song
	 */
	private String url;
	
	/**
	 * Number of votes for item
	 */
	private int votes;
	
	/**
	 * Identifies poll
	 */
	private long pollId;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return the votes
	 */
	public int getVotes() {
		return votes;
	}

	/**
	 * @return the pollId
	 */
	public long getPollId() {
		return pollId;
	}

	/**
	 * @param id
	 * @param name
	 * @param url
	 * @param votes
	 * @param pollId
	 */
	public Data(long id, String name, String url, long pollId, int votes) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.votes = votes;
		this.pollId = pollId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
