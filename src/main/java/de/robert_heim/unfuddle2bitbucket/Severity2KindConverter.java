package de.robert_heim.unfuddle2bitbucket;

import java.util.HashMap;
import java.util.Map;

import de.robert_heim.unfuddle2bitbucket.model.Kind;

public class Severity2KindConverter {

	private Map<String, String> severity2KindMap = new HashMap<String, String>();

	public Severity2KindConverter() {
	}

	/**
	 * Looks up the corresponding kind, if it cannot be found, the defaultKind
	 * is returned.
	 * 
	 * @param unfuddleSeverity
	 * @param defaultKind
	 * @return
	 */
	public Kind lookup(String unfuddleSeverity, Kind defaultKind) {
		if (severity2KindMap.containsKey(unfuddleSeverity)) {
			Kind k = Kind.find(severity2KindMap.get(unfuddleSeverity)
					.toLowerCase());
			if (null != k) {
				return k;
			}
		}
		return defaultKind;
	}

}
