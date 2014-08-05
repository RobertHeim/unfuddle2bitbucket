package de.robert_heim.unfuddle2bitbucket.converters;

import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Milestone;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Project;

public class MilestonesConverter
		extends
		Converter<Milestone, de.robert_heim.unfuddle2bitbucket.model.unfuddle.Milestone> {

	public MilestonesConverter() {
		super();
	}

	public void convert(Project project) {
		for (de.robert_heim.unfuddle2bitbucket.model.unfuddle.Milestone unfuddleMilestone : project
				.getMilestones()) {
			Milestone milestone = new Milestone(unfuddleMilestone.getName());
			getUnfuddleEntities().add(unfuddleMilestone);
			getBitbucketEntities().add(milestone);
		}
	}

}
