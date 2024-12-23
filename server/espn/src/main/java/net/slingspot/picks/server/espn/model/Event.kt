package net.slingspot.picks.server.espn.model

import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val id: String,
    val date: String,
    val name: String,
    val timeValid: Boolean,
    val competitions: List<Competition>
)
