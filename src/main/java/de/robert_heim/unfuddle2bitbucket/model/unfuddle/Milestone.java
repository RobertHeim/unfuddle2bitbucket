package de.robert_heim.unfuddle2bitbucket.model.unfuddle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.robert_heim.unfuddle2bitbucket.model.StringObjectWithId;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Milestone extends StringObjectWithId {
	@XmlElement(name = "title")
	private String name;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
}
