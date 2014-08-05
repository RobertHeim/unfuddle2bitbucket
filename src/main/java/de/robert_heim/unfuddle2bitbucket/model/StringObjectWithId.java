package de.robert_heim.unfuddle2bitbucket.model;

abstract public class StringObjectWithId extends StringObject implements HasId {
	private Integer id;

	public StringObjectWithId() {
		super();
	}

	public StringObjectWithId(String name) {
		super(name);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
