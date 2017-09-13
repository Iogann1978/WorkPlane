package ru.home.workplane.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Content implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement
	private Set<Paragraph> paragraphList;
	
	public Content() {
		super();
		paragraphList = new HashSet<>();
	}
	
	public Set<Paragraph> getParagraphList() {
		return paragraphList;
	}

	public void setParagraphList(Set<Paragraph> paragraphList) {
		this.paragraphList = paragraphList;
	}
}
