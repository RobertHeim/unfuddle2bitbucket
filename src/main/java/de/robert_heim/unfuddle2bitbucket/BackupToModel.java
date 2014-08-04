package de.robert_heim.unfuddle2bitbucket;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import de.robert_heim.unfuddle2bitbucket.model.Attachment;
import de.robert_heim.unfuddle2bitbucket.model.Comment;
import de.robert_heim.unfuddle2bitbucket.model.Component;
import de.robert_heim.unfuddle2bitbucket.model.DbJson;
import de.robert_heim.unfuddle2bitbucket.model.Issue;
import de.robert_heim.unfuddle2bitbucket.model.Kind;
import de.robert_heim.unfuddle2bitbucket.model.Meta;
import de.robert_heim.unfuddle2bitbucket.model.Milestone;
import de.robert_heim.unfuddle2bitbucket.model.Person;
import de.robert_heim.unfuddle2bitbucket.model.Version;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Account;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Project;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Ticket;

public class BackupToModel {

	private Properties properties;

	private int nextUniqueCommentsId;

	private List<Issue> issues;
	private List<Comment> comments;
	private List<Person> people;

	private List<Attachment> attachments;

	private List<Component> components;
	private List<de.robert_heim.unfuddle2bitbucket.model.unfuddle.Component> unfuddleComponents;

	private Meta meta;

	private List<Milestone> milestones;
	private List<de.robert_heim.unfuddle2bitbucket.model.unfuddle.Milestone> unfuddleMilestones;

	private List<Version> versions;
	private List<de.robert_heim.unfuddle2bitbucket.model.unfuddle.Version> unfuddleVersions;

	public BackupToModel(Properties properties) {
		this.properties = properties;
		this.nextUniqueCommentsId = 1;
	}

	public DbJson convert(String file) throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(Account.class);
		Unmarshaller um = context.createUnmarshaller();
		Account account = (Account) um.unmarshal(new FileReader(file));

		if (account.getProjects().size() <= 0) {
			throw new RuntimeException("Could not find any projces");
		}
		this.people = new ArrayList<Person>();

		this.attachments = new ArrayList<Attachment>();
		this.comments = new ArrayList<Comment>();
		this.components = new ArrayList<Component>();
		this.unfuddleComponents = new ArrayList<de.robert_heim.unfuddle2bitbucket.model.unfuddle.Component>();
		this.unfuddleMilestones = new ArrayList<de.robert_heim.unfuddle2bitbucket.model.unfuddle.Milestone>();
		this.unfuddleVersions = new ArrayList<de.robert_heim.unfuddle2bitbucket.model.unfuddle.Version>();
		this.issues = new ArrayList<Issue>();
		this.meta = new Meta();
		this.milestones = new ArrayList<Milestone>();
		this.versions = new ArrayList<Version>();

		convertPeople(account);
		convertMeta();
		Project project = account.getProjects().get(0);
		convertComponents(project);
		convertMilestones(project);
		convertVersions(project);
		convertProject(project);

