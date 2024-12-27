package net.slingspot.picks.server.espn.domain

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import net.slingspot.picks.data.upsert
import net.slingspot.picks.model.football.Contest
import net.slingspot.picks.model.football.Franchise
import net.slingspot.picks.model.football.Schedule
import net.slingspot.picks.server.espn.data.EspnApi
import net.slingspot.picks.server.espn.data.cache.EspnCache
import net.slingspot.picks.server.espn.model.Event
import net.slingspot.picks.server.espn.model.Season
import net.slingspot.picks.server.espn.model.Week
import net.slingspot.picks.server.espn.model.toFranchise
import net.slingspot.picks.server.espn.model.toSchedule
import net.slingspot.server.nfl.NflDataSource

internal class EspnRepository(
    private val cache: EspnCache,
    private val api: EspnApi
) : NflDataSource {
    private val mutex = Mutex()

    private val franchises = mutableMapOf<Int, Set<Franchise>>()
    private val schedule = mutableMapOf<Int, Schedule>()

    override suspend fun initialize(year: Int, rebuildCache: Boolean) {
        mutex.withLock {
            if (rebuildCache) {
                TODO()
                // cache.eraseEverything()
            }

            val teams = teamsOf(year)
            val season = seasonOf(year)
            val weeks = weeksOf(season.type)
            val events = weeks.associate { it.startDate to eventsOf(it) }

            val franchisesThisSeason = teams.map { it.toFranchise() }.toSet()

            franchises[year] = franchisesThisSeason
            schedule[year] = season.toSchedule(franchisesThisSeason, events)
        }
    }

    override suspend fun franchises(year: Int) = mutex.withLock {
        franchises[year].orEmpty()
    }

    override suspend fun schedule(year: Int) = mutex.withLock {
        schedule[year]
    }

    override suspend fun update(contest: Contest) {
        mutex.withLock {
            TODO("Not yet implemented")
        }
    }

    private suspend fun teamsOf(year: Int) =
        cache.teamTable.all()
            .takeIf { it.isNotEmpty() }
            ?: api.teams(year).onEach { cache.teamTable.upsert(it) }

    private suspend fun seasonOf(year: Int) =
        cache.seasonTable.get(year)
            ?: api.season(year).also { cache.seasonTable.upsert(it) }

    /**
     * Not sure yet how to handle the type. Pre/regular season weeks are known prior to the
     * season start, but post-season is not know until the regular season ends.
     */
    private suspend fun weeksOf(seasonType: Season.Type) =
        cache.weekTable.weeksIn(seasonType)
            .takeIf { it.isNotEmpty() }
            ?: api.getList<Week>(seasonType.weeks.ref).onEach { cache.weekTable.upsert(it) }

    private suspend fun eventsOf(week: Week) =
        cache.eventTable.eventsIn(week)
            .takeIf { it.isNotEmpty() }
            ?: api.getList<Event>(week.events.ref).onEach { cache.eventTable.upsert(it) }
}