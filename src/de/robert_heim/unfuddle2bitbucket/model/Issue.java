package de.robert_heim.unfuddle2bitbucket.model;

import java.util.List;

import org.joda.time.DateTime;

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
	private DateTime createdOn;
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
	private DateTime editedOn;
	@NotNull
	private DateTime contentUpdatedOn;
	@NotNull
	private DateTime updatedOn;

	public Issue() {
	}

	public Issue(List<String> watchers, List<String> voters, Kind kind,
			Status status, Priority priority, DateTime createdOn, String title,
			Integer id, Component component, Milestone milestone,
			Version version, String content, Person assignee, Person reporter,
			DateTime editedOn, DateTime contentUpdatedOn, DateTime updatedOn) {
		this.watchers = watchers;
		this.voters = voters;
		this.kind = kind;
		this.status = status;
		this.priority = priority;
		this.createdOn = createdOn;
		this.title = title;
		this.id = id;
		this.component = component;
		this.milestone = milestone;
		this.version = version;
		this.content = content;
		this.assignee = assignee;
		this.reporter = reporter;
		this.editedOn = editedOn;
		this.contentUpdatedOn = contentUpdatedOn;
		this.updatedOn = updatedOn;
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

	public DateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(DateTime createdOn) {
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

	public DateTime getEditedOn() {
		return editedOn;
	}

	public void setEditedOn(DateTime editedOn) {
		this.editedOn = editedOn;
	}

	public DateTime getContentUpdatedOn() {
		return contentUpdatedOn;
	}

	public void setContentUpdatedOn(DateTime contentUpdatedOn) {
		this.contentUpdatedOn = contentUpdatedOn;
	}

	public DateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(DateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

}
