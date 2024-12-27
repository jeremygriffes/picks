package net.slingspot.picks.server.espn.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class Week(
    val number: Int,
    val startDate: String,
    val endDate: String,
    val text: String,
    val events: Ref
) : EspnModel {
    override fun serialize() = Json.encodeToString(this)
}