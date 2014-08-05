package de.robert_heim.unfuddle2bitbucket.converters;

import java.util.ArrayList;
import java.util.List;

import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Project;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Severity;

public class SeveritiesConverter {
	private List<Severity> unfuddleSeverities;

	public SeveritiesConverter() {
		this.unfuddleSeverities = new ArrayList<Severity>();
	}

	public List<Severity> getUnfuddleSeverities() {
		return unfuddleSeverities;
	}

	public void convert(Project project) {
		for (de.robert_heim.unfuddle2bitbucket.model.unfuddle.Severity unfuddleSeverity : project
				.getSeverities()) {
			unfuddleSeverities.add(unfuddleSeverity);
		}
	}

	public Severity findSeverityById(Integer severityId) {
		for (Severity s : unfuddleSeverities) {
			if (s.getId().equals(severityId)) {
				return s;
			}
		}
		return null;
	}

}
