package de.robert_heim.unfuddle2bitbucket;

import java.util.ArrayList;
import java.util.List;

import de.robert_heim.unfuddle2bitbucket.model.Meta;

public class ConfigJson {
	private Meta meta;
	private UsernameMap userMap;
	private Severity2KindConverter severity2KindMap;

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

	public Severity2KindConverter getSeverity2KindMap() {
		return severity2KindMap;
	}

	public void setSeverity2KindMap(Severity2KindConverter severity2KindMap) {
		this.severity2KindMap = severity2KindMap;
	}

	private List<String> errors;
	private boolean isValid = false;

	public boolean isValid() {
		errors = new ArrayList<String>();
		isValid = true;
		if (null == severity2KindMap) {
			errors.add("'severity2KindMap' is Required.");
			isValid = false;
		}
		if (null == userMap) {
			errors.add("'userMap' is Required.");
			isValid = false;
		}
		if (null == meta) {
			errors.add("'meta' is Required.");
			isValid = false;
		}
		return isValid;
	}

	public List<String> getErrors() {
		// ensure that errors are collected.
		isValid();
		return errors;
	}

}
