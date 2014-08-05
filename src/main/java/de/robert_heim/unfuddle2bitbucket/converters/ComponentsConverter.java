package de.robert_heim.unfuddle2bitbucket.converters;

import java.util.ArrayList;
import java.util.List;

import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Component;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Project;

public class ComponentsConverter {
	private List<Component> components;
	private List<de.robert_heim.unfuddle2bitbucket.model.unfuddle.Component> unfuddleComponents;

	public ComponentsConverter() {
		this.components = new ArrayList<Component>();
		this.unfuddleComponents = new ArrayList<de.robert_heim.unfuddle2bitbucket.model.unfuddle.Component>();
	}

	public void convert(Project project) {
		for (de.robert_heim.unfuddle2bitbucket.model.unfuddle.Component unfuddleComponent : project
				.getComponents()) {
			Component component = new Component(unfuddleComponent.getName());
			unfuddleComponents.add(unfuddleComponent);
			components.add(component);
		}
	}

	public de.robert_heim.unfuddle2bitbucket.model.unfuddle.Component findUnfuddleComponentById(
			Integer id) {
		if (null == unfuddleComponents) {
			return null;
		}
		for (de.robert_heim.unfuddle2bitbucket.model.unfuddle.Component component : unfuddleComponents) {
			if (component.getId().equals(id)) {
				return component;
			}
		}
		return null;
	}

	public Component findComponentByName(String name) {
		if (null == components) {
			return null;
		}
		for (Component component : components) {
			if (component.getName().equals(name)) {
				return component;
			}
		}
		return null;
	}

	public List<Component> getBitbucketComponents() {
		return components;
	}

	public List<de.robert_heim.unfuddle2bitbucket.model.unfuddle.Component> getUnfuddleComponents() {
		return unfuddleComponents;
	}

}
