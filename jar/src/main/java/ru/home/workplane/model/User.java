package ru.home.workplane.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private Set<Project> projectList;
	private Set<Skill> skillList;
	private Set<Diary> diaryList;
	private Set<Organization> organizationList;
	
	public User() {
		super();
		projectList = new HashSet<>();
		skillList = new HashSet<>();
		diaryList = new HashSet<>();
		organizationList = new HashSet<>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Set<Project> getProjectList() {
		return projectList;
	}

	public void setProjectList(Set<Project> projectList) {
		this.projectList = projectList;
	}

	public Set<Skill> getSkillList() {
		return skillList;
	}

	public void setSkillList(Set<Skill> skillList) {
		this.skillList = skillList;
	}

	public Set<Diary> getDiaryList() {
		return diaryList;
	}

	public void setDiaryList(Set<Diary> diaryList) {
		this.diaryList = diaryList;
	}

	public Set<Organization> getOrganizationList() {
		return organizationList;
	}

	public void setOrganizationList(Set<Organization> organizationList) {
		this.organizationList = organizationList;
	}
}
