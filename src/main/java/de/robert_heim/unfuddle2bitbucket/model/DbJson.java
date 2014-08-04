package de.robert_heim.unfuddle2bitbucket.model;

import java.util.List;

public class DbJson {
	@NotNull
	private List<Issue> issues;
	@NotNull
	private List<Comment> comments;
	@NotNull
	private List<Attachment> attachments;
	@NotNull
	private List<Component> components;

	@NotNull
	private List<Milestone> milestones;

	@NotNull
	private List<Version> versions;

	@NotNull
	private Meta meta;

	public List<Issue> getIssues() {
		return issues;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}

	public List<Milestone> getMilestones() {
		return milestones;
	}

	public void setMilestones(List<Milestone> milestones) {
		this.milestones = milestones;
	}

	public List<Version> getVersions() {
		return versions;
	}

	public void setVersions(List<Version> versions) {
		this.versions = versions;
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

}
