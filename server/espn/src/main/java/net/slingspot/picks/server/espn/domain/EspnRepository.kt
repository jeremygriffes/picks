package net.slingspot.picks.server.espn.domain

import net.slingspot.picks.data.upsert
import net.slingspot.picks.model.football.Contest
import net.slingspot.picks.model.football.Franchise
import net.slingspot.picks.server.espn.data.EspnApi
import net.slingspot.picks.server.espn.data.cache.Cache
import net.slingspot.picks.server.espn.model.Event
import net.slingspot.picks.server.espn.model.Week
import net.slingspot.server.nfl.NflDataSource

internal class EspnRepository(
    private val cache: Cache,
    private val api: EspnApi
) : NflDataSource {
    override suspend fun initialize(year: Int) {
        cache.clear()

        val season = api.season(year)

        cache.seasonTable.upsert(season)

        val teams = api.teams(year)

        teams.forEach {
            cache.teamTable.upsert(it)
        }

        // Not sure yet how to handle the type. Pre/regular season weeks are known prior to the
        // season start, but post-season is not know until the regular season ends.
        val weeks = api.getList<Week>(season.type.weeks.ref)

        weeks.forEach { week ->
            cache.weekTable.upsert(week)

            val events = api.getList<Event>(week.events.ref)

            events.forEach { event ->
                cache.eventTable.upsert(event)

                event.competitions.forEach { competition ->
                    cache.competitionTable.upsert(competition)
                }
            }
        }
    }

    override suspend fun franchises(year: Int): Set<Franchise> {
        TODO("Not yet implemented")
    }

    override suspend fun schedule(year: Int): Set<Franchise> {
        TODO("Not yet implemented")
    }

    override suspend fun update(contest: Contest) {
        TODO("Not yet implemented")
    }
}