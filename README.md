# Unfuddle2Bitbucket

This program converts a backup.xml (Tickets, Milestones, etc.) created from Unfuddle to a JSON that can be importet by Bitbucket.

## WARNING

This project is under development and there are no releases, yet! IT DOES NOT WORK, YET! DON'T USE IT!

## What is supported?

* People
* Projects / Repository -> Only the 1st repository is converted, because Bitbucket can only import issues to a specific repository.
* Components
* Milestones
* Versions
* Tickets
    * id
    * status
    * reporter
    * assignee
    * priority / severity
    * title / summary
    * createdOn / createdAt
    * updatedOn / updatedAt
    * content / description
    * contentUpdatedOn / updatedOn
    * component
    * milestone
    * comments
* Components
* Meta
* ~~Attachments~~

## Configuration

you can provide a *config.properties* file [TODO how].
* **default.kind** (default: bug) = \[bug | enhancement | proposal | task] (if the given value is not within that list, the default value is used).
* **default.assignee** = [auto_first | username | (can be null)]
    * *auto_first*: takes the first person found
    * *username*: the given *username* is set as default - if it does not exist in the people-tag no user is set as default assignee   
    * *null / not specified:* no default assignee
* **default.component**: analogous to *default.assignee* (if given, the component must exist in components)
* **default.milestone**: analogous to *default.assignee* (if given, the milestone must exist in milestones)
* **default.version**: analogous to *default.assignee* (if given, the version must exist in versions)


## Usage

### 1) Create a backup
In unfuddle go to your project, hit "*Project settings*" and click on "*Request a backup of this project now*". Download and extract the backup. It contains a "*backup.xml*" which contains the tickets, milestones and so on. This is the input for the converter.

### 2) Convert the data

```
usage: unfuddleToBitbucket [-c <FILE>] [-h] -i <FILE> -o <FILE> [-p] [-v]
Options:
    -c,--config-file <FILE>   use FILE instead of standard config.properties
    -h,--help                 print this help text
    -i,--input-file <FILE>    the backup.xml created by unfuddle
    -o,--output-file <FILE>   the file to write the JSON-output to
    -p,--pretty-print         print the json in readable format instead of
                              minimizing the output
    -v,--version              print the version

```

#### example usage:
```
java -jar unfuddleTobitbucket -p -i /path/to/backup.xml -o /path/to/output.json
```

### 3) Import the converted data
Go to a repository, click on "*Settings*" and there on "*Import & export*". WARNING: **Importing removes all existing issues from the repository. You cannot undo an import.** If you are sure that you want to overwrite all the issues, select the output.json for upload and start the import.


## Further information

The data format that is expected by Bitbucket can be found [here](https://confluence.atlassian.com/pages/viewpage.action?pageId=330796872).

## License

see [LICENCE.md](LICENCE.md)
