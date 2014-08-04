package de.robert_heim.unfuddle2bitbucket.model.unfuddle;

abstract public class StringObjectWithId {
	private Integer id;
	private String name;

	public StringObjectWithId() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
