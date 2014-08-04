package de.robert_heim.unfuddle2bitbucket.model;

public enum Priority {
	TRIVIAL("trivial"), MINOR("minor"), MAJOR("major"), CRITICAL("critical"), BLOCKER("blocker");
	
	private String name;

	private Priority(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return name;
	}
}
