package net.slingspot.picks.server.espn.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Leagues(
    val id: String,
    val season: Season,
    val teams: Teams,
    val events: Events
) {
    @Serializable
    data class Teams(
        @SerialName("\$ref")
        val ref: String
    )

    @Serializable
    data class Events(
        @SerialName("\$ref")
        val ref: String
    )
}