package de.robert_heim.unfuddle2bitbucket.model.unfuddle;

public enum Resolution {

	FIXED("fixed"), WORKS_FOR_ME("works_for_me"), POSTPONED("postponed"), DUPLICATE(
			"duplicate"), WILL_NOT_FIX("will_not_fix"), INVALID("invalid"), ;
	private String name;

	private Resolution(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
}
