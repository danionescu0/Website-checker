# Website-checker
Checks a list of urls from a file data source.
* Supports sending failed data to https://thingspeak.com
* Has text to speech capabilities to verbally report the problem (optionally)
* Supports raspberry pi integration having the ability to toggle a pin on and off. This 
can be usefull if you want to implement a physical alarm like a strobe light or a horn
##

## What do you need
* any machine or development board like Raspberry Pi, C.H.I.P, etc
* java 8 to run
* gradle to build
* optional install festival (for text to speech capabilities): 
http://wisercoder.com/install-festival-text-speech-ubuntu/

## Usage
1. build the project: gradle build
2. java -jar -f /path/to/website-list.txt  --gpio-chip CSID0 -va

where: 

* "/path/to/website-list.txt" is a path to a simple text file containing 
website check rules
* website check rules consists in a enumeration of websites each on a new line
along with the milliseconds to wait for the site response. The separator is space
* "--gpio-chip" enable triggering gpio pin on C.H.i.P. development board on pin "CDID0"
* "-va" enable voice alert

## Extra configuration
In application;properties you can configure:

* The thingspeak api key. You can make a free account [here](https://thingspeak.com/) 
and find out more.  
command: **thingspeak.api.key=the_key_provided_by_thingspeak**

* How much time will the GPIO pin be active 
command: **gpio.pinHoldTime=nr_of_seconds_to_hold_gpio_active**

## Extending the code
The code is extension enabled by using an event and listeners. 

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