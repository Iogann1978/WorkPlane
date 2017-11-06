package ru.home.workplane.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Skill implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long id;
	private String title;
	@ManyToMany(mappedBy = "skillList")
	private Set<Diary> diaryList;
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	@ManyToMany(mappedBy = "skillList")
	private Set<Project> projectList;
	@ManyToMany(mappedBy = "skillList")
	private Set<Book> bookList;
	
	public Skill() {
		super();
		title = "";
		diaryList = new HashSet<>();
		projectList = new HashSet<>();
		bookList = new HashSet<>();
		id = 0L;
	}

	public Skill(String title, User user) {
		super();
		this.title = title;
		this.user = user;
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

	@Override
	public String toString() {
		return title;
	}
	
	public int getProjectCount() {
		int count = 0;
		if(projectList != null && projectList.size() > 0) {
			count = projectList.size();
		}
		return count;
	}
	
	public int getDiaryCount() {
		int count = 0;
		if(diaryList != null && diaryList.size() > 0) {
			count = diaryList.size();
		}
		return count;
	}

	public Set<Diary> getDiaryList() {
		return diaryList;
	}

	public void setDiaryList(Set<Diary> diaryList) {
		this.diaryList = diaryList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Project> getProjectList() {
		return projectList;
	}

	public void setProjectList(Set<Project> projectList) {
		this.projectList = projectList;
	}

	public Set<Book> getBookList() {
		return bookList;
	}

	public void setBookList(Set<Book> bookList) {
		this.bookList = bookList;
	}
}
