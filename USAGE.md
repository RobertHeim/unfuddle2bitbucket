# Usage

## 1) Create a backup
In unfuddle go to your project, hit "*Project settings*" and click on "*Request a backup of this project now*". Download and extract the backup. It contains a "*backup.xml*" which contains the tickets, milestones and so on. This is the input for the converter.

## 2) Convert the data

### Download

Download the jar from the [release section](https://github.com/RobertHeim/unfuddle2bitbucket/releases). 


### Example usage:
```
java -jar unfuddle2bitbucket-0.1.0.jar -p -c config.json -i /path/to/backup.xml -o /path/to/output.zip
Done. You can find the result in /path/to/output.zip
```

### Usage options

```
usage: unfuddle2Bitbucket [-c <FILE>] [-fw] [-h] -i <FILE> -o <FILE> [-p] [-v]
Options:
    -c,--config-file <FILE>   The configuration file. See documentation at github for further
                              information.
    -fw,--force-write         overwrite the output file if it exists
    -h,--help                 print this help text
    -i,--input-file <FILE>    the backup.xml created by unfuddle
    -o,--output-file <FILE>   the file to write the archive to
    -p,--pretty-print         print the json in readable format instead of minimizing the output
    -v,--version              print the version
```

#### Configuration
You can specify a config-file in JSON-format. The following explains what you can do:

* meta: some default values, they are all related to bitbucket (not unfuddle)
* userMap: If the names of the users are not the same on Bitbucket as they were on Unfuddle, this helps you to convert them.
* severity2KindMap: While you can specify your own severities on Unfuddle, Bitbucket only knows some specific kind-types. The **valid** ones are: *bug*, *enhancement*, *proposal* and *task*. As a default, the *bug*-kind is used whenever the configuration is not specified sufficiently. It's **case-sensitive**!
 
#### Example Configuration

```
{
	"meta": {
		"defaultKind":"bug",
		"defaultAssignee":"MyBitBucketUser1", // bitbucket account, not unfuddle
		"defaultComponent":"",
		"defaultMilestone":"",
		"defaultVersion":""
	},
	"userMap":{
		"MyUnfuddleUser1":"MyBitBucketUser1"
	},
	"severity2KindMap":{ 		// case sensitive!
		"Task":"task",
		"Defect":"bug",
		"Feature":"enhancement",
		"ThinkOf":"proposal"
	}
}
```


## 3) Import the converted data
Go to a repository, click on "*Settings*" and there on "*Import & export*". WARNING: **Importing removes all existing issues from the repository. You cannot undo an import.** If you are sure that you want to overwrite all the issues, select the output.zip for upload and start the import.
