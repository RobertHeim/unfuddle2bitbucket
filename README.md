# Unfuddle2Bitbucket

This program converts a backup.xml (Tickets, Milestones, etc.) created from Unfuddle to a JSON that can be importet by Bitbucket.

## WARNING

This project is under development and there are no releases, yet! IT DOES NOT WORK, YET! DON'T USE IT!

## Usage

### 1) Create a backup
In unfuddle go to your project, hit "*Project settings*" and click on "*Request a backup of this project now*". Download and extract the backup. It contains a "*backup.xml*" which contains the tickets, milestones and so on. This is the input for the converter.

### 2) Convert the data
```
java -jar unfuddle2bitbucket /path/to/backup.xml /path/to/output.json
```

### 3) Import the converted data
Go to a repository, click on "*Settings*" and there on "*Import & export*". WARNING: **Importing removes all the repository's existing issues. You cannot undo an import.** If you are sure that you want to overwrite all the issues, select the output.json for upload and start the import.


## Further information

The data format that is expected by Bitbucket can be found [here](https://confluence.atlassian.com/pages/viewpage.action?pageId=330796872).

## License

see [LICENCE.md](LICENCE.md)
