# Git-Backup
:floppy_disk: A small CLI tool for archiving GitHub repositories

## Purpose
I star a lot of repositories and sometimes those repositories get deleted. So, I wrote this tool so I can download backups of my favorite repositories.

## Usage
To backup a single repository:  
``git-backup.jar -l <repository link>``  
``git-backup.jar --link <repository link>``  

To backup all of a users repositories:  
``git-backup.jar -l <user profile link/link to user's repos page>``  
``git-backup.jar --link <user profile link/link to user's repos page>``  

To backup all of a given user's starred repositories:  
``git-backup.jar -l <link to user's stars tab>``  
``git-backup.jar --link <link to user's stars tab>``  

To backup all GitHub links found in a file:  
``git-backup.jar -f <file name>``  
``git-backup.jar --file <file name>``  

To specify an output path:  
``git-backup.jar -o <output path>``  
``git-backup.jar --output <output path>``  

**Tip:** Create a cron job to automate backups

## Creating repository lists
To create a list of repositories, simply write links into a file separated by new lines.

Any lines that are not registered as valid GitHub repository links will be ignored, so feel free to add comments or notes to help you organize your list.

**Example list:**
```
Tools:
https://github.com/digitaldisarray/Git-Backup

Libraries:
https://github.com/jhy/jsoup
https://github.com/ocornut/imgui
```
  
**Example command to download the list into a downloads folder:**
```
git-backup.jar -f list.txt -o downloads
```
  
**Terminal output:**  
```
Downloaded https://github.com/digitaldisarray/Git-Backup/archive/refs/heads/master.zip
Downloaded https://github.com/jhy/jsoup/archive/refs/heads/master.zip
Downloaded https://github.com/ocornut/imgui/archive/refs/heads/master.zip
Done!
```

## Possible improvements
- Using the official GitHub API instead of jsoup for extracting data would allow for private repositories to be backed up.  
- Allowing multiple output paths to be specified per command execution.  
- Checking if a repository hasn't changed since last download and skipping it if it hasn't.  

