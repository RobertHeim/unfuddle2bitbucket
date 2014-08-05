package de.robert_heim.unfuddle2bitbucket;

import java.io.FileReader;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

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
import de.robert_heim.unfuddle2bitbucket.model.bitbucket.DbJson;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Account;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.IntegerConverter;
import de.robert_heim.unfuddle2bitbucket.model.unfuddle.Project;

/**
 * This class instanciates all converters and runs the conversion from the input
 * file to {@link DbJson}.
 * 
 * @author Robert Heim
 */
public class BackupToDbJson implements Provider {

	private ConfigJson configJson;

	private MetaConverter metaConverter;

	private IssuesConverter issuesConverter;
	private AttachmentsConverter attachmentsConverter;
	private SeveritiesConverter severitiesConverter;
	private LogsConverter logsConverter;
	private ComponentsConverter componentsConverter;
	private MilestonesConverter milestonesConverter;
	private PeopleConverter peopleConverter;
	private VersionsConverter versionsConverter;
	private CommentsConverter commentsConverter;

	public BackupToDbJson(ConfigJson configJson) {
		this.configJson = configJson;
	}

	public DbJson convert(String file) throws JAXBException, IOException {
		JAXBContext context = JAXBContext.newInstance(Account.class);
		Unmarshaller um = context.createUnmarshaller();
		um.setAdapter(new IntegerConverter());
		Account account = (Account) um.unmarshal(new FileReader(file));

		if (account.getProjects().size() <= 0) {
			throw new RuntimeException("Could not find any projces");
		}

		this.attachmentsConverter = new AttachmentsConverter();
		this.severitiesConverter = new SeveritiesConverter();
		this.componentsConverter = new ComponentsConverter();
		this.milestonesConverter = new MilestonesConverter();
		this.versionsConverter = new VersionsConverter();
		this.metaConverter = new MetaConverter(this);
		this.commentsConverter = new CommentsConverter(this);
		this.issuesConverter = new IssuesConverter(this);
		this.logsConverter = new LogsConverter(this);
		this.peopleConverter = new PeopleConverter(this);

		peopleConverter.convert(account);
		metaConverter.convert();
		Project project = account.getProjects().get(0);
		componentsConverter.convert(project);
		milestonesConverter.convert(project);
		versionsConverter.convert(project);
		// TODO attachmentsConverter.convert();
		severitiesConverter.convert(project);
		convertProject(project);

		DbJson dbJson = new DbJson();
		dbJson.setAttachments(attachmentsConverter.getAttachments());
		dbJson.setComments(commentsConverter.getComments());
		dbJson.setComponents(componentsConverter.getBitbucketEntities());
		dbJson.setIssues(issuesConverter.getIssues());
		dbJson.setMeta(metaConverter.getMeta());
		dbJson.setMilestones(milestonesConverter.getBitbucketEntities());
		dbJson.setVersions(versionsConverter.getBitbucketEntities());
		dbJson.setLogs(logsConverter.getLogs());
		return dbJson;

	}

	private void convertProject(Project project) {
		issuesConverter.convert(project.getTickets());
	}

	@Override
	public ConfigJson getConfigJson() {
		return configJson;
	}

	@Override
	public MetaConverter getMetaConverter() {
		return metaConverter;
	}

	@Override
	public IssuesConverter getIssuesConverter() {
		return issuesConverter;
	}

	@Override
	public AttachmentsConverter getAttachmentsConverter() {
		return attachmentsConverter;
	}

	@Override
	public SeveritiesConverter getSeveritiesConverter() {
		return severitiesConverter;
	}

	@Override
	public LogsConverter getLogsConverter() {
		return logsConverter;
	}

	@Override
	public ComponentsConverter getComponentsConverter() {
		return componentsConverter;
	}

	@Override
	public MilestonesConverter getMilestonesConverter() {
		return milestonesConverter;
	}

	@Override
	public PeopleConverter getPeopleConverter() {
		return peopleConverter;
	}

	public VersionsConverter getVersionsConverter() {
		return versionsConverter;
	}

	@Override
	public CommentsConverter getCommentsConverter() {
		return commentsConverter;
	}

}
