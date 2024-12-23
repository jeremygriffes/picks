package net.slingspot.picks.server.espn.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ref(
    @SerialName("\$ref")
    val ref: String
)
