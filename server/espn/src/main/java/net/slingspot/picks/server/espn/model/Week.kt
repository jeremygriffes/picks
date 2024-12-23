package net.slingspot.picks.server.espn.model

import kotlinx.serialization.Serializable

@Serializable
data class Week(
    val number: Int,
    val startDate: String,
    val endDate: String,
    val text: String,
    val events: Ref
)