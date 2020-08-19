# Open CUE MQTT Adapter ![Build](https://github.com/Legion2/open-cue-mqtt/workflows/Build/badge.svg)
MQTT Adapter for Open CUE Service.
Synchronize a MQTT topic with the Open CUE Service to set and change profiles.

Supported features:
* change profile by sending MQTT message with profile name
* disable/enable SDK control by sending MQTT message
* profiles are automatically set after restart 

## Setup
Create a `config.properties` file and add the configuration for the mqtt broker.
The following can be used as template for the config file:
```properties
broker.url=tcp://somehost
broker.username=mqtt-username
broker.password=secret$password
mqtt.topic.profile=home/open-cue-mqtt/profile
mqtt.topic.sdk=home/open-cue-mqtt/sdk
```

When sending message to the topics use retained messages so they can be automatically applied at start.
