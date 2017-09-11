package ru.home.workplane.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Diary implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String title;
	private Date date;
	private String content;
	private Set<Skill> skillList;
	
	public Diary() {
		super();
		skillList = new HashSet<>();
	}

	public Diary(String title, Date date, String content) {
		super();
		this.title = title;
		this.date = date;
		this.content = content;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Set<Skill> getSkillList() {
		return skillList;
	}

	public void setSkillList(Set<Skill> skillList) {
		this.skillList = skillList;
	}

	@Override
	public String toString() {
		return title;
	}
}
