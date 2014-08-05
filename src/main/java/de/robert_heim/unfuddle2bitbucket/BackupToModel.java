package de.robert_heim.unfuddle2bitbucket;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import de.robert_heim.unfuddle2bitbucket.converters.ComponentsConverter;
import de.robert_heim.unfuddle2bitbucket.converters.MilestonesConverter;
import de.robert_heim.unfuddle2bitbucket.converters.PeopleConverter;
import de.robert_heim.unfuddle2bitbucket.converters.VersionsConverter;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Attachment;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Comment;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Component;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.DbJson;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Issue;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Kind;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Log;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Meta;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Milestone;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Person;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Priority;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Status;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Version;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Account;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Event;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.IntegerConverter;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Project;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Severity;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Subscription;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Ticket;

public class BackupToModel {

	private static final Priority DEFAULT_PRIORITY = Priority.MAJOR;

	static private final Status DEFAULT_STATUS = Status.NEW;

	private ConfigJson configJson;

	private int nextUniqueCommentsId;

	private List<Issue> issues;
	private List<Comment> comments;

	private List<Attachment> attachments;

	private Meta meta;

	private List<de.robert_heim.unfuddle2bitbucket.model.unfuddle.Severity> unfuddleSeverities;

	private List<Log> logs;

	private ComponentsConverter componentsConverter;

	private MilestonesConverter milestonesConverter;

	private PeopleConverter peopleConverter;

	private VersionsConverter versionsConverter;

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

		this.attachments = new ArrayList<Attachment>();
		this.comments = new ArrayList<Comment>();

		this.unfuddleSeverities = new ArrayList<Severity>();
		this.issues = new ArrayList<Issue>();

		this.meta = new Meta();

		this.logs = new ArrayList<Log>();

		this.peopleConverter = new PeopleConverter(configJson);
		this.componentsConverter = new ComponentsConverter();
		this.milestonesConverter = new MilestonesConverter();
		this.versionsConverter = new VersionsConverter();

		peopleConverter.convert(account);
		convertMeta();
		Project project = account.getProjects().get(0);
		componentsConverter.convert(project);
		milestonesConverter.convert(project);
		versionsConverter.convert(project);
		convertSeverities(project);
		convertProject(project);

