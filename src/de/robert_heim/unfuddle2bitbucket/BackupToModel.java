package de.robert_heim.unfuddle2bitbucket;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
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
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Project;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Ticket;

public class BackupToModel {

	private BufferedWriter output;

	private int nextUniqueCommentsId;

	private List<Issue> issues;
	private List<Comment> comments;
	private List<Person> people;

	private List<Attachment> attachments;

	private List<Component> components;

	private Meta meta;

	private List<Milestone> milestones;

	private List<Version> versions;

	public BackupToModel(Writer writer) {
		this.output = new BufferedWriter(writer);
		this.nextUniqueCommentsId = 1;
	}

	public DbJson convert(String file) throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(Account.class);
		Unmarshaller um = context.createUnmarshaller();
		Account account = (Account) um.unmarshal(new FileReader(file));

		output.append(account.getPeople().get(0).getUsername());
		output.newLine();
		output.append("ticket count:"
				+ account.getProjects().get(0).getTickets().size());
		output.newLine();
		output.append("first ticket:"
				+ account.getProjects().get(0).getTickets().get(0));
		output.newLine();
		if (account.getProjects().size() <= 0) {
			throw new RuntimeException("Could not find any projces");
		}
		this.people = new ArrayList<Person>();

		this.attachments = new ArrayList<Attachment>();
		this.comments = new ArrayList<Comment>();
		this.components = new ArrayList<Component>();
		this.issues = new ArrayList<Issue>();
		this.meta = new Meta(Kind.BUG.name());
		this.milestones = new ArrayList<Milestone>();
		this.versions = new ArrayList<Version>();

		convertPeople(account);
		convertProject(account.getProjects().get(0));

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

	private void convertComponents() {
		// TODO
	}

	private void convertMeta() {
		// TODO
	}

	private void convertMilestones() {
		// TODO
	}

	private void convertVersions() {
		// TODO
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
			i.setAssignee(findPersonById(ticket.getAssigneeId()));
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

	public Writer getOutput() {
		return output;
	}
}
