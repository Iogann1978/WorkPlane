package ru.home.workplane.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Book implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String title;
	private int pages;
	private String author;
	private String publisher;
	private int year;
	private Set<Skill> skillList;
	private Content content;
	
	public Book() {
		super();
		skillList = new HashSet<>();
	}

	public Book(String title, int pages, String author, String publisher, int year) {
		super();
		this.title = title;
		this.pages = pages;
		this.author = author;
		this.publisher = publisher;
		this.year = year;
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

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}
}
