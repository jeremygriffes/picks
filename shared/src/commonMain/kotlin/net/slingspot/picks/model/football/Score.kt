package net.slingspot.picks.model.football

import kotlinx.serialization.Serializable

@Serializable
data class Score(
    val home: Int = 0,
    val away: Int = 0
)
