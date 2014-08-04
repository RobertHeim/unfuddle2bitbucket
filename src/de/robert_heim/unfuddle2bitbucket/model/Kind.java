package de.robert_heim.unfuddle2bitbucket.model;

public enum Kind {
	BUG("bug"), ENHANCEMENT("enhancement"), PROPOSAL("proposal"), TASK("task");
	private String name;

	private Kind(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return name;
	}
}
