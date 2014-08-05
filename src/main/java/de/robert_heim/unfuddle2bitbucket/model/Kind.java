package de.robert_heim.unfuddle2bitbucket.model;

public enum Kind {
	BUG("bug"), ENHANCEMENT("enhancement"), PROPOSAL("proposal"), TASK("task");
	private String name;

	private Kind(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return getName();
	}

	/**
	 * find the key or return null
	 * 
	 * @param lowerCase
	 * @return
	 */
	public static Kind find(String lowerCase) {
		for (Kind k : Kind.values()) {
			if (k.name.equalsIgnoreCase(lowerCase)) {
				return k;
			}
		}
		return null;
	}
}
