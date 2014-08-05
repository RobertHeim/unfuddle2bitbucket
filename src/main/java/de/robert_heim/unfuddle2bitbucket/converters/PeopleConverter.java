package de.robert_heim.unfuddle2bitbucket.converters;

import java.util.ArrayList;
import java.util.List;

import de.robert_heim.unfuddle2bitbucket.Provider;
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.Person;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Account;

public class PeopleConverter {
	private List<Person> people;
	private Provider provider;

	public PeopleConverter(Provider provider) {
		this.people = new ArrayList<Person>();
		this.provider = provider;
	}

	public void convert(Account account) {
		for (de.robert_heim.unfuddle2bitbucket.model.unfuddle.Person unfuddlePerosn : account
				.getPeople()) {
			Person person = new Person();
			person.setId(unfuddlePerosn.getId());
			String newUsername = provider.getConfigJson().lookupUsername(
					unfuddlePerosn.getUsername(), unfuddlePerosn.getUsername());
			person.setName(newUsername);
			this.people.add(person);
		}
	}

	/**
	 * @param id
	 * @return the person or null
	 */
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

	public List<Person> getPeople() {
		return people;
	}
}
