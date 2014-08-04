package de.robert_heim.unfuddle2bitbucket.model.unfuddle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Comment {
	@XmlElement(name = "author-id")
	private Integer authorId;
	private String body;
	private Integer id;

	@XmlElement(name = "created-at")
	private XMLGregorianCalendar createdAt;

	@XmlElement(name = "updated-at")
	private XMLGregorianCalendar updatedAt;

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public XMLGregorianCalendar getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(XMLGregorianCalendar createdAt) {
		this.createdAt = createdAt;
	}

	public XMLGregorianCalendar getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(XMLGregorianCalendar updatedAt) {
		this.updatedAt = updatedAt;
	}

}
