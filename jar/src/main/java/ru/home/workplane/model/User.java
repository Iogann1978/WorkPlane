package ru.home.workplane.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private long id;
	private String login;
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	private Set<Skill> skillList;
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	private Set<Diary> diaryList;
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	private Set<Organization> organizationList;
	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	private Set<Book> bookList;
	
	public User() {
		super();
		skillList = new HashSet<>();
		diaryList = new HashSet<>();
		organizationList = new HashSet<>();
		id = 0L;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Set<Book> getBookList() {
		return bookList;
	}

	public void setBookList(Set<Book> bookList) {
		this.bookList = bookList;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
}