		DbJson dbJson = new DbJson();
		dbJson.setAttachments(attachments);
		dbJson.setComments(comments);
		dbJson.setComponents(componentsConverter.getBitbucketComponents());
		dbJson.setIssues(issues);
		dbJson.setMeta(meta);
		dbJson.setMilestones(milestonesConverter.getBitbucketEntities());
		dbJson.setVersions(versionsConverter.getBitbucketEntities());
		dbJson.setLogs(logs);
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
				if (peopleConverter.getPeople().size() > 0) {
					defaultAssignee = peopleConverter.getPeople().get(0)
							.getName();
				} else {
					if (null == peopleConverter
							.findPersonByName(defaultAssignee)) {
						defaultAssignee = null;
					}
				}
			}
			meta.setDefaultAssignee(defaultAssignee);
		}

		String defaultComponent = meta.getDefaultComponent();
		if (defaultComponent != null) {
			if ("auto_first".equals(defaultComponent)) {
				if (componentsConverter.getBitbucketComponents().size() > 0) {
					defaultComponent = componentsConverter
							.getBitbucketComponents().get(0).getName();
				} else {
					if (null == componentsConverter
							.findComponentByName(defaultComponent)) {
						defaultComponent = null;
					}
				}
			}
			meta.setDefaultComponent(defaultComponent);
		}

		String defaultMilestone = meta.getDefaultMilestone();
		if (defaultMilestone != null) {
			if ("auto_first".equals(defaultMilestone)) {
				if (milestonesConverter.getBitbucketEntities().size() > 0) {
					defaultMilestone = milestonesConverter
							.getBitbucketEntities().get(0).getName();
				} else {
					if (null == milestonesConverter
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
				if (versionsConverter.getBitbucketEntities().size() > 0) {
					defaultVersion = versionsConverter.getBitbucketEntities()
							.get(0).getName();
				} else {
					if (null == versionsConverter
							.findBitbucketEntityByName(defaultVersion)) {
						defaultVersion = null;
					}
				}
			}
			meta.setDefaultVersion(defaultVersion);
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

			{
				Status status = DEFAULT_STATUS;

				if (null != ticket.getStatus()) {
					switch (ticket.getStatus()) {
					case ACCEPTED:
					case REASSIGNED:
					case REOPENED:
						status = Status.OPEN;
						break;
					case CLOSED:
					case RESOLVED:
						status = Status.RESOLVED;
						if (null != ticket.getResolution()) {
							switch (ticket.getResolution()) {
							case INVALID:
								status = Status.INVALID;
								break;
							case DUPLICATE:
								status = Status.DUPLICATE;
								break;
							case WILL_NOT_FIX:
								status = Status.WONTFIX;
								break;
							default:
								status = Status.RESOLVED;
								break;
							}
						}
						break;
					case UNACCEPTED:
						status = Status.INVALID;
						break;
					case NEW:
					default:
						status = Status.NEW;
						break;
					}
				}
				i.setStatus(status);
			}

			// priority
			{
				Priority priority = DEFAULT_PRIORITY;
				if (null != ticket.getPriority()) {
					switch (ticket.getPriority()) {
					case 1:
						priority = Priority.TRIVIAL;
						break;
					case 2:
						priority = Priority.MINOR;
						break;
					case 3:
						priority = Priority.MAJOR;
						break;
					case 4:
						priority = Priority.CRITICAL;
						break;
					case 5:
						priority = Priority.BLOCKER;
						break;
					default:
						priority = DEFAULT_PRIORITY;
						break;
					}
				}
				i.setPriority(priority);
			}
			// assignee
			{
				Person assignee = peopleConverter.findPersonById(ticket
						.getAssigneeId());
				if (null != assignee) {
					i.setAssignee(assignee.getName());
				}
			}

			// reporter
			{
				Person reporter = peopleConverter.findPersonById(ticket
						.getReporterId());
				if (null != reporter) {
					i.setReporter(reporter.getName());
				}
			}

			i.setContentUpdatedOn(ticket.getUpdatedAt().toGregorianCalendar()
					.getTime());

			// component
			de.robert_heim.unfuddle2bitbucket.model.unfuddle.Component unfuddleComponent = componentsConverter
					.findUnfuddleComponentById(ticket.getComponentId());
			if (null != unfuddleComponent) {
				Component component = componentsConverter
						.findComponentByName(unfuddleComponent.getName());
				if (null != component) {
					i.setComponent(component.getName());
				}
			}

			// milestone
			de.robert_heim.unfuddle2bitbucket.model.unfuddle.Milestone unfuddleMilestone = milestonesConverter
					.findUnfuddleEntityById(ticket.getMilestoneId());
			if (null != unfuddleMilestone) {
				Milestone milestone = milestonesConverter
						.findBitbucketEntityByName(unfuddleMilestone.getName());
				if (null != milestone) {
					i.setMilestone(milestone.getName());
				}
			}

			// version
			de.robert_heim.unfuddle2bitbucket.model.unfuddle.Version unfuddleVersion = versionsConverter
					.findUnfuddleEntityById(ticket.getVersionId());
			if (null != unfuddleVersion) {
				Version version = versionsConverter
						.findBitbucketEntityByName(unfuddleVersion.getName());
				if (null != version) {
					i.setVersion(version);
				}
			}

			// watchers
			{
				List<String> watchers = new ArrayList<String>();
				for (Subscription s : ticket.getSubscriptions()) {
					Person p = peopleConverter.findPersonById(s.getPersonId());
					if (null == p) {
						System.out
								.println("WARNING: subscription skipped, because the person with id '"
										+ s.getPersonId()
										+ "' could not be found:");
					} else {
						watchers.add(p.getName());
					}
				}
				i.setWatchers(watchers);
			}

			// events
			{
				for (Event e : ticket.getEvents()) {
					Person p = peopleConverter.findPersonById(e.getPersonId());
					String username = null;
					if (null != p) {
						username = p.getName();
					}
					boolean eventSupported = false;
					String changedTo = "";
					String field = "";
					Log l = new Log();
					switch (e.getEvent()) {
					case "create":
						eventSupported = true;
						changedTo = Status.NEW.getName();
						field = "status";
						break;
					case "accept": // no break
					case "reassign": // no break
					case "reopen":
						eventSupported = true;
						changedTo = Status.OPEN.getName();
						field = "status";
						break;
					case "close": // no break
					case "resolve":
						eventSupported = true;
						changedTo = Status.RESOLVED.getName();
						field = "status";
						break;
					case "update":
						eventSupported = false;
						// TODO parse the description for better support, it
						// contains what happened
						// to update more than one field, e.g. summary as
						// well, we need to create 2 logs.
						// changedTo = "???";
						// field = "???";
						break;
					default:
						eventSupported = false;
						break;
					}
					if (eventSupported) {
						Date createdOn = e.getCreatedAt().toGregorianCalendar()
								.getTime();
						Comment comment = new Comment(e.getDescription(),
								createdOn, getUniqueCommentId(),
								ticket.getId(), createdOn, username);
						comments.add(comment);

						l.setChangedTo(changedTo);
						l.setCommentId(comment.getId());
						l.setCreatedOn(createdOn);
						l.setField(field);
						l.setIssue(i.getId());
						l.setUser(username);
						logs.add(l);
					}
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
			Person p = peopleConverter.findPersonById(unfuddleComment
					.getAuthorId());
			if (null == p) {
				System.out
						.println("Warning: the comment-author with id '"
								+ unfuddleComment.getAuthorId()
								+ "' could not be found in the input file. Using 'null'.");
			} else {
				username = p.getName();
			}
			Comment comment = new Comment(unfuddleComment.getBody(),
					unfuddleComment.getCreatedAt().toGregorianCalendar()
							.getTime(), getUniqueCommentId(), ticket.getId(),
					ticket.getUpdatedAt().toGregorianCalendar().getTime(),
					username);
			this.comments.add(comment);
		}
	}

	// **** HELPERS **** //

	private Integer getUniqueCommentId() {
		Integer id = nextUniqueCommentsId;
		nextUniqueCommentsId++;
		return id;
	}

	private Severity findSeverityById(Integer severityId) {
		for (Severity s : unfuddleSeverities) {
			if (s.getId().equals(severityId)) {
				return s;
			}
		}
		return null;
	}

}
