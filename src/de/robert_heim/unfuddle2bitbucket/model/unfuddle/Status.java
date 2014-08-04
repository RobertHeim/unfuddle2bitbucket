package de.robert_heim.unfuddle2bitbucket.model.unfuddle;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "status")
@XmlEnum
public enum Status {
	@XmlEnumValue("new")
	NEW("new"),

	@XmlEnumValue("unaccepted")
	UNACCEPTED("unaccepted"),

	@XmlEnumValue("reassigned")
	REASSIGNED("reassigned"),

	@XmlEnumValue("reopened")
	REOPENED("reopened"),

	@XmlEnumValue("accepted")
	ACCEPTED("accepted"),

	@XmlEnumValue("resolved")
	RESOLVED("resolved"),

	@XmlEnumValue("closed")
	CLOSED("closed");

	private final String value;

	Status(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static Status fromValue(String v) {
		for (Status c : Status.values()) {
			if (c.value.equalsIgnoreCase(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

}
