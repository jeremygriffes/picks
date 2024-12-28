package net.slingspot.picks.model.entities

import kotlinx.serialization.Serializable

@Serializable
data class Ranking(
    val score: Int,
    val players: Set<Player>
)
