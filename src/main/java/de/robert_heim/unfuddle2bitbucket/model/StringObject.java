package de.robert_heim.unfuddle2bitbucket.model;

abstract public class StringObject implements HasName {

	private String name;

	public StringObject() {
		this("");
	}

	public StringObject(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
