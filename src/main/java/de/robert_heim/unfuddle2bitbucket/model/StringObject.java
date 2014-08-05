package de.robert_heim.unfuddle2bitbucket.model;

import de.robert_heim.unfuddle2bitbucket.model.bitbucket.NotNull;

abstract public class StringObject implements HasName {
	@NotNull
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
