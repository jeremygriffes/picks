package net.slingspot.picks.server.espn.model

import kotlinx.serialization.Serializable

@Serializable
data class Leagues(
    val id: String,
    val season: Season,
    val teams: Ref,
    val events: Ref
)