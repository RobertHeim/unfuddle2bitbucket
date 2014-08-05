package de.robert_heim.unfuddle2bitbucket.model;

import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Issue {
	private List<String> watchers;
	private List<String> voters;
	@NotNull
	private Kind kind;
	@NotNull
	private Status status;
	@NotNull
	private Priority priority;
	@NotNull
	@SerializedName("created_on")
	private Date createdOn;
	@NotNull
	private String title;
	@NotNull
	private Integer id;
	private String component;
	private String milestone;
	private Version version;
	private String content;
	private String assignee;
	private String reporter;
	@NotNull
	@SerializedName("content_updated_on")
	private Date contentUpdatedOn;
	@NotNull
	@SerializedName("updated_on")
	private Date updatedOn;

	public Issue() {
	}

	public List<String> getWatchers() {
		return watchers;
	}

	public void setWatchers(List<String> watchers) {
		this.watchers = watchers;
	}

	public List<String> getVoters() {
		return voters;
	}

	public void setVoters(List<String> voters) {
		this.voters = voters;
	}

	public Kind getKind() {
		return kind;
	}

	public void setKind(Kind kind) {
		this.kind = kind;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getMilestone() {
		return milestone;
	}

	public void setMilestone(String milestone) {
		this.milestone = milestone;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public Date getContentUpdatedOn() {
		return contentUpdatedOn;
	}

	public void setContentUpdatedOn(Date contentUpdatedOn) {
		this.contentUpdatedOn = contentUpdatedOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

}
