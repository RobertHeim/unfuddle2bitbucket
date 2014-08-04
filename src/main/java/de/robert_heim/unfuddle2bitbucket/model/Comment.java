package de.robert_heim.unfuddle2bitbucket.model;

import javax.xml.datatype.XMLGregorianCalendar;

public class Comment {
	private String content;
	@NotNull
	private XMLGregorianCalendar createdOn;
	@NotNull
	private Integer id;
	@NotNull
	private Integer issue;
	private XMLGregorianCalendar updatedOn;
	private String user;

	public Comment(String content, XMLGregorianCalendar createdOn, Integer id,
			Integer issue, XMLGregorianCalendar updatedOn, String user) {
		this.content = content;
		this.createdOn = createdOn;
		this.id = id;
		this.issue = issue;
		this.updatedOn = updatedOn;
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public XMLGregorianCalendar getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(XMLGregorianCalendar createdOn) {
		this.createdOn = createdOn;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIssue() {
		return issue;
	}

	public void setIssue(Integer issue) {
		this.issue = issue;
	}

	public XMLGregorianCalendar getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(XMLGregorianCalendar updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
