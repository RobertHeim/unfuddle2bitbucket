package de.robert_heim.unfuddle2bitbucket.model.unfuddle;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Account {
	@XmlElementWrapper(name = "people")
	@XmlElement(name = "person")
	private List<Person> people;

	@XmlElementWrapper(name = "projects")
	@XmlElement(name = "project")
	private List<Project> projects;

	public List<Person> getPeople() {
		return people;
	}

	public void setPeople(List<Person> people) {
		this.people = people;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

}
