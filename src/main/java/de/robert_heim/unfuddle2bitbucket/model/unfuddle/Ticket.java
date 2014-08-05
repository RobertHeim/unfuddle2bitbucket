package de.robert_heim.unfuddle2bitbucket.model.unfuddle;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Ticket {
	@XmlElement(name = "severity-id")
	@XmlJavaTypeAdapter(IntegerConverter.class)
	private Integer severityId;

	private Resolution resolution;
	private Status status;
	private Integer priority;

	@XmlElement(name = "created-at")
	private XMLGregorianCalendar createdAt;

	private String summary;
	private Integer id;

	@XmlElement(name = "component-id")
	private Integer componentId;

	@XmlElement(name = "milestone-id")
	private Integer milestoneId;

	@XmlElement(name = "version-id")
	private Integer versionId;

	private String description;

	@XmlElement(name = "assignee-id")
	private Integer assigneeId;

	@XmlElement(name = "reporter-id")
	private Integer reporterId;

	@XmlElement(name = "updated-at")
	private XMLGregorianCalendar updatedAt;

	@XmlElementWrapper(name = "comments")
	@XmlElement(name = "comment")
	private List<Comment> comments;

	@XmlElementWrapper(name = "subscriptions")
	@XmlElement(name = "subscription")
	private List<Subscription> subscriptions;

	public Ticket() {
	}

	public Integer getSeverityId() {
		return severityId;
	}

	public void setSeverityId(Integer severityId) {
		this.severityId = severityId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Resolution getResolution() {
		return resolution;
	}

	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public XMLGregorianCalendar getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(XMLGregorianCalendar createdAt) {
		this.createdAt = createdAt;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getComponentId() {
		return componentId;
	}

	public void setComponentId(Integer componentId) {
		this.componentId = componentId;
	}

	public Integer getMilestoneId() {
		return milestoneId;
	}

	public void setMilestoneId(Integer milestoneId) {
		this.milestoneId = milestoneId;
	}

	public Integer getVersionId() {
		return versionId;
	}

	public void setVersionId(Integer versionId) {
		this.versionId = versionId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(Integer assigneeId) {
		this.assigneeId = assigneeId;
	}

	public Integer getReporterId() {
		return reporterId;
	}

	public void setReporterId(Integer reporterId) {
		this.reporterId = reporterId;
	}

	public XMLGregorianCalendar getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(XMLGregorianCalendar updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(List<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

}
