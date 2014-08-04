package de.robert_heim.unfuddle2bitbucket.model;

import java.util.Date;
import java.util.List;

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
	private Date createdOn;
	@NotNull
	private String title;
	@NotNull
	private Integer id;
	private Component component;
	private Milestone milestone;
	private Version version;
	private String content;
	private Person assignee;
	private Person reporter;
	@NotNull
	private Date contentUpdatedOn;
	@NotNull
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

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}

	public Milestone getMilestone() {
		return milestone;
	}

	public void setMilestone(Milestone milestone) {
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

	public Person getAssignee() {
		return assignee;
	}

	public void setAssignee(Person assignee) {
		this.assignee = assignee;
	}

	public Person getReporter() {
		return reporter;
	}

	public void setReporter(Person reporter) {
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