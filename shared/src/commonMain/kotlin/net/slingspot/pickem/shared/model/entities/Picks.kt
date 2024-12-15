package net.slingspot.pickem.shared.model.entities

import net.slingspot.pickem.shared.model.football.Contest
import net.slingspot.pickem.shared.model.football.Franchise

data class Picks(
    val selectedVictors: Set<Franchise> = emptySet(),
    val overUnder: Int = 0
) {
    fun selection(contest: Contest) =
        if (contest.home in selectedVictors) contest.home
        else if (contest.away in selectedVictors) contest.away
        else null

    fun selected(victors: Set<Franchise>): Boolean = victors.any { it in selectedVictors }

    fun selected(victor: Franchise): Boolean = victor in selectedVictors
}
