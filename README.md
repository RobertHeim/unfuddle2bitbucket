# Unfuddle2Bitbucket

This program converts a backup.xml (Tickets, Milestones, etc.) created from Unfuddle to a JSON that can be importet by Bitbucket.

Repository: https://github.com/RobertHeim/unfuddle2bitbucket

## WARNING

This project is under development and there are no releases, yet! IT DOES NOT WORK, YET! DON'T USE IT!

Certain objects, such as usernames or comments, rely on foreign keys. During an import, Bitbucket silently uses NULL to replace any foreign keys that it cannot resolve (for example, a username that does not exist on Bitbucket).

All, foreign keys are managed from the converter. However, a user named "unfuddleUser1" may exist on Bitbucket with another name, e.g. "bitbucketUser1". To convert the username, you can provide a *userMap*. See [USAGE.md](USAGE.md) for more information.

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
    * priority
    * severity / kind
    * title / summary
    * createdOn / createdAt
    * updatedOn / updatedAt
    * content / description
    * contentUpdatedOn / updatedOn
    * component
    * milestone
    * comments
    * watcher / subscriptions
    * ~~voters~~ (does not exist in unfuddle?!)
* Components
* Meta
* ~~Attachments~~
* ~~Events on Ticket and Logs~~
* ~~Links to Changesets from Tickets and Comments and Logs~~

## Usage

see [USAGE.md](USAGE.md)

## Further information

The data format that is generated by Unfuddle can be found [here](https://unfuddle.com/support/docs/api/data_models).

The data format that is expected by Bitbucket can be found [here](https://confluence.atlassian.com/pages/viewpage.action?pageId=330796872).

## License

see [LICENCE.md](LICENCE.md)
