package de.robert_heim.unfuddle2bitbucket.converters;

import java.util.ArrayList;
import java.util.List;

import de.robert_heim.unfuddle2bitbucket.model.HasId;
import de.robert_heim.unfuddle2bitbucket.model.HasName;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Project;

public abstract class Converter<BitbucketType extends HasName, UnfuddleType extends HasId> {
	private List<BitbucketType> bitbucketEntities;
	private List<UnfuddleType> unfuddleEntities;

	public Converter() {
		bitbucketEntities = new ArrayList<BitbucketType>();
		unfuddleEntities = new ArrayList<UnfuddleType>();
	}

	abstract public void convert(Project project);

	public UnfuddleType findUnfuddleEntityById(Integer id) {
		if (null == unfuddleEntities) {
			return null;
		}
		for (UnfuddleType unfuddleEntity : unfuddleEntities) {
			if (unfuddleEntity.getId().equals(id)) {
				return unfuddleEntity;
			}
		}
		return null;
	}

	public BitbucketType findBitbucketEntityByName(String name) {
		if (null == bitbucketEntities) {
			return null;
		}
		for (BitbucketType bitbucketEntity : bitbucketEntities) {
			if (bitbucketEntity.getName().equals(name)) {
				return bitbucketEntity;
			}
		}
		return null;
	}

	public List<BitbucketType> getBitbucketEntities() {
		return bitbucketEntities;
	}

	public List<UnfuddleType> getUnfuddleEntities() {
		return unfuddleEntities;
	}
}
