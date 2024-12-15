package net.slingspot.pickem.shared.model.entities

data class Ranking(
    val score: Int,
    val players: Set<Player>
)
