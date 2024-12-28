package net.slingspot.picks.model.entities

import kotlinx.serialization.Serializable
import net.slingspot.picks.model.football.Contest

@Serializable
data class Game(
    val id: String = "",
    val contests: Set<Contest> = emptySet(),
    val playersPicks: Map<Player, Picks> = emptyMap(),
    val overUnderContest: Contest? = contests.maxByOrNull { it.scheduledTime }
)