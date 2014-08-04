package de.robert_heim.unfuddle2bitbucket.model;

public enum Status {
	NEW("new"), OPEN("open"), RESOLVED("resolved"), ON_HOLD("on hold"), INVALID("invalid"), DUPLICATE("duplicate"), WONTFIX("wontfix");
	private String name;

	private Status(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return name;
	}
	
}
