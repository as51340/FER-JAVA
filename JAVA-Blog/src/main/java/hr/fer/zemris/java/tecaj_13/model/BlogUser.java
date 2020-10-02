package hr.fer.zemris.java.tecaj_13.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.QueryHint;
import javax.persistence.Table;

/**
 * Model of blog user. Logged users can create entries and comment existing entries.
 * @author Andi Škrgat
 * @version 1.0
 */
@NamedQueries({
	@NamedQuery(name="getUserWithNick", 
			query="select a from BlogUser a where a.nick=:nick", 
			hints={@QueryHint(name="org.hibernate.cacheable", value="true")}),
	@NamedQuery(name="getAllUsers",
			query="select a from BlogUser a",
			hints={@QueryHint(name="org.hibernate.cacheable", value="true")}),
	@NamedQuery(name ="getEntriesUser",
			query = "select a from BlogEntry a where a.creator = :user",
			hints={@QueryHint(name="org.hibernate.cacheable", value="true")}),
})
@Entity
@Table(name = "blog_users")
public class BlogUser {
	
	/**
	 * User's id
	 */
	private long id;
	
	/**
	 * User's first name
	 */
	private String firstName;

	/**
	 * Last name
	 */
	private String lastName;
	
	/**
	 * Nickname, needs to be unique
	 */
	private String nick;
	
	/**
	 * User's email
	 */
	private String email;
	
	/**
	 * List of blog entries
	 */
	private List<BlogEntry> blogEntries;
	
	/**
	 * Default constructor
	 */
	public BlogUser() {
		
	}
	
	/**
	 * @param firstName
	 * @param lastName
	 * @param nick
	 * @param email
	 * @param blogEntries
	 * @param passwordHash
	 */
	public BlogUser(String firstName, String lastName, String nick, String email, List<BlogEntry> blogEntries,
			String passwordHash) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.nick = nick;
		this.email = email;
		this.blogEntries = blogEntries;
		this.passwordHash = passwordHash;
	}
	
	

	/**
	 * @param firstName
	 * @param lastName
	 * @param nick
	 * @param email
	 * @param passwordHash
	 */
	public BlogUser(String firstName, String lastName, String nick, String email, String passwordHash) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.nick = nick;
		this.email = email;
		this.passwordHash = passwordHash;
	}


	
	/**
	 * @return the blogEntries
	 */
	@OneToMany(mappedBy = "creator")
	public List<BlogEntry> getBlogEntries() {
		return blogEntries;
	}

	/**
	 * @param blogEntries the blogEntries to set
	 */
	public void setBlogEntries(List<BlogEntry> blogEntries) {
		this.blogEntries = blogEntries;
	}

	/**
	 * Hex encoded hash value of user's password used for protecting password(for example admin could see password in plain text and that's something we don't want)
	 */
	private String passwordHash;

	/**
	 * @return the id
	 */
	@Id @GeneratedValue
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the firstName
	 */
	@Column(length=20, nullable = false)
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	@Column(length = 30, nullable = false)
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the nick
	 */
	@Column(length = 20, nullable = false, unique = true)
	public String getNick() {
		return nick;
	}

	/**
	 * @param nick the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the passwordHash
	 */
	@Column(length = 256, nullable = false)
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * @param passwordHash the passwordHash to set
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	

}
