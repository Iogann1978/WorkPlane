package ru.home.workplane.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Content implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private Set<Paragraph> paragraphList;
	
	public Content() {
		super();
		paragraphList = new HashSet<>();
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public Set<Paragraph> getParagraphList() {
		return paragraphList;
	}

	public void setParagraphList(Set<Paragraph> paragraphList) {
		this.paragraphList = paragraphList;
	}
}
