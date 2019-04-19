import requests
from datetime import datetime


with open('repos.txt') as f:
    lines = f.readlines()

t = ''

# Only add the lines with links in them
# https://github.com/digitaldisarray/Git-Backup
# https://github.com/digitaldisarray/Git-Backup/archive/master.zip
for line in lines:
	if line.startswith('http'):
		r = requests.get(line.strip())
		t = datetime.now().strftime('%Y-%m-%d %Hh%Mm%Ss')
		print('Saving to: ./' + line.split('/')[4] + '-master.zip')
		with open('./' + line.split('/')[4] + '-master ' + t + '.zip', 'wb') as f:
			f.write(r.content)
		print(r.status_code)  
		print(r.headers['content-type'])  
		print(r.encoding)  