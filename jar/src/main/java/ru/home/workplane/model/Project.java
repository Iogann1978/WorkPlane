package ru.home.workplane.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import ru.home.workplane.enums.ProjectStates;

@Entity
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private long id;
	private String name;
	@Column(columnDefinition = "LONGVARCHAR")
	private String description;
	private Date dateStart, dateEnd;
	private Double percent;
	private ProjectStates state;
	@ManyToMany
	@JoinTable(name = "LINK_PROJECT_SKILL", joinColumns = {@JoinColumn(name = "PROJECT_ID")}, inverseJoinColumns = {@JoinColumn(name = "SKILL_ID")})
	private Set<Skill> skillList;
	@OneToMany(mappedBy="project")
	private Set<Bug> bugList;
	@OneToMany(mappedBy="project")
	private Set<Log> logList;
	@OneToMany(mappedBy="project")
	private Set<Diary> obstacleList;
	@ManyToOne
	@JoinColumn(name="ORGANIZATION_ID")
	private Organization organization;
	
	public Project(String name, Date dateStart, Date dateEnd, Double percent, ProjectStates state) {
		super();		
		this.name = name;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.percent = percent;
		this.state = state;
		this.id = 0L;
	}

	public Project(Organization organization) {
		super();
		this.name = organization.getName();
		this.dateStart = organization.getDateStart();
		this.dateEnd = organization.getDateEnd();
		this.percent = 0.0d;
		this.state = ProjectStates.ORGANIZATION;
		this.id = 0L;
	}
	
	public Project() {
		super();
		skillList = new HashSet<>();
		bugList = new HashSet<>();
		logList = new HashSet<>();
		obstacleList = new HashSet<>();
		id = 0L;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public ProjectStates getState() {
		return state;
	}

	public void setState(ProjectStates state) {
		this.state = state;
	}

	public Set<Skill> getSkillList() {
		return skillList;
	}

	public void setSkillList(Set<Skill> skillList) {
		this.skillList = skillList;
	}

	public Set<Bug> getBugList() {
		return bugList;
	}

	public void setBugList(Set<Bug> bugList) {
		this.bugList = bugList;
	}
	
	public String getStateName() {
		return state.getName();
	}

	public Set<Log> getLogList() {
		return logList;
	}

	public void setLogList(Set<Log> logList) {
		this.logList = logList;
	}

	public Set<Diary> getObstacleList() {
		return obstacleList;
	}

	public void setObstacleList(Set<Diary> obstacleList) {
		this.obstacleList = obstacleList;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
}
