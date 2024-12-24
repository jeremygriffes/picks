package net.slingspot.server.nfl

import net.slingspot.picks.model.football.Contest
import net.slingspot.picks.model.football.Franchise

interface NflDataSource {
    suspend fun initialize(year: Int)

    suspend fun franchises(year: Int): Set<Franchise>

    suspend fun schedule(year: Int): Set<Franchise>

    suspend fun update(contest: Contest)
}
