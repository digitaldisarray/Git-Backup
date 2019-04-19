# Git-Backup
  :floppy_disk: A small script for archiving GitHub repos.
# About
  I was originally making this to run on a pi 24/7 to make backups of my or anyone else's git repos. This is extremely incomplete and has bad code. Maybe someday I'll fix it so it will actualy work, and who knows, I might swap to python just to make it easier on myself.
  <br/>UPDATE: I ditched the java version (for now) and have made a super basic (working) python version.
## Usage (python version)
  ### 1.  Install dependencies
  As of now the python version only uses [requests](http://docs.python-requests.org/en/master/) which you might have to install, and datetime which you should already have.
  ### 2. Adding repos
  In the repos.txt file you can paste the link to any repository and the script will download that repositorie's master branch in a zip file.
  <br/>As of now there are no config files but when there are I will create some basic explanations on how to create your own config file.
  ### 3. Running
  While in the same directory as `gitbackup.py` run the command `python gitbackup.py` and all of the repos should be downloaded.
