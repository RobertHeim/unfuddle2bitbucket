package de.robert_heim.unfuddle2bitbucket;

import java.util.HashMap;
import java.util.Map;

import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Kind;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Meta;

public class ConfigJson {
	private Meta meta = new Meta();

	private Map<String, String> severity2KindMap = new HashMap<String, String>();
	private Map<String, String> userMap = new HashMap<String, String>();

	/**
	 * Looks up the new name, if there is none, the defaultName is returned.
	 * 
	 * @param unfuddleName
	 * @param defaultName
	 * @return
	 */
	public String lookupUsername(String unfuddleName, String defaultName) {
		if (userMap.containsKey(unfuddleName)) {
			return userMap.get(unfuddleName);
		}
		return defaultName;
	}

	/**
	 * Looks up the corresponding kind, if it cannot be found, the defaultKind
	 * is returned.
	 * 
	 * @param unfuddleSeverity
	 * @param defaultKind
	 * @return
	 */
	public Kind lookupKind(String unfuddleSeverity, Kind defaultKind) {
		if (severity2KindMap.containsKey(unfuddleSeverity)) {

			Kind k = Kind.find(severity2KindMap.get(unfuddleSeverity)
					.toLowerCase());
			if (null != k) {
				return k;
			}
		}
		return defaultKind;
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}
}
