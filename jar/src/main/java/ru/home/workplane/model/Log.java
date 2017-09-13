package ru.home.workplane.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Log implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private long id;
	private Date dateStart;
	private Date dateEnd;
	@Column(columnDefinition = "LONGVARCHAR")
	private String description;
	@ManyToOne
	@JoinColumn(name="PROJECT_ID")
	private Project project;

	public Log() {
		super();
		id = 0L;
	}
	
	public Log(Date dateStart, Date dateEnd, String description) {
		super();
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.description = description;
		this.id = 0L;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}
