package de.robert_heim.unfuddle2bitbucket.converters;

import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Component;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Project;

public class ComponentsConverter
		extends
		Converter<Component, de.robert_heim.unfuddle2bitbucket.model.unfuddle.Component> {

	public void convert(Project project) {
		for (de.robert_heim.unfuddle2bitbucket.model.unfuddle.Component unfuddleComponent : project
				.getComponents()) {
			Component component = new Component(unfuddleComponent.getName());
			getUnfuddleEntities().add(unfuddleComponent);
			getBitbucketEntities().add(component);
		}
	}

}
