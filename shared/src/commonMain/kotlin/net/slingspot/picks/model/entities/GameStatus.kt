package net.slingspot.picks.model.entities

import net.slingspot.picks.model.Progress

data class GameStatus(
    val scores: Map<Player, Int> = mapOf(),
    val rankings: List<Ranking> = listOf(),
    val contenders: Set<Player> = emptySet(),
    val progress: Progress = Progress.Pending
)