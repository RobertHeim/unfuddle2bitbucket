package de.robert_heim.unfuddle2bitbucket.model.unfuddle;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Project {

	@XmlElementWrapper(name = "tickets")
	@XmlElement(name = "ticket")
	private List<Ticket> tickets;

	@XmlElementWrapper(name = "components")
	@XmlElement(name = "component")
	private List<Component> components;

	@XmlElementWrapper(name = "milestones")
	@XmlElement(name = "milestone")
	private List<Milestone> milestones;

	@XmlElementWrapper(name = "versions")
	@XmlElement(name = "version")
	private List<Version> versions;

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
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

}
