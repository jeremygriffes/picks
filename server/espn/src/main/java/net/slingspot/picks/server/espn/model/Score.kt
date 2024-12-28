package net.slingspot.picks.server.espn.model

import kotlinx.serialization.Serializable

@Serializable
data class Score(
    val value: Float,
    val winner: Boolean = false
)
