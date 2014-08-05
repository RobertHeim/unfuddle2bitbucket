package de.robert_heim.unfuddle2bitbucket.converters;

import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Version;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Project;

public class VersionsConverter
		extends
		Converter<Version, de.robert_heim.unfuddle2bitbucket.model.unfuddle.Version> {

	public VersionsConverter() {

	}

	@Override
	public void convert(Project project) {
		for (de.robert_heim.unfuddle2bitbucket.model.unfuddle.Version unfuddleVersion : project
				.getVersions()) {
			Version version = new Version(unfuddleVersion.getName());
			getUnfuddleEntities().add(unfuddleVersion);
			getBitbucketEntities().add(version);
		}
	}
}
