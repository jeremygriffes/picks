package net.slingspot.picks.server.espn.domain

import net.slingspot.picks.data.upsert
import net.slingspot.picks.model.football.Contest
import net.slingspot.picks.model.football.Franchise
import net.slingspot.picks.model.football.Schedule
import net.slingspot.picks.server.espn.data.EspnApi
import net.slingspot.picks.server.espn.data.cache.EspnCache
import net.slingspot.picks.server.espn.model.Event
import net.slingspot.picks.server.espn.model.Week
import net.slingspot.server.nfl.NflDataSource

internal class EspnRepository(
    private val cache: EspnCache,
    private val api: EspnApi
) : NflDataSource {
    override suspend fun initialize(year: Int, rebuildCache: Boolean) {
        cache.clear()

        if (rebuildCache) {
            TODO()
            // cache.eraseEverything()
        }

        cache.teamTable.all().takeUnless { it.isEmpty() } ?: run {
            api.teams(year).onEach {
                cache.teamTable.upsert(it)
            }
        }

        val season = cache.seasonTable.get(year) ?: run {
            api.season(year).also {
                cache.seasonTable.upsert(it)
            }
        }

        // Not sure yet how to handle the type. Pre/regular season weeks are known prior to the
        // season start, but post-season is not know until the regular season ends.
        val weeks = api.getList<Week>(season.type.weeks.ref)

        val weeksSize = weeks.size

        weeks.forEachIndexed { weekIndex, week ->
            cache.weekTable.upsert(week)

            println("week $weekIndex/$weeksSize $week")

            val events = api.getList<Event>(week.events.ref)

            val eventsSize = events.size

            events.forEachIndexed { eventIndex, event ->
                cache.eventTable.upsert(event)

                println("week $weekIndex/$weeksSize: event $eventIndex/$eventsSize $event")
            }
        }
    }

    override suspend fun franchises(year: Int): Set<Franchise> {
        TODO("Not yet implemented")
    }

    override suspend fun schedule(year: Int): Schedule {
        TODO("Not yet implemented")
    }

    override suspend fun update(contest: Contest) {
        TODO("Not yet implemented")
    }
}