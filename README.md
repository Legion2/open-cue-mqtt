# Open CUE MQTT Adapter ![Build](https://github.com/Legion2/open-cue-mqtt/workflows/Build/badge.svg)
MQTT Adapter for [Open CUE Service](https://github.com/Legion2/open-cue-service).
Synchronize a MQTT topic with the Open CUE Service to set and change profiles.

Supported features:
* change profile by sending MQTT message with profile name
* disable/enable SDK control by sending MQTT message
* profiles are automatically set after restart 

## Setup
First setup Open CUE Service, therefore please read the documentation in the [Open CUE CLI](https://github.com/Legion2/open-cue-cli) repository.
Comeback here when Open CUE Service is working.

Download the [latest Release](https://github.com/Legion2/open-cue-mqtt/releases) and extract the archive.
Create a `config.properties` file in the extracted directory and add the configuration for the mqtt broker.
The following can be used as template for the config file:
```properties
broker.url=tcp://somehost
broker.username=mqtt-username
broker.password=secret$password
mqtt.topic.profile=home/open-cue-mqtt/profile
mqtt.topic.sdk=home/open-cue-mqtt/sdk
```
Run the Open CUE MQTT Adapter by executing `open-cue-mqtt.bat` or `./open-cue-mqtt`.

### MQTT messages
The messages send to the adapter must be plain text.
On the profile topic just send the name of the profile you want to be activated (all other profiles will be deactivated).
On the SDK topic `true` or `false` can be send.

When sending message to the topics use retained messages so they can be automatically applied at startup.

### Run at PC start
To run Open CUE MQTT Adapter when the PC is started on windows the task scheduler can be used.
Setup a new task, which is triggered 30 seconds after the user(you) logged in.
The task should run the `open-cue-mqtt.bat` in the directory where also the `config.properties` file is.
It should run as the user which logged in and does not require administrator permissions.

Do the same for the Open CUE Service.
But it should start without delay when the used logged in.
