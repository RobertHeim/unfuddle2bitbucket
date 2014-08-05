package de.robert_heim.unfuddle2bitbucket.converters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.robert_heim.unfuddle2bitbucket.Provider;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Comment;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Issue;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Log;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Person;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Status;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Event;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Ticket;

public class LogsConverter {

	private List<Log> logs;
	private Provider provider;

	public LogsConverter(Provider provider) {
		this.provider = provider;
		this.logs = new ArrayList<Log>();
	}

	public List<Log> getLogs() {
		return logs;
	}

	public void convertEventsOfTicket(Issue issue, Ticket ticket) {
		for (Event e : ticket.getEvents()) {
			Person p = provider.getPeopleConverter().findPersonById(
					e.getPersonId());
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
				Comment comment = new Comment(e.getDescription(), createdOn,
						provider.getCommentsConverter().getUniqueCommentId(),
						ticket.getId(), createdOn, username);
				provider.getCommentsConverter().add(comment);

				l.setChangedTo(changedTo);
				l.setCommentId(comment.getId());
				l.setCreatedOn(createdOn);
				l.setField(field);
				l.setIssue(issue.getId());
				l.setUser(username);
				logs.add(l);
			}
		}
	}

}
