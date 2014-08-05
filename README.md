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

## Usage

see [USAGE.md](USAGE.md)

## Further information

The data format that is expected by Bitbucket can be found [here](https://confluence.atlassian.com/pages/viewpage.action?pageId=330796872).

## License

see [LICENCE.md](LICENCE.md)
