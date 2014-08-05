# Unfuddle2Bitbucket

This program converts a backup.xml (Tickets, Milestones, etc.) created from Unfuddle to a JSON that can be importet by Bitbucket.

Repository: https://github.com/RobertHeim/unfuddle2bitbucket

## WARNING
1. An import on Bitbucket removes ALL existing issues!

2. Certain objects, such as usernames or comments, rely on foreign keys. During an import, **Bitbucket silently uses NULL to replace any foreign keys that it cannot resolve** (for example, a username that does not exist on Bitbucket).

3. All, foreign keys are managed from the converter. However, a user at unfuddle named "unfuddleUser1" may exist on Bitbucket with another username, e.g. "bitbucketUser1". To convert the username, you can provide a *userMap*. See [USAGE.md](USAGE.md) for more information. If you forget to configure the config-file, **someoneelse on this planet might get assigned to your issues!**

## What is supported?

* People (they are mappable to bitbucket-accounts)
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
    * watcher / subscriptions, they are converted but [Bitbucket will not import them](https://bitbucket.org/site/master/issue/7417/issue-importer-does-not-transfer-watchers), unless the zip is signed by them to prevent spam.
    * ~~voters~~ (does not exist in unfuddle?!)
* Components
* Meta
* ~~Attachments~~
* Events on tickets converted to logs:
    * create ticket
    * accept ticket
    * close ticket
    * reassign ticket
    * resolve ticket
    * reopen ticket
    * ~~update ticket~~ Unfuddle puts the changes from the update in the description - this may contain emailaddresses as plaintext or sometimes the Names (not usernames) and other stuff... which makes it quite complicated to support it. A solution might parse the description-field. It may contain the changed field enclosed in  double-stars (\*\*)
* ~~Links to Changesets from Tickets, Comments and Logs~~

## Usage

see [USAGE.md](USAGE.md)

## Further information

The data format that is generated by Unfuddle can be found [here](https://unfuddle.com/support/docs/api/data_models).

The data format that is expected by Bitbucket can be found [here](https://confluence.atlassian.com/pages/viewpage.action?pageId=330796872).

## License

see [LICENCE.md](LICENCE.md)
