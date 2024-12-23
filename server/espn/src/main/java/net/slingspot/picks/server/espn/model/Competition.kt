package net.slingspot.picks.server.espn.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Competition(
    val id: String,
    val date: Instant,
    val timeValid: Boolean,
    val dateValid: Boolean,
    val competitors: List<Competitor>,
    val status: Status
)
