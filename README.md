# Unfuddle2Bitbucket

This program converts a backup.xml (Tickets, Milestones, etc.) created from Unfuddle to a JSON that can be importet by Bitbucket.

## WARNING

This project is under development and there are no releases, yet! IT DOES NOT WORK, YET! DON'T USE IT!

## What is supported?

* People
* Projects / Repository -> Only the 1st repository is converted, because Bitbucket can only import issues to a specific repository.
* Milestones
* Versions
* Tickets + Comments
* Components
* Meta
    * see File **config.properties**
    * **default.kind** (default: bug) = [bug | enhancement | proposal | task] (if the given value is not within that list, the default value is used).
    * **defaul.assignee** = [auto_first | username | (can be null)]
        * *auto_first*: takes the first person found
        * *username*: the given *username* is set as default - if it does not exist in the people-tag no user is set as default assignee   
        * *null / not specified:* no default assignee
    * **default.component**: analogous to *default.assignee* (if given, the component must exist in components)
    * default.milestone**: analogous to *default.assignee* (if given, the milestone must exist in milestones)
    * default.version**: analogous to *default.assignee* (if given, the version must exist in versions)
* ~~Attachments~~

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
