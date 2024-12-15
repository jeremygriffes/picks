package net.slingspot.pickem.shared.model.entities

import net.slingspot.pickem.shared.model.Progress

data class GameStatus(
    val scores: Map<Player, Int> = mapOf(),
    val rankings: List<Ranking> = listOf(),
    val contenders: Set<Player> = emptySet(),
    val progress: Progress = Progress.Pending
)