package ru.home.workplane.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
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
	
	public String marshal() {
		String content = "";
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JAXBContext context = JAXBContext.newInstance(Content.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(this, baos);
			content = new String(baos.toByteArray());
		} catch (JAXBException e) {
			content = "<xml><error>" + e.getMessage() + "</error></xml>";
		}
		return content;
	}
	
	public void unmarshal(String content) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(content.getBytes());
			JAXBContext context = JAXBContext.newInstance(Content.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Content xmlcontent = (Content) unmarshaller.unmarshal(bais);
			setParagraphList(xmlcontent.getParagraphList());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}
