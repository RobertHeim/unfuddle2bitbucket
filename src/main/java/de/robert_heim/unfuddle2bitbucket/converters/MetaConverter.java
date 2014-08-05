package de.robert_heim.unfuddle2bitbucket.converters;

import de.robert_heim.unfuddle2bitbucket.Provider;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Kind;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Meta;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Priority;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Status;

public class MetaConverter {
	private static final Priority DEFAULT_PRIORITY = Priority.MAJOR;
	private static final Status DEFAULT_STATUS = Status.NEW;

	private Meta meta;
	private Provider provider;

	public MetaConverter(Provider provider) {
		this.provider = provider;
		this.meta = new Meta();
	}

	public Meta getMeta() {
		return meta;
	}

	public void convert() {

		this.meta = provider.getConfigJson().getMeta();
		String defaultKindName = meta.getDefaultKind();
		if (defaultKindName != null) {
			Kind defaultKind = Kind.find(defaultKindName.toLowerCase());
			if (null == defaultKind) {
				defaultKind = Meta.DEFAULT_KIND;
			}
			meta.setDefaultKind(defaultKind.getName());
		}

		String defaultAssignee = meta.getDefaultAssignee();
		if (defaultAssignee != null) {
			if ("auto_first".equals(defaultAssignee)) {
				if (provider.getPeopleConverter().getPeople().size() > 0) {
					defaultAssignee = provider.getPeopleConverter().getPeople()
							.get(0).getName();
				} else {
					if (null == provider.getPeopleConverter().findPersonByName(
							defaultAssignee)) {
						defaultAssignee = null;
					}
				}
			}
			meta.setDefaultAssignee(defaultAssignee);
		}

		String defaultComponent = meta.getDefaultComponent();
		if (defaultComponent != null) {
			if ("auto_first".equals(defaultComponent)) {
				if (provider.getComponentsConverter().getBitbucketEntities()
						.size() > 0) {
					defaultComponent = provider.getComponentsConverter()
							.getBitbucketEntities().get(0).getName();
				} else {
					if (null == provider.getComponentsConverter()
							.findBitbucketEntityByName(defaultComponent)) {
						defaultComponent = null;
					}
				}
			}
			meta.setDefaultComponent(defaultComponent);
		}

		String defaultMilestone = meta.getDefaultMilestone();
		if (defaultMilestone != null) {
			if ("auto_first".equals(defaultMilestone)) {
				if (provider.getMilestonesConverter().getBitbucketEntities()
						.size() > 0) {
					defaultMilestone = provider.getMilestonesConverter()
							.getBitbucketEntities().get(0).getName();
				} else {
					if (null == provider.getMilestonesConverter()
							.findBitbucketEntityByName(defaultMilestone)) {
						defaultMilestone = null;
					}
				}
			}
			meta.setDefaultMilestone(defaultMilestone);
		}

		String defaultVersion = meta.getDefaultVersion();
		if (defaultVersion != null) {
			if ("auto_first".equals(defaultVersion)) {
				if (provider.getVersionsConverter().getBitbucketEntities()
						.size() > 0) {
					defaultVersion = provider.getVersionsConverter()
							.getBitbucketEntities().get(0).getName();
				} else {
					if (null == provider.getVersionsConverter()
							.findBitbucketEntityByName(defaultVersion)) {
						defaultVersion = null;
					}
				}
			}
			meta.setDefaultVersion(defaultVersion);
		}

	}

	public String getDefaultKind() {
		return meta.getDefaultKind();
	}

	public Priority getDefaultPriority() {
		return DEFAULT_PRIORITY;
	}

	public Status getDefaultStatus() {
		return DEFAULT_STATUS;
	}
}
