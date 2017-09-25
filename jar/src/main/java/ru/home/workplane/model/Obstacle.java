package ru.home.workplane.model;

import java.io.Serializable;

public class Obstacle implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String title;
	private Diary solution;
	
	public Obstacle() {
		super();
	}

	public Obstacle(Diary solution) {
		super();
		this.title = solution.getTitle();
		this.solution = solution;
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

	public Diary getSolution() {
		return solution;
	}

	public void setSolution(Diary solution) {
		this.solution = solution;
	}

	@Override
	public String toString() {
		return title;
	}
}
