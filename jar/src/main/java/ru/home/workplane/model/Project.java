package ru.home.workplane.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
	@GeneratedValue
	private long id;
	private String name;
	@Column(columnDefinition = "LONGVARCHAR")
	private String description;
	private Date dateStart, dateEnd;
	private int percent;
	private ProjectStates state;
	@ManyToMany
	@JoinTable(name = "link_project_skill", joinColumns = {@JoinColumn(name = "project_id")}, inverseJoinColumns = {@JoinColumn(name = "skill_id")})
	private Set<Skill> skillList;
	@OneToMany(mappedBy="project")
	private Set<Bug> bugList;
	@OneToMany(mappedBy="project")
	private Set<Log> logList;
	@OneToMany(mappedBy="project")
	private Set<Diary> obstacleList;
	@ManyToOne
	@JoinColumn(name="organization_id")
	private Organization organization;
	
	public Project() {
		super();
		name = "";
		description= "";
		dateStart = new Date();
		dateEnd = new Date();
		percent = 0;
		state = ProjectStates.ORGANIZATION;
		skillList = new HashSet<>();
		bugList = new HashSet<>();
		logList = new HashSet<>();
		obstacleList = new HashSet<>();
		id = 0L;
	}
	
	public Project(String name, Date dateStart, Date dateEnd, String description, int percent, ProjectStates state, Organization organization) {
		super();		
		this.name = name;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.description = description;
		this.percent = percent;
		this.state = state;
		this.organization = organization;
		this.id = 0L;
	}

	public Project(Organization organization) {
		super();
		this.name = organization.getName();
		this.dateStart = organization.getDateStart();
		this.dateEnd = organization.getDateEnd();
		this.percent = 0;
		this.state = ProjectStates.ORGANIZATION;
		this.id = 0L;
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

	public int getPercent() {
		return percent;
	}

	public Double getPercentDouble() {
		return Double.valueOf(percent/100.0d);
	}

	public void setPercent(int percent) {
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
