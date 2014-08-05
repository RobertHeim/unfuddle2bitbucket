package de.robert_heim.unfuddle2bitbucket;

import de.robert_heim.unfuddle2bitbucket.converters.AttachmentsConverter;
import de.robert_heim.unfuddle2bitbucket.converters.CommentsConverter;
import de.robert_heim.unfuddle2bitbucket.converters.ComponentsConverter;
import de.robert_heim.unfuddle2bitbucket.converters.IssuesConverter;
import de.robert_heim.unfuddle2bitbucket.converters.LogsConverter;
import de.robert_heim.unfuddle2bitbucket.converters.MetaConverter;
import de.robert_heim.unfuddle2bitbucket.converters.MilestonesConverter;
import de.robert_heim.unfuddle2bitbucket.converters.PeopleConverter;
import de.robert_heim.unfuddle2bitbucket.converters.SeveritiesConverter;
import de.robert_heim.unfuddle2bitbucket.converters.VersionsConverter;
import de.robert_heim.unfuddle2bitbucket.model.ConfigJson;

public interface Provider {

	public ConfigJson getConfigJson();

	public MetaConverter getMetaConverter();

	public IssuesConverter getIssuesConverter();

	public AttachmentsConverter getAttachmentsConverter();

	public SeveritiesConverter getSeveritiesConverter();

	public LogsConverter getLogsConverter();

	public ComponentsConverter getComponentsConverter();

	public MilestonesConverter getMilestonesConverter();

	public PeopleConverter getPeopleConverter();

	public VersionsConverter getVersionsConverter();

	public CommentsConverter getCommentsConverter();

}
