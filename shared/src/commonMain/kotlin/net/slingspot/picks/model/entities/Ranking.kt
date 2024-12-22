package net.slingspot.picks.model.entities

data class Ranking(
    val score: Int,
    val players: Set<Player>
)
