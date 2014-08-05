package de.robert_heim.unfuddle2bitbucket.model.bitbucket;

public class Attachment {
	private String filename;
	private Integer issue;
	private String path;
	private String user;

	public Attachment() {
	}

	public Attachment(String filename, Integer issue, String path) {
		this.filename = filename;
		this.issue = issue;
		this.path = path;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Integer getIssue() {
		return issue;
	}

	public void setIssue(Integer issue) {
		this.issue = issue;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
