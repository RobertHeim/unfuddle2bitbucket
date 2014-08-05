package de.robert_heim.unfuddle2bitbucket;

import de.robert_heim.unfuddle2bitbucket.model.Meta;

public class ConfigJson {
	private Meta meta = new Meta();
	private UsernameMap userMap = new UsernameMap();
	private Severity2KindMap severity2KindMap = new Severity2KindMap();

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public UsernameMap getUserMap() {
		return userMap;
	}

	public void setUserMap(UsernameMap userMap) {
		this.userMap = userMap;
	}

	public Severity2KindMap getSeverity2KindMap() {
		return severity2KindMap;
	}

	public void setSeverity2KindMap(Severity2KindMap severity2KindMap) {
		this.severity2KindMap = severity2KindMap;
	}
}
