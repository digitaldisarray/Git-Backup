import requests
from datetime import datetime

# Todo:
# Entire profile backups
# Entire organization backup
# Config file
# Sorting options in config file
# Able to select different branches in config file

# Converts https://github.com/digitaldisarray/Git-Backup
# To: https://github.com/digitaldisarray/Git-Backup/archive/master.zip
def repo_to_dl(repolink):
	if repolink.endswith('/'):
		return repolink + "archive/master.zip"
	else:
		return repolink + "/archive/master.zip"

with open('repos.txt') as f:
    lines = f.readlines()

# Only add the lines with links in them
for line in lines:
	if line.startswith('http'):
		if line.endswith('.zip') == False:
			line = repo_to_dl(line)
			
		r = requests.get(line.strip())
		t = datetime.now().strftime('%Y-%m-%d %Hh %Mm %Ss') # We get the time each time in the loop rather than once in case the script takes a while
		
		print('Saving: ' + line.split('/')[4] + '-master' + t + '.zip')
		with open('./' + line.split('/')[4] + '-master ' + t + '.zip', 'wb') as f:
			f.write(r.content)