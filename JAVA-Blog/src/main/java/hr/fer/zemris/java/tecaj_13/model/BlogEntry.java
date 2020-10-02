package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * This represents one thread on corona blog. Users can leave comments and every user can see list of all threads started by some user
 */
@NamedQueries({
	@NamedQuery(
		name="BlogEntry.upit1",
		query="select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when",
		hints={@QueryHint(name="org.hibernate.cacheable", value="true")}),
	@NamedQuery(
		name ="getEntryWithID",
		query="select a from BlogEntry a where a.id=:id",
		hints= {@QueryHint(name="org.hibernate.cacheable", value="true")})
})
@Entity
@Table(name="blog_entries")
public class BlogEntry {
	
	/**
	 * Entry's id
	 */
	private Long id;
	
	/**
	 * List of comments
	 */
	private List<BlogComment> comments = new ArrayList<>();
	
	/**
	 * Date when it has been created
	 */
	private Date createdAt;
	
	/**
	 * Date when it has been modified
	 */
	private Date lastModifiedAt;
	
	/**
	 * Entry's title
	 */
	private String title;
	
	/**
	 * Entry's text
	 */
	private String text;
	
	/**
	 * User that created blog entry
	 */
	private BlogUser creator;
	
	/**
	 * @return the creator
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogUser getCreator() {
		return creator;
	}

	/**
	 * @param creator the creator to set
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
	}

	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@OneToMany(mappedBy="blogEntry",fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}
	
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	@Column(length=200,nullable=false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(length=4096,nullable=false)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}