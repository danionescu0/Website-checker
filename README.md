# Website-checker
Checks a list of urls from a file data source.
* Supports sending failed data to https://thingspeak.com
* Has text to speech capabilities to verbally report the problem (optionally)
* Supports raspberry pi integration having the ability to toggle a pin on and off. This 
can be usefull if you want to implement a physical alarm like a strobe light or a horn
##

## What do you need
* any machine or development board like Raspberry Pi, C.H.I.P, etc
* java 8
* gradle

## Usage
1. build the project: gradle build
2. java -jar -f /path/to/website-list.txt -gt -va

where: 

* "/path/to/website-list.txt" is a path to a simple text file containing each website that should be checked on a new line
* "-gt" enable triggering gpio pin
* "-va" enable voice alert
