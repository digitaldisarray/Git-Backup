import requests
from datetime import datetime

# Todo:
# Link conversion
# Entire profile backups
# Config file
# Sorting options

with open('repos.txt') as f:
    lines = f.readlines()

# Only add the lines with links in them
for line in lines:
	if line.startswith('http'):
		r = requests.get(line.strip())
		t = datetime.now().strftime('%Y-%m-%d %Hh %Mm %Ss') # We get the time each time in the loop rather than once in case the script takes a while
		print('Saving: ' + line.split('/')[4] + '-master' + t + '.zip')
		with open('./' + line.split('/')[4] + '-master ' + t + '.zip', 'wb') as f:
			f.write(r.content)
