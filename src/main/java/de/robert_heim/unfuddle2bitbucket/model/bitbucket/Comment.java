package de.robert_heim.unfuddle2bitbucket.model.bitbucket;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class Comment {
	private String content;
	@NotNull
	@SerializedName("created_on")
	private Date createdOn;
	@NotNull
	private Integer id;
	@NotNull
	private Integer issue;
	@SerializedName("udpated_on")
	private Date updatedOn;
	private String user;

	public Comment(String content, Date createdOn, Integer id, Integer issue,
			Date updatedOn, String user) {
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

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
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

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
