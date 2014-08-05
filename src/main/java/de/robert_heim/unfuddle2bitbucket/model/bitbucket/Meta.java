package de.robert_heim.unfuddle2bitbucket.model.bitbucket;

import com.google.gson.annotations.SerializedName;

public class Meta {
	public static final Kind DEFAULT_KIND = Kind.BUG;

	@SerializedName("default_assignee")
	private String defaultAssignee;

	@SerializedName("default_component")
	private String defaultComponent;

	@NotNull
	@SerializedName("default_kind")
	private String defaultKind = DEFAULT_KIND.name();

	@SerializedName("default_milestone")
	private String defaultMilestone;

	@SerializedName("default_version")
	private String defaultVersion;

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
