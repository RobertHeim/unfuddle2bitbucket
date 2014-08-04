package de.robert_heim.unfuddle2bitbucket.model;

import java.util.List;

public class DbJson {
	@NotNull
	List<Issue> issues;
	@NotNull
	List<Comment> comments;
	@NotNull
	List<Attachment> attachments;
	@NotNull
	List<Component> components;
	
	@NotNull
	List<Milestone> milestones;
	
	@NotNull
	List<Version> versions;
	
	@NotNull
	Meta meta;	
	
}
