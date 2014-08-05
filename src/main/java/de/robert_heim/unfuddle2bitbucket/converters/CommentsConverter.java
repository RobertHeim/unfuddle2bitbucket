package de.robert_heim.unfuddle2bitbucket.converters;

import java.util.ArrayList;
import java.util.List;

import de.robert_heim.unfuddle2bitbucket.Provider;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Comment;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Person;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Ticket;

public class CommentsConverter {
	private int nextUniqueCommentsId;
	private List<Comment> comments;
	private Provider provider;

	public CommentsConverter(Provider provider) {
		this.nextUniqueCommentsId = 1;
		this.provider = provider;
		this.comments = new ArrayList<Comment>();
	}

	public void add(Comment comment) {
		comments.add(comment);
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void convert(Ticket ticket) {
		for (de.robert_heim.unfuddle2bitbucket.model.unfuddle.Comment unfuddleComment : ticket
				.getComments()) {

			String username = null;
			Person p = provider.getPeopleConverter().findPersonById(
					unfuddleComment.getAuthorId());
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
			comments.add(comment);
		}
	}

	public Integer getUniqueCommentId() {
		Integer id = nextUniqueCommentsId;
		nextUniqueCommentsId++;
		return id;
	}

}