		DbJson dbJson = new DbJson();
		dbJson.setAttachments(attachments);
		dbJson.setComments(comments);
		dbJson.setComponents(components);
		dbJson.setIssues(issues);
		dbJson.setMeta(meta);
		dbJson.setMilestones(milestones);
		dbJson.setVersions(versions);
		return dbJson;

	}

	// **** NOT IMPLEMENTED **** //
	private void convertAttachments() {
		// TODO
	}

	private void convertComponents(Project project) {
		for (de.robert_heim.unfuddle2bitbucket.model.unfuddle.Component unfuddleComponent : project
				.getComponents()) {
			Component component = new Component(unfuddleComponent.getName());
			unfuddleComponents.add(unfuddleComponent);
			components.add(component);
		}
	}

	private void convertMeta() {
		String defaultKindName = properties.getProperty("default.kind", null);
		if (defaultKindName != null) {
			Kind defaultKind = Kind.find(defaultKindName.toLowerCase());
			if (null == defaultKind) {
				defaultKind = Kind.BUG;
			}
			meta.setDefaultKind(defaultKind.getName());
		}

		String defaultAssignee = properties.getProperty("default.assignee",
				null);
		if (defaultAssignee != null) {
			if ("auto_first".equals(defaultAssignee)) {
				if (people.size() > 0) {
					defaultAssignee = people.get(0).getName();
				} else {
					if (null == findPersonByName(defaultAssignee)) {
						defaultAssignee = null;
					}
				}
			}
			meta.setDefaultAssignee(defaultAssignee);
		}

		String defaultComponent = properties.getProperty("default.component",
				null);
		if (defaultComponent != null) {
			if ("auto_first".equals(defaultComponent)) {
				if (components.size() > 0) {
					defaultComponent = components.get(0).getName();
				} else {
					if (null == findComponentByName(defaultComponent)) {
						defaultComponent = null;
					}
				}
			}
			meta.setDefaultComponent(defaultComponent);
		}

		String defaultMilestone = properties.getProperty("default.milestone",
				null);
		if (defaultMilestone != null) {
			if ("auto_first".equals(defaultMilestone)) {
				if (milestones.size() > 0) {
					defaultMilestone = milestones.get(0).getName();
				} else {
					if (null == findMilestoneByName(defaultMilestone)) {
						defaultMilestone = null;
					}
				}
			}
			meta.setDefaultMilestone(defaultMilestone);
		}

		String defaultVersion = properties.getProperty("default.version", null);
		if (defaultVersion != null) {
			if ("auto_first".equals(defaultVersion)) {
				if (versions.size() > 0) {
					defaultVersion = versions.get(0).getName();
				} else {
					if (null == findVersionByName(defaultVersion)) {
						defaultVersion = null;
					}
				}
			}
			meta.setDefaultVersion(defaultVersion);
		}

	}

	private void convertMilestones(Project project) {
		for (de.robert_heim.unfuddle2bitbucket.model.unfuddle.Milestone unfuddleMilestone : project
				.getMilestones()) {
			Milestone milestone = new Milestone(unfuddleMilestone.getName());
			unfuddleMilestones.add(unfuddleMilestone);
			milestones.add(milestone);
		}
	}

	private void convertVersions(Project project) {
		for (de.robert_heim.unfuddle2bitbucket.model.unfuddle.Version unfuddleVersion : project
				.getVersions()) {
			Version version = new Version(unfuddleVersion.getName());
			unfuddleVersions.add(unfuddleVersion);
			versions.add(version);
		}
	}

	// ******* //

	private void convertPeople(Account account) {
		for (de.robert_heim.unfuddle2bitbucket.model.unfuddle.Person unfuddlePerosn : account
				.getPeople()) {
			Person person = new Person();
			person.setId(unfuddlePerosn.getId());
			person.setName(unfuddlePerosn.getUsername());
			this.people.add(person);
		}
	}

	private void convertProject(Project project) {
		convertTickets(project.getTickets());
	}

	private void convertTickets(List<Ticket> tickets) {
		if (null == tickets) {
			return;
		}
		for (Ticket ticket : tickets) {
			Issue i = new Issue();
			i.setId(ticket.getId());
			i.setCreatedOn(ticket.getCreatedAt().toGregorianCalendar()
					.getTime());
			i.setUpdatedOn(ticket.getUpdatedAt().toGregorianCalendar()
					.getTime());
			i.setContent(ticket.getDescription());
			i.setTitle(ticket.getSummary());
			// TODO i.setStatus(ticket.getStatus());
			// TODO i.setPriority(ticket.getSeverityId());
			i.setAssignee(findPersonById(ticket.getAssigneeId()));
			i.setReporter(findPersonById(ticket.getReporterId()));
			i.setContentUpdatedOn(ticket.getUpdatedAt().toGregorianCalendar()
					.getTime());

			// component
			de.robert_heim.unfuddle2bitbucket.model.unfuddle.Component unfuddleComponent = findUnfuddleComponentById(ticket
					.getComponentId());
			if (null != unfuddleComponent) {
				Component component = findComponentByName(unfuddleComponent
						.getName());
				if (null != component) {
					i.setComponent(component);
				}
			}

			// milestone
			de.robert_heim.unfuddle2bitbucket.model.unfuddle.Milestone unfuddleMilestone = findUnfuddleMilestoneById(ticket
					.getMilestoneId());
			if (null != unfuddleMilestone) {
				Milestone milestone = findMilestoneByName(unfuddleMilestone
						.getName());
				if (null != milestone) {
					i.setMilestone(milestone);
				}
			}

			// version
			de.robert_heim.unfuddle2bitbucket.model.unfuddle.Version unfuddleVersion = findUnfuddleVersionById(ticket
					.getVersionId());
			if (null != unfuddleVersion) {
				Version version = findVersionByName(unfuddleVersion.getName());
				if (null != version) {
					i.setVersion(version);
				}
			}

			this.issues.add(i);
			convertComments(ticket);
		}
	}

	private void convertComments(Ticket ticket) {
		for (de.robert_heim.unfuddle2bitbucket.model.unfuddle.Comment unufuddleComment : ticket
				.getComments()) {
			Comment comment = new Comment(unufuddleComment.getBody(),
					unufuddleComment.getCreatedAt(), getUniqueCommentId(),
					ticket.getId(), ticket.getUpdatedAt(), findPersonById(
							unufuddleComment.getAuthorId()).getName());
			this.comments.add(comment);
		}
	}

	// **** HELPERS **** //

	private Integer getUniqueCommentId() {
		Integer id = nextUniqueCommentsId;
		nextUniqueCommentsId++;
		return id;
	}

	public Person findPersonById(Integer id) {
		if (null == people) {
			return null;
		}
		for (Person person : people) {
			if (person.getId().equals(id)) {
				return person;
			}
		}
		return null;
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

	public de.robert_heim.unfuddle2bitbucket.model.unfuddle.Milestone findUnfuddleMilestoneById(
			Integer id) {
		if (null == unfuddleMilestones) {
			return null;
		}
		for (de.robert_heim.unfuddle2bitbucket.model.unfuddle.Milestone milestone : unfuddleMilestones) {
			if (milestone.getId().equals(id)) {
				return milestone;
			}
		}
		return null;
	}

	public de.robert_heim.unfuddle2bitbucket.model.unfuddle.Version findUnfuddleVersionById(
			Integer id) {
		if (null == unfuddleVersions) {
			return null;
		}
		for (de.robert_heim.unfuddle2bitbucket.model.unfuddle.Version version : unfuddleVersions) {
			if (version.getId().equals(id)) {
				return version;
			}
		}
		return null;
	}

	public Person findPersonByName(String name) {
		if (null == people) {
			return null;
		}
		for (Person person : people) {
			if (person.getName().equals(name)) {
				return person;
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

	public Milestone findMilestoneByName(String name) {
		if (null == milestones) {
			return null;
		}
		for (Milestone milestone : milestones) {
			if (milestone.getName().equals(name)) {
				return milestone;
			}
		}
		return null;
	}

	public Version findVersionByName(String name) {
		if (null == versions) {
			return null;
		}
		for (Version version : versions) {
			if (version.getName().equals(name)) {
				return version;
			}
		}
		return null;
	}

}
