package io.github.legion2.open_cue_mqtt.client

class GameSdkError(override val message: String?, override val cause: Throwable? = null) : RuntimeException()
