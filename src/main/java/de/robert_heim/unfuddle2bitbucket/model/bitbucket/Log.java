package de.robert_heim.unfuddle2bitbucket.model.bitbucket;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Log {
	@SerializedName("changed_from")
	private String changedFrom;
	@SerializedName("changed_to")
	private String changedTo;
	@SerializedName("comment")
	private Integer commentId;
	@SerializedName("created_on")
	private Date createdOn;
	private String field;
	private Integer issue;
	private String user;

	public Log() {
	}

	public String getChangedFrom() {
		return changedFrom;
	}

	public void setChangedFrom(String changedFrom) {
		this.changedFrom = changedFrom;
	}

	public String getChangedTo() {
		return changedTo;
	}

	public void setChangedTo(String changedTo) {
		this.changedTo = changedTo;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Integer getIssue() {
		return issue;
	}

	public void setIssue(Integer issue) {
		this.issue = issue;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
