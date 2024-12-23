package net.slingspot.picks.server.espn.model

import kotlinx.serialization.Serializable

@Serializable
data class Competitor(
    val id: String,
    val homeAway: String,
    val team: Team,
    val score: Score
)
