# siem-monitor
Systems Security class project - system for security information and event management (SIEM).

## MongoDB setup
1. Download MongoDB from [here](https://www.mongodb.com/download-center#production) and install it
2. Create the mongod.cfg config file with the following contents (replacing paths) in `C:\Program Files\MongoDB\Server\3.6`:
```
systemLog:
    destination: file
    path: c:\...\mongodb\log\mongod.log
storage:
    dbPath: c:\...\mongodb\data
```
3. Create files and folders specified in the mongod.cfg file
4. Start cmd with admin privileges and start MongoDB with:
```
"C:\Program Files\MongoDB\Server\3.6\bin\mongod.exe" --config "C:\Program Files\MongoDB\Server\3.6\mongod.cfg"
```
5. Open a second cmd with admin privileges and run the following commands (replacing `<admin username>` and `<admin password>`):
```
"C:\Program Files\MongoDB\Server\3.6\bin\mongo.exe"

use admin
db.createUser({user: <admin username>, pwd: <admin password>, roles: [{role: "userAdminAnyDatabase", db: "admin"}]})
exit
```
6. Stop the first cmd and install MongoDB (that requires authentication) as a Windows service (uninstall first if already installed):
```
"C:\Program Files\MongoDB\Server\3.6\bin\mongod.exe" --config "C:\Program Files\MongoDB\Server\3.6\mongod.cfg" --auth --install
```
7. Run MongoDB from the same cmd: `net start MongoDB`
8. Go back to that second cmd and start mongo shell again
9. Authenticate as admin (replacing `<admin username>` and `<admin password>`):
```
use admin
db.auth(<admin username>, <admin password>)
```
10. Create database and its user (replacing `<dev username>` and `<dev password>`):
```
use siemdb
db.createUser({user: <dev username>, pwd: <dev password>, roles: [{role: "readWrite", db: "siemdb"}]})
```
11. Add the following system environment variables:
```
CODE10_SIEM_DB_DEV_USERNAME <dev username>
CODE10_SIEM_DB_DEV_PASSWORD <dev password>
```

## Linux log format setup
1. Open `etc/rsyslog.d/50-default.conf`
2. Paste this as the first two lines of the file:
 ``` 
$template SiemFormat, "%TIMESTAMP:1:24:date-rfc3339% | %hostname% | %source% | %procid% | %syslogfacility-text% | %syslogseverity-text% | %msg%\n"

$ActionFileDefaultTemplate SiemFormat
 ```
3. Run `sudo service rsyslog restart` in terminal

## Generating SSL certificates
1. Add the following system environment variables:
```
CODE10_SIEM_CERT_SERVER_KEY_PASS <password>
CODE10_SIEM_CERT_CLIENT_KEY_PASS <password>
```
2. Open Power Shell and run the script `.\ssl\ssl.ps1`.

## Team members
* [Aleksandar Nikolic](https://github.com/aleknik)
* [Helena Zecevic](https://github.com/helenazecevic)
* [Luka Maletin](https://github.com/lukamaletin)
