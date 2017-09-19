package ru.home.workplane.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
@NamedQuery(name="Book.findAll", query="SELECT b FROM Book b ORDER BY b.id"),
@NamedQuery(name="Book.find", query="SELECT b FROM Book b WHERE UPPER(b.content) LIKE ?1 ORDER BY b.id")
})
public class Book implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long id;
	private String title;
	private int pages;
	private String author;
	private String publisher;
	private int year;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "link_book_skill", joinColumns = {@JoinColumn(name = "book_id")}, inverseJoinColumns = {@JoinColumn(name = "skill_id")})
	private Set<Skill> skillList;
	@Column(columnDefinition = "LONGVARCHAR")
	private String content;
	private boolean studied;
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	public Book() {
		super();
		skillList = new HashSet<>();
		id = 0L;
	}

	public Book(String title, int pages, String author, String publisher, int year, boolean studied) {
		super();
		this.title = title;
		this.pages = pages;
		this.author = author;
		this.publisher = publisher;
		this.year = year;
		this.studied = studied;
		this.id = 0L;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Set<Skill> getSkillList() {
		return skillList;
	}

	public void setSkillList(Set<Skill> skillList) {
		this.skillList = skillList;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isStudied() {
		return studied;
	}

	public void setStudied(boolean studied) {
		this.studied = studied;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
