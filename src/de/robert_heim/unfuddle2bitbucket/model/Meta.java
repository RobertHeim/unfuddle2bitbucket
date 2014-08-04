package de.robert_heim.unfuddle2bitbucket.model;

public class Meta {
	private String defaultAssignee;
	private String defaultComponent;
	@NotNull
	private String defaultKind;
	private String defaultMilestone;
	private String defaultVersion;

	public Meta(String defaultKind) {
		this.defaultKind = defaultKind;
	}

	public String getDefaultAssignee() {
		return defaultAssignee;
	}

	public void setDefaultAssignee(String defaultAssignee) {
		this.defaultAssignee = defaultAssignee;
	}

	public String getDefaultComponent() {
		return defaultComponent;
	}

	public void setDefaultComponent(String defaultComponent) {
		this.defaultComponent = defaultComponent;
	}

	public String getDefaultKind() {
		return defaultKind;
	}

	public void setDefaultKind(String defaultKind) {
		this.defaultKind = defaultKind;
	}

	public String getDefaultMilestone() {
		return defaultMilestone;
	}

	public void setDefaultMilestone(String defaultMilestone) {
		this.defaultMilestone = defaultMilestone;
	}

	public String getDefaultVersion() {
		return defaultVersion;
	}

	public void setDefaultVersion(String defaultVersion) {
		this.defaultVersion = defaultVersion;
	}

}
