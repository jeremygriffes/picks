package net.slingspot.server.nfl

import net.slingspot.picks.model.football.Contest
import net.slingspot.picks.model.football.Franchise
import net.slingspot.picks.model.football.Schedule

interface NflDataSource {
    suspend fun initialize(year: Int)

    suspend fun franchises(year: Int): Set<Franchise>

    suspend fun schedule(year: Int): Schedule

    suspend fun update(contest: Contest)
}
