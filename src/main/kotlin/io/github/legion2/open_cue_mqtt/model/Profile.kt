package io.github.legion2.open_cue_mqtt.model

import kotlinx.serialization.Serializable

@Serializable
data class Profile(val name: String, val priority: Int, val state: Boolean)
