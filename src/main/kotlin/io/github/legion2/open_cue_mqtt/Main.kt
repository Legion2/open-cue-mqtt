package io.github.legion2.open_cue_mqtt

import com.natpryce.konfig.*
import com.natpryce.konfig.ConfigurationProperties.Companion.systemProperties
import io.github.legion2.open_cue_mqtt.client.CueSdkHttpServer
import kotlinx.coroutines.runBlocking
import org.eclipse.paho.client.mqttv3.IMqttClient
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import java.io.File
import java.nio.charset.StandardCharsets

suspend fun main() {
    val broker = Key("broker.url", stringType)
    val username = Key("broker.username", stringType)
    val password = Key("broker.password", stringType)
    val profileTopic = Key("mqtt.topic.profile", stringType)
    val sdkTopic = Key("mqtt.topic.sdk", stringType)
    val cueSdkServerHost = Key("cue.server.host", stringType)
    val cueSdkServerPort = Key("cue.server.port", intType)

    val config = systemProperties() overriding
            EnvironmentVariables() overriding
            ConfigurationProperties.fromOptionalFile(File("config.properties"))

    val sdkClient = CueSdkHttpServer(config.getOrElse(cueSdkServerHost, "localhost"), config.getOrElse(cueSdkServerPort, 25555))
    val mqttClient = MqttClient(config[broker], MqttClient.generateClientId(), MemoryPersistence())
    val connOpts = MqttConnectOptions()
    connOpts.userName = config[username]
    connOpts.password = config[password].toCharArray()
    connOpts.isCleanSession = true
    connOpts.isAutomaticReconnect = true
    mqttClient.connectUntilSuccess(connOpts)
    Runtime.getRuntime().addShutdownHook(Thread {
        mqttClient.disconnectForcibly()
        mqttClient.close()
    })

    mqttClient.subscribe(config[profileTopic], 1) { _, msg ->
        try {
            val profile = msg.payload.toString(StandardCharsets.UTF_8)
            runBlocking {
                sdkClient.deactivateAll()
                sdkClient.activate(profile)
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    mqttClient.subscribe(config[sdkTopic], 1) { _, msg ->
        try {
            val sdkControl = msg.payload.toString(StandardCharsets.UTF_8).toBoolean()
            runBlocking {
                sdkClient.setControl(sdkControl)
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}

fun IMqttClient.connectUntilSuccess(connOpts: MqttConnectOptions) {
    val connected = false
    while (!connected) {
        try {
            this.connect(connOpts)
            return
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }
}
