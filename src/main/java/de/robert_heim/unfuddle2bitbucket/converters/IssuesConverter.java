package de.robert_heim.unfuddle2bitbucket.converters;

import java.util.ArrayList;
import java.util.List;

import de.robert_heim.unfuddle2bitbucket.Provider;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Component;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Issue;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Kind;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Meta;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Milestone;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Person;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Priority;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Status;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Version;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Severity;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Subscription;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Ticket;

public class IssuesConverter {
	private List<Issue> issues;
	private Provider provider;

	public IssuesConverter(Provider provider) {
		this.provider = provider;
		this.issues = new ArrayList<Issue>();
	}

	public List<Issue> getIssues() {
		return issues;
	}

	public void add(Issue i) {
		issues.add(i);
	}

	public void convert(List<Ticket> tickets) {
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
				Kind kind = Kind.find(provider.getMetaConverter()
						.getDefaultKind());
				if (null == kind) {
					kind = Meta.DEFAULT_KIND;
				}

				if (null != ticket.getSeverityId()) {
					Severity s = provider.getSeveritiesConverter()
							.findSeverityById(ticket.getSeverityId());
					if (null == s) {
						System.out
								.println("Warning: the severity with id '"
										+ ticket.getSeverityId()
										+ "' could not be found in the input file. Using default kind ('"
										+ kind + "').");
					} else {
						kind = provider.getConfigJson().lookupKind(s.getName(),
								null);
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
				Status status = provider.getMetaConverter().getDefaultStatus();

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
				Priority priority = provider.getMetaConverter()
						.getDefaultPriority();
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
						priority = provider.getMetaConverter()
								.getDefaultPriority();
						break;
					}
				}
				i.setPriority(priority);
			}
			// assignee
			{
				Person assignee = provider.getPeopleConverter().findPersonById(
						ticket.getAssigneeId());
				if (null != assignee) {
					i.setAssignee(assignee.getName());
				}
			}

			// reporter
			{
				Person reporter = provider.getPeopleConverter().findPersonById(
						ticket.getReporterId());
				if (null != reporter) {
					i.setReporter(reporter.getName());
				}
			}

			i.setContentUpdatedOn(ticket.getUpdatedAt().toGregorianCalendar()
					.getTime());

			// component
			de.robert_heim.unfuddle2bitbucket.model.unfuddle.Component unfuddleComponent = provider
					.getComponentsConverter().findUnfuddleEntityById(
							ticket.getComponentId());
			if (null != unfuddleComponent) {
				Component component = provider.getComponentsConverter()
						.findBitbucketEntityByName(unfuddleComponent.getName());
				if (null != component) {
					i.setComponent(component.getName());
				}
			}

			// milestone
			de.robert_heim.unfuddle2bitbucket.model.unfuddle.Milestone unfuddleMilestone = provider
					.getMilestonesConverter().findUnfuddleEntityById(
							ticket.getMilestoneId());
			if (null != unfuddleMilestone) {
				Milestone milestone = provider.getMilestonesConverter()
						.findBitbucketEntityByName(unfuddleMilestone.getName());
				if (null != milestone) {
					i.setMilestone(milestone.getName());
				}
			}

			// version
			de.robert_heim.unfuddle2bitbucket.model.unfuddle.Version unfuddleVersion = provider
					.getVersionsConverter().findUnfuddleEntityById(
							ticket.getVersionId());
			if (null != unfuddleVersion) {
				Version version = provider.getVersionsConverter()
						.findBitbucketEntityByName(unfuddleVersion.getName());
				if (null != version) {
					i.setVersion(version);
				}
			}

			// watchers
			{
				List<String> watchers = new ArrayList<String>();
				for (Subscription s : ticket.getSubscriptions()) {
					Person p = provider.getPeopleConverter().findPersonById(
							s.getPersonId());
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

			provider.getLogsConverter().convertEventsOfTicket(i, ticket);
			provider.getIssuesConverter().add(i);
			provider.getCommentsConverter().convert(ticket);
		}
	}

}
