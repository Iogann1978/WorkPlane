package ru.home.workplane.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Skill implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private long id;
	private String title;
	@ManyToMany(mappedBy = "skillList")
	private Set<Diary> diaryList;
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User user;
	@ManyToMany(mappedBy = "skillList")
	private Set<Project> projectList;
	
	public Skill() {
		super();
		diaryList = new HashSet<>();
		projectList = new HashSet<>();
		id = 0L;
	}

	public Skill(String title) {
		super();
		this.title = title;
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
		return 0;
	}
	
	public int getDiaryCount() {
		return 0;
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
}
