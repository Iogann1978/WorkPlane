package ru.home.workplane.model;

import java.io.Serializable;

public class Skill implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String title;
	
	public Skill() {
		super();
	}

	public Skill(String title) {
		super();
		this.title = title;
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
}
