package de.robert_heim.unfuddle2bitbucket.model.unfuddle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Subscription {
	@XmlElement(name = "created-at")
	private XMLGregorianCalendar createdAt;
	private Integer id;
	@XmlElement(name = "parent-id")
	private Integer parentId;
	@XmlElement(name = "person-id")
	private Integer personId;
	@XmlElement(name = "updated-at")
	private XMLGregorianCalendar updatedAt;

	public Subscription() {
	}

	public XMLGregorianCalendar getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(XMLGregorianCalendar createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public XMLGregorianCalendar getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(XMLGregorianCalendar updatedAt) {
		this.updatedAt = updatedAt;
	}

}
