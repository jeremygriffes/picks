package net.slingspot.picks.server.data

import net.slingspot.picks.model.football.Contest
import net.slingspot.picks.model.football.Franchise
import net.slingspot.picks.model.football.Schedule

interface NflRepository {
    suspend fun initialize(year: Int, rebuildCache: Boolean = false)

    suspend fun franchises(year: Int): Set<Franchise>

    suspend fun schedule(year: Int): Schedule?

    suspend fun today(): List<Contest>

    suspend fun thisWeek(): List<Contest>

    suspend fun week(year: Int, weekNumber: Int): List<Contest>
}
