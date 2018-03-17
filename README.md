# Website-checker
This is a website down checker which supports running requests in paralel and can be installed
on a development board.

Main features:

* Running requests in parallel
* Each webpage from the checklist has a max timeout and a optionally regex list to check
* Supports sending failed data to https://thingspeak.com
* Has text to speech capabilities to verbally report the problem (optionally)
* Supports [raspberry pi](https://www.raspberrypi.org/) and [C.H.I.P](https://getchip.com/). development board integration having the ability to toggle a pin on and off. This 
can be usefull if you want to implement a physical alarm like a strobe light or a horn


## What do you need
* any machine or development board like Raspberry Pi, C.H.I.P, etc
* java 8 to run
* gradle to build
* [optional] install festival (for text to speech capabilities): 
http://wisercoder.com/install-festival-text-speech-ubuntu/

## Usage
1. Build the project:

````
gradle build
````
2. Run it: 
````
java -jar -f /path_to_cloned_repo/build/libs/websitechecker-0.0.1-SNAPSHOT.jar /path/to/website-list.txt [--gpio-chip CSID0] [-va] [--gpio-pi pin_nr_on_pi]
````

where: 

* "/path/to/website-list.txt" is a path to a simple text file containing 
website check rules
* website check rules consists in a enumeration of websites each on a new line
along with the milliseconds to wait for the site response. The separator is space
* "--gpio-chip" enable triggering gpio pin on C.H.I.P. development board on pin "CDID0"
* "-va" enable voice alert

website check rules from the "/path/to/website-list.txt" 

* each line contains one rule
* first argument is the webpage
* second argument is the timeout in milliseconds
* the next arguments are regex rules to check if they are matched in the webpage content, 
if they aren't matched the page is considered as "down"

example:
````
https://github.com/ 1500 .*Learn.* .*Git.*
http://www.imdb.com 5050
````
The example above checks if github.com responds under 1500 ms and does contains the words "Learn"
and "Git" and the website imdb.com responds in 5050 ms.


4. [optional] set it to run as a cron job using crontab

The example below sets the checker to run every minute and to log output on "/path_to_log_file": 
````
crontab -e

# now write in crontab
* * * * * java -jar /path_to_cloned_repo/build/libs/websitechecker-0.0.1-SNAPSHOT.jar -f /path/to/website-list.txt [--gpio-chip CSID0] [-va] [--gpio-pi pin_nr_on_pi]  /path_to_log_file
````


## Extra configuration
In application;properties you can configure:

* The thingspeak api key. You can make a free account [here](https://thingspeak.com/) 
and find out more.  
command: **thingspeak.api.key=the_key_provided_by_thingspeak**

* How much time will the GPIO pin be active 
command: **gpio.pinHoldTime=nr_of_seconds_to_hold_gpio_active**

## Extending the code
Using event listeners. 

The event is called "com.danionescu.event.FinishedCheckingEvent" and it's launched after
all the website list has been crawled. It contains information about the failure / succes of 
each website to provide a 200 OK status.

By making a listener to this event for example: com.danionescu.listener.FooListener
you will receive the event your method that is marked with "@EventListener"

Code example:

````
    @EventListener
    public void onFinishedChecking(FinishedCheckingEvent finishedCheckingEvent) {

        //your code here
    }
````

Currently there are four event listeners, the listeners are responsable for:

* Submitting the failure to thingspeak
* Speak a verbal alert (with voice)
* Trigger a pin for C.H.I.P. development board if configured
* Trigger a pin for raspberry pi development board if configured