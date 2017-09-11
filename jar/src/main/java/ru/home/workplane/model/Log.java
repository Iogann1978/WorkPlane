package ru.home.workplane.model;

import java.io.Serializable;
import java.util.Date;

public class Log implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private Date dateStart;
	private Date dateEnd;
	private String description;

	public Log() {
		super();
	}
	
	public Log(Date dateStart, Date dateEnd, String description) {
		super();
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.description = description;
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
}
