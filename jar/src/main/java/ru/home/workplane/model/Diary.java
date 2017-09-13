package ru.home.workplane.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
@NamedQuery(name="Diary.findAll", query="SELECT d FROM Diary d ORDER BY d.date DESC"),
@NamedQuery(name="Diary.find", query="SELECT d FROM Diary d WHERE UPPER(d.content) LIKE ?1 ORDER BY d.date DESC")
})
public class Diary implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private long id;
	private String title;
	private Date date;
	@Column(columnDefinition = "LONGVARCHAR")
	private String content;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "LINK_DIARY_SKILL", joinColumns = {@JoinColumn(name = "DIARY_ID")}, inverseJoinColumns = {@JoinColumn(name = "SKILL_ID")})
	private Set<Skill> skillList;
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User user;	
	@ManyToOne
	@JoinColumn(name="PROJECT_ID")
	private Project project;
	
	public Diary() {
		super();
		skillList = new HashSet<>();
		id = 0L;
	}

	public Diary(String title, Date date, String content) {
		super();
		this.title = title;
		this.date = date;
		this.content = content;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}
