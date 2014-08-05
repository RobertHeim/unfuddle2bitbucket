# Usage

## 1) Create a backup
In unfuddle go to your project, hit "*Project settings*" and click on "*Request a backup of this project now*". Download and extract the backup. It contains a "*backup.xml*" which contains the tickets, milestones and so on. This is the input for the converter.

## 2) Convert the data

```
usage: unfuddleToBitbucket [-c <FILE>] [-h] -i <FILE> -o <FILE> [-p] [-v]
Options:
    -c,--config-file <FILE>   use FILE instead of standard config
                              the FILE can contain:
                              |	default.kind=[bug | enhancement | proposal | task]
                              |		if the given value is not within that list, bug is used
                              |	default.assignee=[auto_first | name | (can be null)]
                              |		auto_first: takes the first person found
                              |		name:	the given username is set as default
                              |			if it does not exist in the people-tag
                              |			no user is set as default assignee
                              |		null / not specified: no default assignee
                              |	default.component=... analogous to default.assignee
                              |		if name is provided, the component must exist in the backup
                              |	default.milestone=... analogous to default.assignee
                              |		if name is provided, the milestone must exist in the backup
                              |	default.version=... analogous to default.assignee
                              |		if name is provided, the version must exist in the backup
                              |
    -fw,--force-write         overwrite the output file if it exists
    -h,--help                 print this help text
    -i,--input-file <FILE>    the backup.xml created by unfuddle
    -o,--output-file <FILE>   the file to write the JSON-output to
    -p,--pretty-print         print the json in readable format instead of minimizing the output
    -v,--version              print the version
```

### Configuration
If no *config-file* is specified, it defaults to:

```
default.kind=bug
default.assignee=
default.component=
default.milestone=
default.version=
```

### Example usage:
```
java -jar unfuddleTobitbucket -p -i /path/to/backup.xml -o /path/to/output.json
Done. You can find the result in /path/to/output.json
```

## 3) Import the converted data
Go to a repository, click on "*Settings*" and there on "*Import & export*". WARNING: **Importing removes all existing issues from the repository. You cannot undo an import.** If you are sure that you want to overwrite all the issues, select the output.json for upload and start the import.
