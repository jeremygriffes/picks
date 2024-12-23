package net.slingspot.picks.server.espn.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Week(
    val number: Int,
    val startDate: Instant,
    val endDate: Instant,
    val events: List<Event>
)
