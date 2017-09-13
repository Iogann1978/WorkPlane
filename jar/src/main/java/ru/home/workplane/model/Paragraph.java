package ru.home.workplane.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Paragraph implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlAttribute
	private String number;
	@XmlAttribute
	private String title;
	@XmlAttribute
	private int page;
	@XmlElement
	private Set<Paragraph> childs;
	
	public Paragraph() {
		super();
		childs = new HashSet<>();
	}

	public Paragraph(String number, String title, int page) {
		super();
		this.number = number;
		this.title = title;
		this.page = page;
	}
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public Set<Paragraph> getChilds() {
		return childs;
	}

	public void setChilds(Set<Paragraph> childs) {
		this.childs = childs;
	}
}
