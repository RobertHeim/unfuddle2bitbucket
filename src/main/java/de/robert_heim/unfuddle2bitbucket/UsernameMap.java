package de.robert_heim.unfuddle2bitbucket;

import java.util.HashMap;
import java.util.Map;

public class UsernameMap {

	private Map<String, String> userMap;

	public UsernameMap(Map<String, String> map) {
		this.userMap = map;
	}

	public UsernameMap() {
		this(new HashMap<String, String>());
	}

	/**
	 * Looks up the new name, if there is none, the defaultName is returned.
	 * 
	 * @param unfuddleName
	 * @param defaultName
	 * @return
	 */
	public String lookup(String unfuddleName, String defaultName) {
		if (userMap.containsKey(unfuddleName)) {
			return userMap.get(unfuddleName);
		}
		return defaultName;
	}

}
