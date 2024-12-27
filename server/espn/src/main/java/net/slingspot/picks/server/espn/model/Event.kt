package net.slingspot.picks.server.espn.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class Event(
    val id: String,
    val date: String,
    val name: String,
    val timeValid: Boolean,
    val competitions: List<Competition>
) : EspnModel {
    override fun serialize() = Json.encodeToString(this)
}
