package de.robert_heim.unfuddle2bitbucket;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.IntegerConverter;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Project;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Severity;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Ticket;

public class BackupToModel {

	private ConfigJson configJson;

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

	private List<de.robert_heim.unfuddle2bitbucket.model.unfuddle.Severity> unfuddleSeverities;

	public BackupToModel(ConfigJson configJson) {
		this.configJson = configJson;
		this.nextUniqueCommentsId = 1;
	}

	public DbJson convert(String file) throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(Account.class);
		Unmarshaller um = context.createUnmarshaller();
		um.setAdapter(new IntegerConverter());
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
		this.unfuddleSeverities = new ArrayList<Severity>();
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
		convertSeverities(project);
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

	private void convertSeverities(Project project) {
		for (de.robert_heim.unfuddle2bitbucket.model.unfuddle.Severity unfuddleSeverity : project
				.getSeverities()) {
			unfuddleSeverities.add(unfuddleSeverity);
		}
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
		this.meta = configJson.getMeta();
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

		String defaultComponent = meta.getDefaultComponent();
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

		String defaultMilestone = meta.getDefaultMilestone();
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

		String defaultVersion = meta.getDefaultVersion();
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
			String newUsername = configJson.lookupUsername(
					unfuddlePerosn.getUsername(), unfuddlePerosn.getUsername());
			person.setName(newUsername);
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

			// kind
			{
				Kind kind = Kind.find(meta.getDefaultKind());
				if (null == kind) {
					kind = Meta.DEFAULT_KIND;
				}

				if (null != ticket.getSeverityId()) {
					Severity s = findSeverityById(ticket.getSeverityId());
					if (null == s) {
						System.out
								.println("Warning: the severity with id '"
										+ ticket.getSeverityId()
										+ "' could not be found in the input file. Using default kind ('"
										+ kind + "').");
					} else {
						kind = configJson.lookupKind(s.getName(), null);
						if (null == kind) {
							kind = Meta.DEFAULT_KIND;
							System.out
									.println("Warning: there is no or an invalid mapping specified for the severity '"
											+ s.getName()
											+ "'. Using default ('"
											+ kind
											+ "'). Please specify the mapping in the config-file to prevent this warning.");
						}
					}
				}
				i.setKind(kind);
			}

			// status
			// TODO i.setStatus(ticket.getStatus());

			// priority
			// TODO i.setPriority(ticket.getSeverityId());

			// assignee
			{
				Person assignee = findPersonById(ticket.getAssigneeId());
				if (null != assignee) {
					i.setAssignee(assignee.getName());
				}
			}

			// reporter
			{
				Person reporter = findPersonById(ticket.getReporterId());
				if (null != reporter) {
					i.setReporter(reporter.getName());
				}
			}

			i.setContentUpdatedOn(ticket.getUpdatedAt().toGregorianCalendar()
					.getTime());

			// component
			de.robert_heim.unfuddle2bitbucket.model.unfuddle.Component unfuddleComponent = findUnfuddleComponentById(ticket
					.getComponentId());
			if (null != unfuddleComponent) {
				Component component = findComponentByName(unfuddleComponent
						.getName());
				if (null != component) {
					i.setComponent(component.getName());
				}
			}

			// milestone
			de.robert_heim.unfuddle2bitbucket.model.unfuddle.Milestone unfuddleMilestone = findUnfuddleMilestoneById(ticket
					.getMilestoneId());
			if (null != unfuddleMilestone) {
				Milestone milestone = findMilestoneByName(unfuddleMilestone
						.getName());
				if (null != milestone) {
					i.setMilestone(milestone.getName());
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
		for (de.robert_heim.unfuddle2bitbucket.model.unfuddle.Comment unfuddleComment : ticket
				.getComments()) {

			String username = null;
			Person p = findPersonById(unfuddleComment.getAuthorId());
			if (null == p) {
				System.out
						.println("Warning: the comment-author with id '"
								+ unfuddleComment.getAuthorId()
								+ "' could not be found in the input file. Using 'null'.");
			} else {
				username = p.getName();
			}
			Comment comment = new Comment(unfuddleComment.getBody(),
					unfuddleComment.getCreatedAt(), getUniqueCommentId(),
					ticket.getId(), ticket.getUpdatedAt(), username);
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

	private Severity findSeverityById(Integer severityId) {
		for (Severity s : unfuddleSeverities) {
			if (s.getId().equals(severityId)) {
				return s;
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
